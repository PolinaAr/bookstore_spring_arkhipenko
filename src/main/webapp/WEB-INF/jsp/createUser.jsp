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
        <h1>Create user</h1>
    </div>
    <p>Enter values</p>
    <form action="/users" method="post">
        <label for="name-input"> Name: </label><input id="name-input" name="name" type="text"/><br/>
        <label for="lastname-input">Lastname: </label><input id="lastname-input" name="lastname" type="text"/><br/>
        <label for="role-input">Role: </label><input id="role-input" name="role" type="text"/><br/>
        <label for="email-input">Email: </label><input id="email-input" name="email" type="text"/><br/>
        <label for="password-input">Password: </label><input id="password-input" name="password" type="text"/><br/>
        <label for="birthday-input">Birthday: </label><input id="birthday-input" name="birthday" type="date"/><br/>
        <input type="submit" value="Create"/>
    </form>
</body>
</html>