<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Alice's Restaurant</title>
</head>
<body>
<h2>JSP - Alice's Restaurant</h2>

<h3>Order Edit</h3>

<c:if test="${not empty error}"><p>${error}</p></c:if>

<%--'order' is provided by the Servlet as a Request Attribute--%>
<jsp:useBean id="order" type="ITSD.Entity.CustomerOrder" scope="request"/>

Order Details:
<form method="post">
    <input type="hidden" name="id" value="${order.id}">
    <table style="width: 500px; border: 1px solid black">
        <tr>
            <td>Order #</td>
            <td colspan="2">${order.id}</td>
        </tr>
        <tr>
            <td>Customer</td>
            <td colspan="2">
                ${order.customer.customerName}<c:if test="${order.customer.vegetarian}"> (Vegetarian)</c:if>
            </td>
        </tr>
        <tr>
            <td>Sub Total</td>
            <td colspan="2"><fmt:formatNumber value="${order.subTotal}" type="currency" currencySymbol="$"/></td>
        </tr>
        <tr>
            <td>Tip</td>
            <td><input name="tipPercent" type="number" value="${order.tipPercent}" style="width: 50px; text-align: right"> %</td>
            <td><fmt:formatNumber value="${order.tipAmount}" type="currency" currencySymbol="$"/></td>
        </tr>
        <tr>
            <td>Tax</td>
            <td><input name="taxPercent" type="number" value="${order.taxPercent}" style="width: 50px; text-align: right"> %</td>
            <td><fmt:formatNumber value="${order.taxAmount}" type="currency" currencySymbol="$"/></td>
        </tr>
        <tr>
            <td>Grand Total</td>
            <td colspan="2"><fmt:formatNumber value="${order.grandTotal}" type="currency" currencySymbol="$"/></td>
        </tr>
    </table>
    <input type="submit" value="Update Order">
</form>
<br/>

Order Items:
<form method="post">
    <input type="hidden" name="id" value="${order.id}">
    <table style="width: 500px; border: 1px solid black">
        <tr>
            <th style="width:50px">Qty</th>
            <th>Description</th>
            <th>Price (ea)</th>
        </tr>
        <c:forEach var="oi" items="${order.items}">
            <tr>
                <td><input name="qty_${oi.id}" value="${oi.quantity}" type="number" style="width: 50px; text-align: right"></td>
                <td>${oi.menuItem.description}<c:if test="${oi.menuItem.vegetarian}"> (V)</c:if></td>
                <td style="text-align: right"><fmt:formatNumber value="${oi.menuItem.price}" type="currency" currencySymbol="$"/></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Update Order">
</form>

<a href="AddItem?id=${order.id}&cat=APP"><button type="button">Add Appetizer</button></a>
<a href="AddItem?id=${order.id}&cat=ENT"><button type="button">Add Entree</button></a>
<a href="AddItem?id=${order.id}&cat=DES"><button type="button">Add Dessert</button></a>
<br/>
<br/>
<a href="List">Back to Order List</a>

</body>
</html>
