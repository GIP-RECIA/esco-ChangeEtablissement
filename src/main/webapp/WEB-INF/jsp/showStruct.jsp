<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin_simple.xml"/>


<%------------ DISPLAY Current Etablissement only --------------%>
<span class="${n}currentEtabOnly changeEtabPortlet">${currentStruct.displayName}</span>

