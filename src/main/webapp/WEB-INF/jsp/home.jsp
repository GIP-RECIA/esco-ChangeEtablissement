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

<portlet:actionURL var="changeEtabAction" name="changeEtab" />

<%------------ DISPLAY Current Etablissement only --------------%>

<c:if test="${displayCurrentEtab && !displayPortlet}">
	<span class="${n}currentEtab changeEtabPortlet">${currentEtab.displayName}</span>
</c:if>

<%------------ DISPLAY MODE 1 : Select Box ------------%>

<c:if test="${displayMode == 'All' || displayMode == '1'}">
<span class="${n}container1 changeEtabPortlet">
	<c:if test="${displayPortlet}">
		<form:form method="post" action="${changeEtabAction}">
		<span>
		<form:select class="changeEtab-select" path="selectedEtabCode" >
			<optgroup label="${msgEtabCurrentMessage}">
				<form:option value="currentEtab" label="${currentEtab.displayName}" />
			</optgroup>
			<%-- <form:option value="EMPTY" label="" /> --%>
			<optgroup label="${msgEtabChangeMessage}" >
				<form:options items="${etabs}" itemValue="code" itemLabel="displayName" />
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
						${fn:substring(currentEtab.displayName, 0, 20)}
				</div>
				<div style="float:right;">
					▾
				</div>
			</div>
			<ul class="dropdown-menu ">
				<form:form method="post" commandName="command" action="${changeEtabAction}">
					<c:forEach items="${etabs}" var="etab">
						<div>
							<label class="liDropChangeEtab">
								<form:radiobutton id="${n}2radio${etab.code}" class="changeEtab-select" path="selectedEtabCode" value="${etab.code}" />
									${etab.displayName}
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
		<form:form method="post" action="${changeEtabAction}">
			<c:forEach items="${etabs}" var="etab">
			<div class="fl-widget">
				<div class="fl-widget-titlebar">
					<form:radiobutton id="${n}3radio${etab.code}" class="changeEtab-select" path="selectedEtabCode" value="${etab.code}" />
					<label for="${n}3radio${etab.code}">${etab.displayName}</label>
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
					<form:radiobutton id="${n}3radio${etab.code}" class="changeEtab-select" path="selectedEtabCode" value="${etab.code}" />
					<label for="${n}3radio${etab.code}">${etab.displayName}</label>
				</div>
			</div>
			</c:forEach>
		</form:form>
	</div>
	</c:if>
</span>
</c:if>
<%@include file="/WEB-INF/jsp/scripts.jsp" %>