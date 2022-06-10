<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href="../css/general.css" rel="stylesheet">
    <title>Create book</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Create book</h1>
</div>
<div class="mainPart">
    <p>Enter values</p>
    <form action="/books" method="post">
        <label for="isbn-input"> ISBN: </label><input id="isbn-input" name="isbn" type="text" required/><br/>
        <label for="title-input">Title: </label><input id="title-input" name="title" type="text" required/><br/>
        <label for="author-input">Author: </label><input id="author-input" name="author" type="text" required/><br/>
        <label for="pages-input">Pages: </label><input id="pages-input" name="pages" type="number" required/><br/>
        <label for="cover-input">Cover: </label>
        <select id="cover-input" name="cover" required>
            <option value="">Choose value</option>
            <option>Hard</option>
            <option>Soft</option>
            <option>Gift</option>
        </select> <br/>
        <label for="price-input">Price: </label><input id="price-input" name="price" type="number" required/><br/>
        <input type="submit" value="Create"/>
    </form>
</div>
</body>
</html>