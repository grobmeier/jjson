#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JAVA_HOME="${JAVA_HOME:-/Users/cy/.sdkman/candidates/java/17.0.6-zulu}"
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
export GPG_TTY="${GPG_TTY:-$(tty)}"

usage() {
  cat <<'USAGE'
Usage:
  scripts/release.sh release <parent-version> <jjson-version> <jjson-struts2-version>
  scripts/release.sh prepare <parent-version> <jjson-version> <jjson-struts2-version>
  scripts/release.sh perform
  scripts/release.sh publish-checkout
  scripts/release.sh clean

Examples:
  scripts/release.sh release 9 0.1.9 0.0.11
  scripts/release.sh publish-checkout

Requirements:
  - ~/.m2/settings.xml must contain a literal <server><id>central</id> entry
    with a Sonatype Central Portal user token.
  - A usable GPG secret key must be available locally.
USAGE
}

die() {
  echo "ERROR: $*" >&2
  exit 1
}

require_command() {
  command -v "$1" >/dev/null 2>&1 || die "Missing required command: $1"
}

require_clean_worktree() {
  local status
  status="$(git -C "$ROOT_DIR" status --porcelain)"
  [[ -z "$status" ]] || die "Working tree is not clean. Commit or stash changes before releasing."
}

require_central_credentials() {
  local settings="${HOME}/.m2/settings.xml"
  [[ -f "$settings" ]] || die "Missing $settings"
  grep -Eq '<id>[[:space:]]*central[[:space:]]*</id>' "$settings" \
    || die "Missing literal <id>central</id> server entry in $settings"
}

next_snapshot() {
  local version="$1"

  if [[ "$version" =~ ^[0-9]+$ ]]; then
    echo "$((version + 1))-SNAPSHOT"
    return
  fi

  if [[ "$version" =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]]; then
    echo "${BASH_REMATCH[1]}.${BASH_REMATCH[2]}.$((BASH_REMATCH[3] + 1))-SNAPSHOT"
    return
  fi

  die "Cannot compute next snapshot for version '$version'. Use a simple integer or x.y.z version."
}

warm_gpg() {
  echo "Checking GPG signing setup..."
  gpgconf --kill gpg-agent >/dev/null 2>&1 || true
  echo "jjson release signing check" | gpg --clearsign >/dev/null
}

prepare_release() {
  local parent_version="$1"
  local jjson_version="$2"
  local struts_version="$3"
  local parent_next
  local jjson_next
  local struts_next
  local tag

  parent_next="$(next_snapshot "$parent_version")"
  jjson_next="$(next_snapshot "$jjson_version")"
  struts_next="$(next_snapshot "$struts_version")"
  tag="parent-${parent_version}"

  require_clean_worktree
  require_central_credentials
  warm_gpg

  mvn -B release:prepare \
    -Dproject.rel.de.grobmeier.json:parent="$parent_version" \
    -Dproject.dev.de.grobmeier.json:parent="$parent_next" \
    -Dproject.rel.de.grobmeier.json:jjson="$jjson_version" \
    -Dproject.dev.de.grobmeier.json:jjson="$jjson_next" \
    -Dproject.rel.de.grobmeier.json:jjson-struts2="$struts_version" \
    -Dproject.dev.de.grobmeier.json:jjson-struts2="$struts_next" \
    -Dtag="$tag"
}

perform_release() {
  require_central_credentials
  warm_gpg
  mvn -B release:perform
}

publish_checkout() {
  local checkout_dir="${ROOT_DIR}/target/checkout"

  [[ -f "${checkout_dir}/pom.xml" ]] || die "Missing release checkout at $checkout_dir"
  require_central_credentials
  warm_gpg

  (cd "$checkout_dir" && mvn -B -Psonatype-oss-release deploy)
}

clean_release_files() {
  mvn -B release:clean
}

main() {
  require_command git
  require_command grep
  require_command gpg
  require_command gpgconf
  require_command mvn

  cd "$ROOT_DIR"

  case "${1:-}" in
    release)
      [[ $# -eq 4 ]] || { usage; exit 2; }
      prepare_release "$2" "$3" "$4"
      perform_release
      ;;
    prepare)
      [[ $# -eq 4 ]] || { usage; exit 2; }
      prepare_release "$2" "$3" "$4"
      ;;
    perform)
      [[ $# -eq 1 ]] || { usage; exit 2; }
      perform_release
      ;;
    publish-checkout)
      [[ $# -eq 1 ]] || { usage; exit 2; }
      publish_checkout
      ;;
    clean)
      [[ $# -eq 1 ]] || { usage; exit 2; }
      clean_release_files
      ;;
    -h|--help|help|"")
      usage
      ;;
    *)
      usage
      exit 2
      ;;
  esac
}

main "$@"
