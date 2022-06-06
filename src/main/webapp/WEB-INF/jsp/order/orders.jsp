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
<div class="mainPart">
    <form action="/orders" method="get">
        <label for="sort-input">Sort: </label>
        <select id="sort-input" name="direction" required="required">
            <option value="ASC">Choose value</option>
            <option value="ASC">Ascending</option>
            <option value="DESC">Descending</option>
        </select>
        <label for="sortBy-input">Sort by: </label>
        <select id="sortBy-input" name="sortColumn" required="required">
            <option value="id">Choose value</option>
            <option value="id">id</option>
            <option value="user.name">user name</option>
            <option value="user.lastName">user lastname</option>
            <option value="totalCost">total cost</option>
        </select>
        <label for="show-input">Show on page by: </label>
        <select id="show-input" name="items" required="required">
            <option value="10">Choose value</option>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
        </select>
        <input type="submit" value="Sort"/>
    </form>
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
                <td><a href="/orders/${order.id}"> ${order.userDto.getName()} ${order.userDto.getLastName()}</a></td>
                <td> ${order.totalCost} </td>
                <td> ${order.status} </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
