<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="queryResponse" type="org.apache.solr.client.solrj.response.QueryResponse"--%>
<html>
<body>

<hr/>
<h1>Facets</h1>
<c:forEach var="facetField" items="${queryResponse.facetFields}">
   <%--@elvariable id="facetField" type="org.apache.solr.client.solrj.response.FacetField"--%>
   <h2>${facetField.name}</h2>
   <c:forEach var="count" items="${facetField.values}">
      <%--@elvariable id="count" type="org.apache.solr.client.solrj.response.FacetField.Count"--%>
      <p>${count.name} (${count.count}) -- ${count.asFilterQuery}</p>
   </c:forEach>
</c:forEach>
</body>
</html>
