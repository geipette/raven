<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="queryResponse" type="org.apache.solr.client.solrj.response.QueryResponse"--%>
<%--@elvariable id="searchResponse" type="no.guttab.raven.webapp.controller.SearchResponse"--%>
<html>

<body>
<h2>Selected</h2>
<c:forEach var="item" items="${searchResponse.navigators.selectedNavigators}">
   <%--@elvariable id="item" type="no.guttab.raven.search.response.SingleSelectNavigator"--%>
   <p><a href="${item.selectedItem.url}">${item.selectedItem.name} (${item.selectedItem.count})</a> <a href="${item.selectedItem.deselectUrl}">x-deselect</a></p>
</c:forEach>

<h2>Unselected</h2>
<c:forEach var="navigator" items="${searchResponse.navigators.navigators}">
   <h3></h3>
   <c:forEach var="item" items="${navigator.items}">
      <%--@elvariable id="item" type="no.guttab.raven.search.response.SingleSelectNavigatorItem"--%>
      <p><a href="${item.url}">${item.name} (${item.count})</a></p>
   </c:forEach>
</c:forEach>


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
