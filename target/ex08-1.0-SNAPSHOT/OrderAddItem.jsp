<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Alice's Restaurant</title>
</head>
<body>
<h2>JSP - Alice's Restaurant</h2>

<h3>Add ${category.description} to Order #${order.id}</h3>

<c:if test="${not empty error}"><p>${error}</p></c:if>

<table style="width: 600px; border: 1px solid black">
    <tr>
        <th>Description</th>
        <th>Price</th>
        <th>Vegetarian</th>
        <th>Qty</th>
        <th></th>
    </tr>
    <c:forEach var="mi" items="${items}">
        <tr>
            <td>${mi.description}</td>
            <td>${mi.price}</td>
            <td>${mi.vegetarian ? "Yes" : "No"}</td>
            <td colspan="2">
                <form method="post" action="${pageContext.request.contextPath}/Order/AddItem">
                    <input type="hidden" name="id" value="${order.id}">
                    <input type="hidden" name="cat" value="${category.id}">
                    <input type="hidden" name="menuItemId" value="${mi.id}">
                    <input name="qty" type="number" value="1" min="1" style="width: 60px; text-align: right">
                    <input type="submit" value="Add">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<br/>
<a href="Edit?id=${order.id}">Back to Order</a>

</body>
</html>
