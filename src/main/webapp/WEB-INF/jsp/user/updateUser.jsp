<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <link href="/css/general.css" rel="stylesheet">
    <title>Update user</title>
</head>
<body>
<div class="topPanel">
    <a href="http://localhost:8080"><img src="/pictures/logo.png" alt="Polina bookstore"/></a>
    <h1>Update user</h1>
</div>
<div class="mainPart">
    <p>Enter values</p>
    <form action="/users/${user.id}" method="post">
        <label for="name-input"> Name: </label><input id="name-input" name="name" type="text" value="${user.name}"/><br/>
        <label for="lastname-input">Lastname: </label><input id="lastname-input" name="lastname" type="text" value="${user.lastName}"/><br/>
        <label for="role-input">Role: </label>
        <select id="role-input" name="role" required="required">
            <option value="${user.role.toString().toLowerCase()}">Choose value</option>s
            <option>Admin</option>
            <option>Manager</option>
            <option>Customer</option>
        </select> <br/>
        <label for="email-input">Email: </label><input id="email-input" name="email" type="text" value="${user.email}"/><br/>
        <label for="password-input">Password: </label><input id="password-input" name="password" type="text" value="${user.password}"/><br/>
        <label for="birthday-input">Birthday: </label><input id="birthday-input" name="birthday" type="date" value="${user.birthday}"/><br/>
        <input type="submit" value="Update"/>
    </form>
</div>
</body>
</html>