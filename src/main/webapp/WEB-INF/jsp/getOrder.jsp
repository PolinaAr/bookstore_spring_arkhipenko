<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <link href="../css/mainPartOfPage.css" rel="stylesheet">
    <title>Order</title>
</head>
<body>
    <div class="topPanel">
        <img src="../pictures/logo.png" alt="Polina bookstore" />
    <h1><a href="http://localhost:8080/orders">Order</a></h1>
    </div>
    <div class="mainPart">
    <h2>with id = ${order.id}</h2>
        <p>User name = ${order.userDto.getName()}</p>
        <p>User lastname = ${order.userDto.getLastName()}</p>
        <p>Shopping list:</br>
            <c:forEach items="${items}" var="item">
                &nbsp&nbsp&nbsp&nbsp<a href="http://localhost:8080/books/${item.bookDto.getId()}">${item.bookDto.getId()}</a>.
                ${item.bookDto.getAuthor()} "${item.bookDto.getTitle()}"</br>
        </c:forEach> </p>
        <p>Order date = ${order.timestamp}</p>
        <p>Status = ${order.status.toString().toLowerCase()}</p>
    </div>
</body>
</html>