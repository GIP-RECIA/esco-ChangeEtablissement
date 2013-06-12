<%@include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="n"><portlet:namespace/></c:set>

<div id="${n}container" class="">

	<h1>Etabs list to change :</h1>
	<ul>
	<c:forEach items="${etabs}" var="etab">
		<li>
			<p>UAI: ${etab.uai}</p>
			<p>Name: ${etab.name}</p>
			<p>Desc: ${etab.description}</p>			
		</li>
	</c:forEach>
	</ul>
</div>