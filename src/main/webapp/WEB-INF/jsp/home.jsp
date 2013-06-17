<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin.xml"/>

<%@include file="/WEB-INF/jsp/scripts.jsp" %>

<!-- i18n messages -->
<spring:message code="etab.change.action" var="msgEtabChangeAction" />
<spring:message code="etab.change.cancel" var="msgEtabChangeCancel" />
<spring:message code="etab.change.title" var="msgEtabChangeTitle" />
<spring:message code="etab.change.message" var="msgEtabChangeMessage" />
<spring:message code="etab.current.message" var="msgEtabCurrentMessage" />

<portlet:actionURL var="changeEtabAction" name="changeEtab" />

<span id="${n}container1" class="changeEtabPortlet">
	<c:if test="${displayPortlet}">

	<form:form method="post" action="${changeEtabAction}">
		<span>
		<form:select class="changeEtab-select" path="selectedEtabId" >
			<form:option value="currentEtab" label="--- ${currentEtab.name} ---" />
			<form:option value="EMPTY" label="" />
			<form:options items="${etabs}" itemValue="id" itemLabel="name" />
		</form:select>

		<input type="submit" class="changeEtab-submit" value="${msgEtabChangeAction}" />
		</span>
	</form:form>
	</c:if>
</span>

<span id="${n}container2" class="changeEtabPortlet">
	<c:if test="${displayPortlet}">
	<span>
		${msgEtabCurrentMessage} ${currentEtab.name} 
	</span>
	<span class="changeEtab-button">
		<a href="#" onclick="return false;">${msgEtabChangeAction}</a>
	</span>
	<div class="changeEtab-dialog">
	
		${msgEtabChangeMessage}

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

<span id="${n}container3" class="changeEtabPortlet">
	<c:if test="${displayPortlet}">
	<span class="changeEtab-button">
		<a href="#" onclick="return false;" title="${msgEtabChangeAction}">
			<c:url value="/images/swap.png" var="imgUrl" />
			<img src="${imgUrl}" alt="${msgEtabChangeAction}" />
		</a>
	</span>
	<div class="changeEtab-dialog">
	
		${msgEtabChangeMessage}
		<br />
		
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


<span class="changeEtabPortletMessages">
	<span class="etabChangeTitle">${msgEtabChangeTitle}</span>
	<span class="etabChangeMessage">${msgEtabChangeMessage}</span>
	<span class="etabChangeAction">${msgEtabChangeAction}</span>
	<span class="etabChangeCancel">${msgEtabChangeCancel}</span>
</span>
