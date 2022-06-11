<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/general.css" rel="stylesheet">
    <title>Login</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Error</h1>
</div >
<div class="mainPart">
    <p>Log in</p>
    <form action="/users/login" method="post">
        <label for="email-input">Email: </label><input id="email-input" name="email" type="text" required/><br/>
        <label for="password-input">Password: </label><input id="password-input" name="password" type="text" required/><br/>
        <input type="submit" value="Log in"/>
    </form>
</div>
</body>
</html>