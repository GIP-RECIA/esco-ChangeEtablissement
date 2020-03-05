package org.esco.portlet.changeetab.service.impl;

import org.esco.portlet.changeetab.service.ISecurityChecker;

import javax.servlet.http.HttpServletRequest;

public class MockedSecurityChecker implements ISecurityChecker {

    @Override
    public boolean isSecureAccess(HttpServletRequest request) {
        return true;
    }
}
