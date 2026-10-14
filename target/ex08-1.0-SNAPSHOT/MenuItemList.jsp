<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Alice's Restaurant</title>
</head>
<body>
<h2>JSP - Alice's Restaurant</h2>

<h3>Menu Items</h3>

<c:forEach var="cat" items="${categories}">
    <h4>${cat.description} (${cat.id})</h4>
    <table style="width: 600px; border: 1px solid black">
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Price</th>
            <th>Vegetarian</th>
            <th></th>
        </tr>
        <c:forEach var="mi" items="${cat.items}">
            <tr>
                <td>${mi.id}</td>
                <td>${mi.description}</td>
                <td>${mi.price}</td>
                <td>${mi.vegetarian ? "Yes" : "No"}</td>
                <td><a href="Edit?id=${mi.id}"><button>Edit</button></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>

</c:forEach>

<a href="${pageContext.request.contextPath}/">Home</a>

</body>
</html>
