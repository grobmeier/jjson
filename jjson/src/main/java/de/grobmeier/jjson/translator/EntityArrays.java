/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.grobmeier.jjson.translator;

/**
 * Class holding various entity data for HTML and XML - generally for use with
 * the LookupTranslator.
 * All arrays are of length [*][2].
 *
 * @since 3.0
 * @version $Id: EntityArrays.java 1088899 2011-04-05 05:31:27Z bayard $
 */
public class EntityArrays {
    /**
     * Mapping to escape the Java control characters.
     *
     * Namely: {@code \b \n \t \f \r}
     * @return the mapping table
     */
    public static String[][] JAVA_CTRL_CHARS_ESCAPE() { return JAVA_CTRL_CHARS_ESCAPE.clone(); }
    private static final String[][] JAVA_CTRL_CHARS_ESCAPE = {
        {"\b", "\\b"},
        {"\n", "\\n"},
        {"\t", "\\t"},
        {"\f", "\\f"},
        {"\r", "\\r"}
    };
}
