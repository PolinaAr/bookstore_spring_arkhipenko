<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <title>Book</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1><a href="/books">Book</a></h1>
</div>
<div class="mainPart">
    <h2>with id = ${book.id}</h2>
    <p>ISBN = ${book.isbn}</p>
    <p>Title = <c:out value="${book.title}"/></p>
    <p>Author = <c:out value="${book.author}"/></p>
    <p>Pages = ${book.pages}</p>
    <p>Cover = ${book.cover.toString().toLowerCase()}</p>
    <p>Price = ${book.price}</p>
    <form action="/books/edit/${book.id}" method="get">
        <button>Edit book</button>
    </form>
    <form action="/books/delete/${book.id}" method="post">
        <button>Delete book</button>
    </form>
</div>
</body>
</html>