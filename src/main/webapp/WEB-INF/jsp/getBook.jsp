<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="style/general.css" rel="stylesheet">
    <link href="style/mainPartOfPage.css" rel="stylesheet">
    <title>Book</title>
</head>
<body>
    <div class="topPanel">
        <img src="pictures/logo.png" alt="Polina bookstore" />
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