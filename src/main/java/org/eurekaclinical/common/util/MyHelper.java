package org.eurekaclinical.common.util;

/*-
 * #%L
 * Eureka! Clinical Common
 * %%
 * Copyright (C) 2016 - 2019 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.servlet.http.HttpServletRequest;

public class MyHelper {
	private final static Object LOCK = new Object();
	
	
    /**
     * session synchtonization lock
     * */
    public static Object getSessionLock(HttpServletRequest request, String lockName) {
        if (lockName == null) lockName = "SESSION_LOCK";
        Object result = request.getSession().getAttribute(lockName);
        if (result == null) {
            // only if there is no session-lock object in the session we apply the global lock
            synchronized (LOCK) {
                // as it can be that another thread has updated the session-lock object in the meantime, we have to read it again from the session and create it only if it is not there yet!
                result = request.getSession().getAttribute(lockName);
                if (result == null) {
                    result = new Object();
                    request.getSession().setAttribute(lockName, result);
                }
            }
        }
        return result;
    }
    
}
