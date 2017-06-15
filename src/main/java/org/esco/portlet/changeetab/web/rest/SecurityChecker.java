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
