/**
 * Copyright (C) 2017 GIP RECIA http://www.recia.fr
 * @Author (C) 2013 Maxime Bossard <mxbossard@gmail.com>
 * @Author (C) 2016 Julien Gribonvald <julien.gribonvald@recia.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esco.portlet.changeetab.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Created by jgribonvald on 14/06/17.
 */
public class SecurityChecker {

    private Set<String> ips = new HashSet<>();

    private List<RequestMatcher> matchers = new ArrayList<>();
    private boolean initialized = false;

    public SecurityChecker() {
        super();
    }
    public SecurityChecker(Set<String> ips) {
        this.ips = ips;
    }

    public void setIps(final String ips) {
        String[] tmp = ips.replaceAll("\\s", "").split(",");
        this.ips = new HashSet<>(Arrays.asList(tmp));
    }

    public boolean isSecureAccess(final HttpServletRequest request){
        if (matchers.isEmpty() || !initialized) {
            for (String ip: ips) {
                matchers.add(new IpAddressMatcher(ip));
            }
        }

        for (RequestMatcher rm: matchers) {
            if (rm.matches(request)) return true;
        }
        return false;
    }
}
