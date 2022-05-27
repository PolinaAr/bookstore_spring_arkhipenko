<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <td>${user.id} </td>
            <td><a href="http://localhost:8080/users/${user.id}">${user.name}</a> </td>
            <td>${user.lastName}</td>
            <td>${user.role.toString().toLowerCase()}</td>
            <td>${user.birthday}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>