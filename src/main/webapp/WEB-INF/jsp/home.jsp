<%@include file="/WEB-INF/jsp/include.jsp" %>

<rs:aggregatedResources path="skin.xml"/>

<%@include file="/WEB-INF/jsp/scripts.jsp" %>

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
	
		<input type="submit" class="changeEtab-submit" value="Change Etab !" />
		</span>
	</form:form>
	</c:if>
</span>

<span id="${n}container2" class="changeEtabPortlet">
	<c:if test="${displayPortlet}">
	<span>
		Current etablissement : ${currentEtab.name} 
	</span>
	<span class="changeEtab-button"><a href="" onclick="return false;">Change Etab !</a></span>
	<div class="changeEtab-dialog">
		Etablissement(s) you can switch to :
		<ul>
			<c:forEach items="${etabs}" var="etab">
			<li>${etab.name}</li>
			</c:forEach>
		</ul>
	</div>
	</c:if>
</span>

<span id="${n}container3" class="changeEtabPortlet">
	<c:if test="${displayPortlet}">
	Test
	</c:if>
</span>