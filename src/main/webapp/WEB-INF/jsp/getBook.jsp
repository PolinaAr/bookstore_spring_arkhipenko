<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <title>Book</title>
</head>
<body>
    <div class="topPanel">
        <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1><a href="http://localhost:8080/books">Book</a></h1>
    </div>
    <div class="mainPart">
    <h2>with id = ${book.id}</h2>
        <p>ISBN = ${book.isbn}</p>
        <p>Title = ${book.title}</p>
        <p>Author = ${book.author}</p>
        <p>Pages = ${book.pages}</p>
        <p>Cover = ${book.cover.toString().toLowerCase()}</p>
        <p>Price = ${book.price}</p>
    </div>
</body>
</html>