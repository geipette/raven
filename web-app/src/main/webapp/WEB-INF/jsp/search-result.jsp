<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="queryResponse" type="org.apache.solr.client.solrj.response.QueryResponse"--%>
<%--@elvariable id="searchResponse" type="no.guttab.raven.search.response.SearchResponse"--%>
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

<h2>Results</h2>

<p>result count: ${searchResponse.resultCount}</p>
<c:forEach var="document" items="${searchResponse.documents}">
   <%--@elvariable id="document" type="no.guttab.raven.webapp.controller.DemoDocument"--%>
   <hr/>
   <p>${document.name}</p>

   <p>${document.manufacturer}</p>

   <p>${document.manufacturedDate}</p>
   <h6>Categories</h6>
   <ul>
      <c:forEach var="cat" items="${document.categories}">
         <li>${cat}</li>
      </c:forEach>
   </ul>
   <h6>Features</h6>
   <ul>
      <c:forEach var="feature" items="${document.features}">
         <li>${feature}</li>
      </c:forEach>
   </ul>

</c:forEach>

</body>
</html>
