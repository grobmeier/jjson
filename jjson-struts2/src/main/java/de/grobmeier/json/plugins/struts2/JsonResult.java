/*
 *  Copyright 2007 Christian Grobmeier 
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, 
 *  software distributed under the License is distributed 
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 *  either express or implied. See the License for the specific 
 *  language governing permissions and limitations under the License.
 */
package de.grobmeier.json.plugins.struts2;

import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

import de.grobmeier.jjson.convert.JSONAnnotationEncoder;

/**
 * 
 * @author grobmeier
 */
public class JsonResult extends StrutsResultSupport {
	/** Serial */
	private static final long serialVersionUID = 2476992951577523951L;

	/** Log */
	private static final Log log = LogFactory.getLog(JsonResult.class);

	private String charSet = "UTF-8";

	/** Default constructor */
	public JsonResult() {
	}

	/**
	 * @param location
	 */
	public JsonResult(String location) {
		super(location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.dispatcher.StrutsResultSupport#doExecute(java.lang
	 * .String, com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		Charset charset = null;
		if (charSet != null) {
			if (Charset.isSupported(charSet)) {
				charset = Charset.forName(charSet);
			} else {
				charset = null;
			}
		}

		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(HTTP_RESPONSE);

		if (charset != null) {
			response.setContentType("application/json; charset=UTF-8");
		} else {
			response.setContentType("application/json");
		}
		response.setHeader("Content-Disposition", "inline");
		
		// allows crosssite ajax - TODO: should be optional 
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		PrintWriter writer = response.getWriter();
		try {
			if (!ActionSupport.SUCCESS.equals(invocation.getResultCode())) {
				if(ActionSupport.LOGIN.equals(invocation.getResultCode())) {
					response.sendError(401, "Not authorized");
					return;
				} else if("forbidden".equalsIgnoreCase(invocation.getResultCode())) {
					response.sendError(403, "Request is not allowed");
					return;
				} else if(ActionSupport.ERROR.equalsIgnoreCase(invocation.getResultCode())) {
					response.sendError(500, "Server error");
					return;
				}
				
				writer.write(new char[] { 'n', 'u', 'l', 'l' }, 0, 4);
				return;
			}
			Object obj = invocation.getAction();
			JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
			String result = encoder.encode(obj);
			log.debug("Encoded JSON: " + result);
			writer.write(result);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * Set the character set
	 * 
	 * @return The character set
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * Set the character set
	 * 
	 * @param charSet
	 *            The character set
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
}
