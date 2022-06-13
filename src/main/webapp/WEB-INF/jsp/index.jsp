<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <title>Polina bookstore</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Polina bookstore</h1>
</div>
<div class="mainPart">Please, choose a section
    <c:if test="${sessionScope.user == null}">
    <form action="/users/create">
        <button>Register</button>
    </form>
    <form action="/users/login">
        <button>Login</button>
    </form></c:if>
    <c:if test="${sessionScope.user != null}">
        <form action="/users/logout">
            <button>Log out</button>
        </form>
    </c:if>
</div>
<div class="blocks">
    <div class="chapter">
        <p class="name">Books</p>
        <a href="http://localhost:8080/books">Click here to see all books</a>
    </div>
    <div class="chapter">
        <p class="name">Users</p>
        <a href="http://localhost:8080/users">Click here to see all users</a>
    </div>
    <div class="chapter">
        <p class="name">Orders</p>
        <a href="http://localhost:8080/orders">Click here to see all orders</a>
    </div>
</div>
</body>
</html>