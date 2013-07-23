<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin.xml"/>

<%@include file="/WEB-INF/jsp/scripts.jsp" %>

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

<portlet:actionURL var="changeEtabAction" name="changeEtab" />

<%------------ DISPLAY MODE 1 ------------%>

<c:if test="${displayMode == 'All' || displayMode == '1'}">
<span class="${n}container1 changeEtabPortlet">
	<c:if test="${displayPortlet}">

	<form:form method="post" action="${changeEtabAction}">
		<span>
		<form:select class="changeEtab-select" path="selectedEtabId" >
			<optgroup label="${msgEtabCurrentMessage}">
				<form:option value="currentEtab" label="--- ${currentEtab.name} ---" />
			</optgroup>
			<%-- <form:option value="EMPTY" label="" /> --%>
			<optgroup label="${msgEtabChangeMessage}" >
				<form:options items="${etabs}" itemValue="id" itemLabel="name" />
			</optgroup>
		</form:select>

		<input type="button" class="changeEtab-submit" value="${msgEtabChangeAction}" />
		</span>
	</form:form>
	</c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 2 ------------%>

<c:if test="${displayMode == 'All' || displayMode == '2'}">
<span class="${n}container2 changeEtabPortlet">
	<c:if test="${displayPortlet}">
	<span>
		${msgEtabCurrentMessage} ${currentEtab.name} 
	</span>
	<span class="changeEtab-button">
		<a href="#" onclick="return false;">${msgEtabChangeAction}</a>
	</span>
	<div class="changeEtab-dialog">
		<div class="message">
			${msgEtabChangeMessage}
		</div>
		<form:form method="post" action="${changeEtabAction}">
			<c:forEach items="${etabs}" var="etab">
			<div class="fl-widget">
				<div class="">
					<form:radiobutton id="${n}2radio${etab.id}" class="changeEtab-select" path="selectedEtabId" value="${etab.id}" /> 
					<label for="${n}2radio${etab.id}">${etab.name}</label>
				</div>
			</div>
			</c:forEach>
		</form:form>
	</div>
	</c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 3 ------------%>

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
		<form:form method="post" action="${changeEtabAction}">
			<c:forEach items="${etabs}" var="etab">
			<div class="fl-widget">
				<div class="fl-widget-titlebar">
					<form:radiobutton id="${n}3radio${etab.id}" class="changeEtab-select" path="selectedEtabId" value="${etab.id}" /> 
					<label for="${n}3radio${etab.id}">${etab.name}</label>
				</div>
			</div>
			</c:forEach>
		</form:form>
	</div>
	</c:if>
</span>
</c:if>

<%------------ DISPLAY MODE 4 ------------%>

<c:if test="${displayMode == 'All' || displayMode == '4'}">
<span class="${n}container4 changeEtabPortlet">
	<c:if test="${displayPortlet}">
	<span class="changeEtab-button">
		<a href="#" onclick="return false;" title="${msgEtabChangeAction}">
			<span>${msgEtabChangeAction}</span>
		</a>
	</span>
	<div class="changeEtab-dialog">
		<div class="message">
			${msgEtabChangeMessage}
		</div>
		<form:form method="post" action="${changeEtabAction}">
			<c:forEach items="${etabs}" var="etab">
			<div class="fl-widget">
				<div class="fl-widget-titlebar">
					<form:radiobutton id="${n}3radio${etab.id}" class="changeEtab-select" path="selectedEtabId" value="${etab.id}" /> 
					<label for="${n}3radio${etab.id}">${etab.name}</label>
				</div>
			</div>
			</c:forEach>
		</form:form>
	</div>
	</c:if>
</span>
</c:if>