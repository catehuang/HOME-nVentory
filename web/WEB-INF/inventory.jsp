<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <title>Home Inventory</title>
    </head>
    <body>
        <div class="page"> 
            <h1>Home Inventory</h1>
            <h3>Menu</h3>
            <div>
                <table class="menu">
                    <tr><td><a href="inventory">Inventory</a></td></tr>
                    <tr><td><a href="profile">Account</a></td></tr>
                    <tr><td><a href="admin">Admin</a></td></tr>
                    <tr><td><a href="login?logout">Logout</a></td></tr>
                </table>
            </div>

            <h3>Inventory for ${user.firstName} ${user.lastName}</h3>
            <table class="tableA">
                <tr>
                    <th>Category</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.category.getCategoryName()}</th>
                        <td>${item.itemName}</th>
                        <td>&#36;${item.price}</th>
                        <td>
                            <form method="post">
                                <input type="submit" value="Delete">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="key" value="${item.getItemId()}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${message eq 'deleted'}">
                <p>Successfully deleted item from inventory.</p>
            </c:if>   
            <c:if test="${message eq 'not_belong_to_you'}">
                <p>Cannot delete an item which does not belong to you.</p>
            </c:if>
            <c:if test="${message eq 'added'}">
                <p>An item has been successfully added.</p>
            </c:if>  

            <form method="POST" action="inventory">
                <h3>Add Item</h3>
                <table class="tableB">
                    <tr>
                        <td><label>Category</label></td>
                        <td>
                            <select name="category">
                                <c:forEach items="${categoryList}" var="c">
                                    <option value=${c.getCategoryId()}
                                            <c:if test="${c.equals(item.category)}">selected</c:if>
                                            >${c.getCategoryName()}</option>
                                </c:forEach>
                            </select>                        
                        </td>
                    </tr>
                    <tr>
                        <td><label>Item Name: </label></td>
                        <td><input type="text" name="item" value="${item.itemName}"></td>
                    </tr>
                    <tr>
                        <td rowspan="2"><label>Price: </label></td>
                        <td><input type="text" name="price" value="${item.price}"></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Save">
                            <input type="hidden" name="action" value="create">
                            <input type="hidden" name="key" value="${item}">
                        </td>

                    </tr>
                </table>
            </form>        
            <c:if test="${message eq 'invalid_input'}">
                <p>Error. Please enter all fields.</p> 
                <p>Hint: the range of price is 0 to 9999.</p>
            </c:if>   
        </div>
    </body>
</html>
