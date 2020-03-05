package org.esco.portlet.changeetab.service;

import javax.servlet.http.HttpServletRequest;

public interface ISecurityChecker {
    boolean isSecureAccess(HttpServletRequest request);
}
