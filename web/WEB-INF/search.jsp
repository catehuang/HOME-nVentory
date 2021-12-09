<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <title>HOME nVentory</title>
    </head>
    <body>
        <div class="nav">
            <ul>
                <li><a href="#" class="welcome">Hello ${user.firstName} ${user.lastName}</a></li>
                <li><a href="inventory">Inventory</a></li>
                <li><a href="account">Account</a></li>
                    <c:if test="${user.role.getRoleId() eq 1}">
                    <li><a href="admin">Manage Users</a></li>
                    <li><a href="category">Manage Categories</a></li>
                    </c:if>
                    <c:if test="${user.role.getRoleId() != 2}">
                    <li><a href="search">Search Items</a></li>
                    </c:if>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </div>
        <h1>Search Item</h1>   
        <div class="w40">
            <form method="get" action="search">
                <table class="left margin border">
                    <tr class="morepadding">
                        <td>Keyword</td>
                        <td><input type="text" name="keyword" value="${keyword}"</td>
                    </tr>
                </table>
            </form>
            <table class="left border">
                <tr class="border">
                    <th>Item Name</th>
                    <th>Owner</th>
                </tr>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.itemName}</td>
                        <td>${item.owner.getFirstName()} ${item.owner.getFirstName()}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
