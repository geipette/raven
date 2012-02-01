<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="queryResponse" type="org.apache.solr.client.solrj.response.QueryResponse"--%>
<%--@elvariable id="searchResponse" type="no.guttab.raven.webapp.controller.DemoSearchResponse"--%>
<html>

<body>
<h2>Selected</h2>
<c:forEach var="navigator" items="${searchResponse.selectedNavigators}">
   <c:forEach var="item" items="${navigator.selectedItems}">
      <p><a href="${item.url}">${item.name} (${item.count})</a> <a href="${item.deselectUrl}">(x) - deselect</a></p>
   </c:forEach>
</c:forEach>

<h2>Unselected</h2>
<c:forEach var="navigator" items="${searchResponse.navigators}">
   <h3>${navigator.displayName}</h3>
   <c:forEach var="item" items="${navigator.items}">
      <%--@elvariable id="item" type="no.guttab.raven.search.response.navigators.select.SelectNavigatorItem"--%>
      <p><a href="${item.url}">${item.name} (${item.count})</a></p>
   </c:forEach>
</c:forEach>


<%--<hr/>--%>
<%--<h1>Facets</h1>--%>
<%--<c:forEach var="facetField" items="${queryResponse.facetFields}">--%>
<%--&lt;%&ndash;@elvariable id="facetField" type="org.apache.solr.client.solrj.response.FacetField"&ndash;%&gt;--%>
<%--<h2>${facetField.name}</h2>--%>
<%--<c:forEach var="count" items="${facetField.values}">--%>
<%--&lt;%&ndash;@elvariable id="count" type="org.apache.solr.client.solrj.response.FacetField.Count"&ndash;%&gt;--%>
<%--<p>${count.name} (${count.count}) -- ${count.asFilterQuery}</p>--%>
<%--</c:forEach>--%>
<%--</c:forEach>--%>
</body>
</html>
