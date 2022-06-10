<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href="../css/general.css" rel="stylesheet">
    <title>Create book</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="../pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Create user</h1>
</div>
<div class="mainPart">
    <p>Enter values</p>
    <form action="/users" method="post">
        <label for="name-input"> Name: </label><input id="name-input" name="name" type="text" required/><br/>
        <label for="lastname-input">Lastname: </label><input id="lastname-input" name="lastname" type="text" required/><br/>
        <label for="role-input">Role: </label>
        <select id="role-input" name="role" required>
            <option value="">Choose value</option>
            <option>Admin</option>
            <option>Manager</option>
            <option>Customer</option>
        </select> <br/>
        <label for="email-input">Email: </label><input id="email-input" name="email" type="text" required/><br/>
        <label for="password-input">Password: </label><input id="password-input" name="password" type="text" required/><br/>
        <label for="birthday-input">Birthday: </label><input id="birthday-input" name="birthday" type="date"
                                                             required min="1930-01-01" max="2012-01-01"/><br/>
        <input type="submit" value="Create"/>
    </form>
</div>
</body>
</html>