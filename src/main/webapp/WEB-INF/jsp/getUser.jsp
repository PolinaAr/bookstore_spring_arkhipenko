<%@page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href=".../css/general.css" rel="stylesheet"/>
    <link href=".../css/mainPartOfPage.css" rel="stylesheet"/>
    <title>User</title>
</head>
<body>
    <div class="topPanel">
        <img src="pictures/logo.png" alt="Polina bookstore"/>
        <h1><a href="http://localhost:8080/users">User</a></h1>
    </div>
    <div class="mainPart">
        <h2>with id = ${user.id}</h2>
        <p>Name = ${user.name} </p>
        <p>Last name = ${user.lastName} </p>
        <p>Role = ${user.role.toString().toLowerCase()}</p>
        <p>Email = ${user.email} </p>
        <p>Password = ${user.password} </p>
        <p>Birthday = ${user.birthday} </p>
    </div>
</body>
</html>