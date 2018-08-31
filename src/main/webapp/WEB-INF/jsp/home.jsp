<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin.xml"/>

<!-- i18n messages -->
<spring:message code="etab.change.action" var="msgEtabChangeAction" />
<spring:message code="etab.change.cancel" var="msgEtabChangeCancel" />
<spring:message code="etab.change.confirm" var="msgEtabChangeConfirm" />
<spring:message code="etab.change.title" var="msgEtabChangeTitle" />
<spring:message code="etab.change.message" var="msgEtabChangeMessage" />
<spring:message code="etab.current.message" var="msgEtabCurrentMessage" />
<span class="changeEtabPortletMessages">
    <span class="etabChangeTitle">${msgEtabChangeTitle}</span>
    <span class="etabChangeMessage">${msgEtabChangeMessage}</span>
    <span class="etabChangeAction">${msgEtabChangeAction}</span>
    <span class="etabChangeCancel">${msgEtabChangeCancel}</span>
    <span class="etabChangeConfirm">${msgEtabChangeConfirm}</span>
</span>

<portlet:actionURL var="changeStructAction" name="changeStruct" />
<portlet:actionURL var="newChangeStructAction" escapeXml="false">
    <portlet:param name="action" value="changeStruct"></portlet:param>
</portlet:actionURL>

<%------------ DISPLAY Current Etablissement only --------------%>

<c:if test="${displayCurrentStruct && !displayPortlet}">
    <span class="${n}currentStruct changeEtabPortlet">${currentStruct.displayName}</span>
</c:if>

<%------------ DISPLAY MODE 1 : Select Box ------------%>

<c:if test="${displayMode == 'All' || displayMode == '1'}">
<span class="${n}container1 changeEtabPortlet">
    <c:if test="${displayPortlet}">
        <form:form method="post" action="${changeStructAction}">
            <span>
                <form:select class="changeEtab-select" path="selectedStructId" >
                    <optgroup label="${msgEtabCurrentMessage}">
                        <form:option value="currentStruct" label="${currentStruct.displayName}" />
                    </optgroup>
                    <%-- <form:option value="EMPTY" label="" /> --%>
                    <optgroup label="${msgEtabChangeMessage}" >
                        <form:options items="${structs}" itemValue="id" itemLabel="displayName" />
                    </optgroup>
                </form:select>

                <input type="button" class="changeEtab-submit" value="${msgEtabChangeAction}" />
            </span>
        </form:form>
    </c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 2 : Anchor + Current etab ------------%>

<c:if test="${displayMode == 'All' || displayMode == '2'}">
<span class="${n}container2 changeEtabPortlet">
    <c:if test="${displayPortlet}">
        <div class="dropdown">
            <div class="dropMenuChangeEtab dropdown-toggle" data-toggle="dropdown">

                <div style="float:left;">
                        ${fn:substring(currentStruct.displayName, 0, 20)}
                </div>
                <div style="float:right;">
                    ▾
                </div>
            </div>
            <ul class="dropdown-menu ">
                <form:form method="post" commandName="command" action="${changeStructAction}">
                    <c:forEach items="${structs}" var="struct">
                        <div>
                            <label class="liDropChangeEtab">
                                <form:radiobutton id="${n}2radio${struct.id}" class="changeEtab-select" path="selectedStructId" value="${struct.id}" />
                                    ${struct.displayName}
                            </label>
                        </div>
                    </c:forEach>
                </form:form>
            </ul>
        </div>
    </c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 3 : Picture only ------------%>

<c:if test="${displayMode == 'All' || displayMode == '3'}">
<span class="${n}container3 changeEtabPortlet">
    <c:if test="${displayPortlet}">
    <span class="changeEtab-button">
        <c:url value="/images/swap-mini2.png" var="imgUrl" />
        <a href="#" onclick="return false;" title="${msgEtabChangeAction}">
            <span class="iconified" style="background: transparent url(${imgUrl}) scroll no-repeat left center;">
            </span>
        </a>
    </span>
    <div class="changeEtab-dialog">
        <div class="message">
            ${msgEtabChangeMessage}
        </div>
        <form:form method="post" action="${changeStructAction}">
            <c:forEach items="${structs}" var="struct">
            <div class="fl-widget">
                <div class="fl-widget-titlebar">
                    <form:radiobutton id="${n}3radio${struct.id}" class="changeEtab-select" path="selectedStructId" value="${etab.id}" />
                    <label for="${n}3radio${struct.id}">${struct.displayName}</label>
                </div>
            </div>
            </c:forEach>
        </form:form>
    </div>
    </c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 4 : Anchor only ------------%>

<c:if test="${displayMode == 'All' || displayMode == '4'}">
<span class="${n}container4 changeEtabPortlet">
    <c:if test="${displayPortlet}">
    <h1 class="changeEtab-button">
        <%--<a href="#" onclick="return false;" title="${msgEtabChangeAction}">--%>
            <span>${msgEtabChangeAction}</span>
        <%--</a>--%>
    </h1>
    <div class="changeEtab-dialog">
        <div class="message">
            ${msgEtabChangeMessage}
        </div>
        <form:form method="post" commandName="command" action="${newChangeStructAction}" role="form">
            <c:forEach items="${structs}" var="struct">
            <div class="fl-widget">
                <div class="fl-widget-titlebar">
                    <form:radiobutton id="${n}3radio${struct.id}" class="changeEtab-select" path="selectedStructId" value="${struct.id}" />
                    <label for="${n}3radio${struct.id}">${struct.displayName}</label>
                </div>
            </div>
            </c:forEach>
            <input type="button" class="btn btn-primary changeEtab-submit" value="${msgEtabChangeAction}" />
        </form:form>
    </div>
    </c:if>
</span>
</c:if>
<%@include file="/WEB-INF/jsp/scripts.jsp" %>