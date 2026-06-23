/*
 *  Copyright 2011 Christian Grobmeier
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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import junit.framework.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonResultTest {

    private CapturingResponse response;

    @Test
    public void testSimple() throws Exception {
        JsonResult result = new JsonResult();
        result.setCommentOutput(false);

        String executeAction = executeResult(result, new TestAction(), ActionSupport.SUCCESS);

        Assert.assertEquals("UTF-8", response.characterEncoding);
        Assert.assertEquals("application/json", response.contentType);
        Assert.assertEquals("inline", response.headers.get("Content-Disposition"));
        Assert.assertEquals("{\"test\":\"mytest\"}", executeAction);
    }

    @Test
    public void testCommenteJson() throws Exception {
        JsonResult result = new JsonResult();

        String executeAction = executeResult(result, new TestAction(), ActionSupport.SUCCESS);

        Assert.assertEquals("text/ext-json", response.contentType);
        Assert.assertEquals("/* {\"test\":\"mytest\"} */", executeAction);
    }

    @Test
    public void testMultiline() throws Exception {
        char[] charArray = new char[6];

        int i = 0;
        charArray[i++] = 'm';
        charArray[i++] = 'y';
        charArray[i++] = '\\';
        charArray[i++] = 'n';
        charArray[i++] = 'a';
        charArray[i++] = 'g';
        String part1 = new String(charArray);

        char[] charArray2 = new char[6];

        i = 0;
        charArray2[i++] = 'm';
        charArray2[i++] = 'y';
        charArray2[i++] = '\\';
        charArray2[i++] = 'n';
        charArray2[i++] = 'a';
        charArray2[i++] = 'g';
        String part2 = new String(charArray2);

        JsonResult result = new JsonResult();
        result.setCommentOutput(false);

        String executeAction = executeResult(result, new MultilineTestAction(), ActionSupport.SUCCESS);

        String expected = "{\"test\":\"" + part1 + "\",\"test2\":\"" + part2 + "\"}";
        Assert.assertEquals(expected, executeAction);
    }

    @Test
    public void testSetJsonResponse() throws Exception {
        JsonResult result = new JsonResult();
        result.setJsonResponse("{\"hello\":\"world\"}");

        String executeAction = executeResult(result, new SendToLoginAction(), ActionSupport.LOGIN);

        Assert.assertEquals("/* {\"hello\":\"world\"} */", executeAction);
    }

    private String executeResult(JsonResult result, Object action, String resultCode) throws Exception {
        response = new CapturingResponse();

        ActionContext context = ActionContext.of()
                .withServletResponse(response.asHttpServletResponse());
        ActionInvocation invocation = invocation(action, resultCode, context);

        result.doExecute(null, invocation);

        return response.body.toString();
    }

    private ActionInvocation invocation(final Object action, final String resultCode, final ActionContext context) {
        InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                String name = method.getName();

                if ("getAction".equals(name)) {
                    return action;
                }
                if ("getResultCode".equals(name)) {
                    return resultCode;
                }
                if ("getInvocationContext".equals(name)) {
                    return context;
                }
                if ("toString".equals(name)) {
                    return "JsonResultTest invocation";
                }
                if ("hashCode".equals(name)) {
                    return System.identityHashCode(proxy);
                }
                if ("equals".equals(name)) {
                    return proxy == args[0];
                }
                if (method.getReturnType().equals(Boolean.TYPE)) {
                    return false;
                }
                return null;
            }
        };

        return (ActionInvocation) Proxy.newProxyInstance(
                ActionInvocation.class.getClassLoader(),
                new Class<?>[]{ActionInvocation.class},
                handler);
    }

    private static class CapturingResponse {
        private final StringWriter body = new StringWriter();
        private final Map<String, String> headers = new LinkedHashMap<String, String>();
        private String characterEncoding;
        private String contentType;

        private HttpServletResponse asHttpServletResponse() {
            InvocationHandler handler = new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) {
                    String name = method.getName();

                    if ("setCharacterEncoding".equals(name)) {
                        characterEncoding = (String) args[0];
                        return null;
                    }
                    if ("setContentType".equals(name)) {
                        contentType = (String) args[0];
                        return null;
                    }
                    if ("setHeader".equals(name)) {
                        headers.put((String) args[0], (String) args[1]);
                        return null;
                    }
                    if ("getWriter".equals(name)) {
                        return new PrintWriter(body);
                    }
                    if ("sendError".equals(name)) {
                        return null;
                    }
                    if ("toString".equals(name)) {
                        return "JsonResultTest response";
                    }
                    if ("hashCode".equals(name)) {
                        return System.identityHashCode(proxy);
                    }
                    if ("equals".equals(name)) {
                        return proxy == args[0];
                    }
                    if (method.getReturnType().equals(Boolean.TYPE)) {
                        return false;
                    }
                    if (method.getReturnType().equals(Integer.TYPE)) {
                        return 0;
                    }
                    if (method.getReturnType().equals(Long.TYPE)) {
                        return 0L;
                    }
                    return null;
                }
            };

            return (HttpServletResponse) Proxy.newProxyInstance(
                    HttpServletResponse.class.getClassLoader(),
                    new Class<?>[]{HttpServletResponse.class},
                    handler);
        }
    }
}
