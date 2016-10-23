<%-- 
    Document   : edit
    Created on : Oct 23, 2016, 6:42:07 PM
    Author     : craigwatt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%String show = (String) request.getAttribute("editp");
        %>
        <h1>Modify!</h1>
        <form method="POST"  action="ChangeProfileN">
           <%if (show.equals("namechange")){%>
            <p>First name:</p>
            <input  type="text" name="First Name">
            <br/>
            <p>Last name:</p>
            <input type="text" name ="Last Name">
            <br/>
            <%}%>
            <%if (show.equals("editemail")){%>
            <input  type="text" name="Email">
            <br/>
            <%}%>
            <%if (show.equals("addemail")){%>
            <input type text name="AEmail">
            <br/>
            <%}%>
            <br/>
            <input type="submit" value="Confirm">
        </form>
        
    </body>
    
</html>
