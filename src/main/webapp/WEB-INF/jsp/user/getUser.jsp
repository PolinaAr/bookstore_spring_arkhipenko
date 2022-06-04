<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet"/>
    <title>User</title>
</head>
<body>
    <div class="topPanel">
        <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
        <h1><a href="/users">User</a></h1>
    </div>
    <div class="mainPart">
        <h2>with id = ${user.id}</h2>
        <p>Name = ${user.name} </p>
        <p>Last name = ${user.lastName} </p>
        <p>Role = ${user.role.toString().toLowerCase()}</p>
        <p>Email = ${user.email} </p>
        <p>Password = ${user.password} </p>
        <p>Birthday = ${user.birthday} </p>
        <form action="/users/edit/${user.id}" method="get">
            <button>Edit user</button>
        </form>
        <form action="/users/delete/${user.id}" method="post">
            <button>Delete user</button>
        </form>
    </div>
</body>
</html>