<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <link href="../css/tables.css" rel="stylesheet">
    <title>Books</title>
</head>
<body>
    <div class="topPanel">
        <img src="../pictures/logo.png" alt="Polina bookstore"/>
        <h1>Books</h1>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>ISBN</th>
            <th>Title</th>
            <th>Author</th>
        </tr>
        <c:forEach items="${books}" var="book">
        <tr>
            <td> ${book.id} </td>
            <td> ${book.isbn} </td>
            <td><a href="http://localhost:8080/books/${book.id}"> ${book.title} </a></td>
            <td> ${book.author} </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>