<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <link href="../css/tables.css" rel="stylesheet">
    <title>Users</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Users</h1>
</div>
<div class="mainPart">
    <form action="/users/create">
        <button>Add user</button>
    </form>
    <form action="/users" method="get">
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
            <option value="name">name</option>
            <option value="lastName">lastname</option>
            <option value="birthday">birthday</option>
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
            <td>ID</td>
            <td>Name</td>
            <td>Last Name</td>
            <td>Role</td>
            <td>Birthday</td>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><a href="/users/${user.id}"><c:out value="${user.name}"/></a></td>
                <td><c:out value="${user.lastName}"/></td>
                <td>${user.role.toString().toLowerCase()}</td>
                <td>${user.birthday}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>