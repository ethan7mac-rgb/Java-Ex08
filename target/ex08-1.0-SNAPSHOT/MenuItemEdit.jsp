<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Alice's Restaurant</title>
</head>
<body>
<h2>JSP - Alice's Restaurant</h2>

<h3>Menu Item Edit</h3>
<jsp:useBean id="mi" type="ITSD.Entity.MenuItem" scope="request"/>
<jsp:useBean id="categoryList" type="java.util.List<ITSD.Entity.MenuCategory>" scope="request"/>
<c:if test="${not empty error}">
    <p role="alert"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/MenuItem/Edit">
    <input type="hidden" name="id" value="<c:out value='${mi.id}'/>"/>

    <label for="description">Description:</label><br/>
    <input id="description" type="text" name="description"
           value="<c:out value='${mi.description}'/>" required/>
    <br/><br/>

    <label for="price">Price:</label><br/>
    <input id="price" type="number" name="price" min="0" step="0.01"
           value="<c:out value='${mi.price}'/>" required/>
    <br/><br/>

    <label for="vegetarian">
        <input id="vegetarian" type="checkbox" name="vegetarian" value="true"
               <c:if test="${mi.vegetarian}">checked</c:if>/>
        Vegetarian
    </label>
    <br/><br/>

    <label for="categoryID">Category:</label><br/>
    <select id="categoryID" name="categoryID" required>
        <c:forEach var="mc" items="${categoryList}">
            <option value="<c:out value='${mc.id}'/>"
                <c:if test="${mi.category.id == mc.id}">selected</c:if>>
                <c:out value="${mc.description}"/>
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <button type="submit">Save</button>
</form>
</body>
</html>
