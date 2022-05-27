<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <link href="../css/tables.css" rel="stylesheet">
    <title>Orders</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Orders</h1>
</div>
<table>
    <tr>
        <th>ID</th>
        <th>User</th>
        <th>Total cost</th>
        <th>Status</th>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td> ${order.id} </td>
            <td><a href="http://localhost:8080/orders/${order.id}"> ${order.userDto.getName()} ${order.userDto.getLastName()}</a></td>
            <td> ${order.totalCost} </td>
            <td> ${order.status} </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
