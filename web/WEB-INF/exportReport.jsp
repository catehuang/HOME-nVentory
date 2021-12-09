<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory</title>
    </head>
    <body>
        <h2>Summary of all users and their items</h2>
        <form method="post" action="report">
            <table class="border">
                <tr class="border">
                    <th>Name</th>
                    <th>Number of items</th>
                    <th>Total value</th>
                </tr>

                <c:forEach items="${all_users}" var="each_user">
                    <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "inline; filename="+ "report.xls");
                    %>                    
                    <c:set var="total_number" value="${0}"/>
                    <c:set var="total_value" value="${0}"/>
                    <tr>
                        <td>${each_user.firstName} ${each_user.lastName}</td>
                        <c:forEach items="${each_user.getItemList()}" var="each_item">      
                            <c:set var="total_number" value="${total_number + 1}"/>
                            <c:set var="total_value" value="${total_value + each_item.price}"/>
                        </c:forEach>
                        <td>${total_number}</td>
                        <td>${total_value}</td>
                    <tr>
                    </c:forEach>
                    <tr><td colspan="4">
                            <input type="hidden" name="action" value="export">
                            <input type="submit" value="Export">
                        </td>
                    </tr>        
            </table>
        </form> 
    </body>
</html>
