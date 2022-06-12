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
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Books</h1>
</div>
<div class="mainPart">
    <form action="/books/create">
        <button>Add book</button>
    </form>
    <form action="/books" method="get">
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
            <option value="isbn">isbn</option>
            <option value="title">title</option>
            <option value="author">author</option>
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
                <th>ISBN</th>
                <th>Title</th>
                <th>Author</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td> ${book.id} </td>
                    <td> ${book.isbn} </td>
                    <td><a href="/books/${book.id}"> <c:out value="${book.title}"/> </a></td>
                    <td> <c:out value="${book.author}"/> </td>
                </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>