<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin_simple.xml"/>


<%------------ DISPLAY Current Structure Logo only --------------%>
<c:choose>
    <c:when test="${empty currentAttrStruct}">
        <img class="${n}changeEtabPortlet currentStructLogoOnly"
            src="${currentAttrStructAlternative }" alt="Logo Portail" />
    </c:when>
    <c:otherwise>
        <img class="${n}changeEtabPortlet currentStructLogoOnly"
            src="${currentAttrStruct}" alt="Logo Structure" />
    </c:otherwise>
</c:choose>
