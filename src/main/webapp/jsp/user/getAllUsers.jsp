<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="style/general.css" rel="stylesheet">
    <link href="style/tables.css" rel="stylesheet">
    <title>Users</title>
</head>
<body>
    <div class="topPanel">
        <img src="pictures/logo.png" alt="Polina bookstore"/>
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
            <td><a href="http://localhost:8090/bookstore-arkhipenko/controller?command=user&id=${user.id}">${user.name}</a> </td>
            <td>${user.lastName}</td>
            <td>${user.role.toString().toLowerCase()}</td>
            <td>${user.birthday}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>