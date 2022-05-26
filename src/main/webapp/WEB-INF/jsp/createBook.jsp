<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href="../css/general.css" rel="stylesheet">
    <link href="../css/mainPartOfPage.css" rel="stylesheet">
    <title>Create book</title>
</head>
<body>
    <div class="topPanel">
        <img src="../pictures/logo.png" alt="Polina bookstore"/>
        <h1>Create book</h1>
    </div>
    <p>Enter values</p>
    <form action="/books" method="post">
        <label for="isbn-input"> ISBN: </label><input id="isbn-input" name="isbn" type="text"/><br/>
        <label for="title-input">Title: </label><input id="title-input" name="title" type="text"/><br/>
        <label for="author-input">Author: </label><input id="author-input" name="author" type="text"/><br/>
        <label for="pages-input">Pages: </label><input id="pages-input" name="pages" type="number"/><br/>
        <label for="cover-input">Role: </label>
        <select id="cover-input" name="cover" required="required">
            <option value="">Choose value</option>s
            <option>Hard</option>
            <option>Soft</option>
            <option>Gift</option>
        </select> <br/>
        <label for="price-input">Price: </label><input id="price-input" name="price" type=""/><br/>
        <input type="submit" value="Create"/>
    </form>
</body>
</html>