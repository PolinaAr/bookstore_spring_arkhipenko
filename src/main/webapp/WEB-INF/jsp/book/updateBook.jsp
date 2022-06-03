<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href="/css/general.css" rel="stylesheet">
    <title>Update book</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="/pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Update book</h1>
</div>
<div class="mainPart">
    <p>Enter values</p>
    <form action="/books/${book.id}" method="post">
        <label for="isbn-input"> ISBN: </label><input id="isbn-input" name="isbn" type="text" value="${book.isbn}"/><br/>
        <label for="title-input">Title: </label><input id="title-input" name="title" type="text" value="${book.title}"/><br/>
        <label for="author-input">Author: </label><input id="author-input" name="author" type="text" value="${book.author}"/><br/>
        <label for="pages-input">Pages: </label><input id="pages-input" name="pages" type="number" value="${book.pages}"/><br/>
        <label for="cover-input">Cover: </label>
        <select id="cover-input" name="cover" required="required" >
            <option value="${book.cover}">Choose value</option>
            <option>Hard</option>
            <option>Soft</option>
            <option>Gift</option>
        </select> <br/>
        <label for="price-input">Price: </label><input id="price-input" name="price" value="${book.price}"/><br/>
        <input type="submit" value="Update"/>
    </form>
</div>
</body>
</html>