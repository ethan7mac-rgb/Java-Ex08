<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Alice's Restaurant</title>
</head>
<body>
<h2>JSP - Alice's Restaurant</h2>

<h3>Order List</h3>

<table>
    <tr><th>Order #</th><th>Customer</th></tr>
    <c:forEach var="o" items="${list}">
        <tr>
            <td>${o.id}</td>
            <td>${o.customer.customerName}</td>
            <td><a href="Edit?id=${o.id}"><button>Edit</button></a></td>
        </tr>
    </c:forEach>


</table>

<br/>
<a href="${pageContext.request.contextPath}/">Home</a>

</body>
</html>
