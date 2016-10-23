<%-- 
    Document   : index.jsp
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Instagrim</title>
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/imagelightbox.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/main.css">
        <link href="css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button data-target=".navbar-collapse" data-toggle="collapse" class="navbar-toggle" type="button" style="color:#888888">
                        <i class="fa fa-3x fa-bars"></i>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-left" href="/Instagrim" style="color:graytext; font-size: x-large;">Instagrim</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>
                        <li>
                            <%if (lg!= null){
                                if(lg.getlogedin()==true){%>
                                    <a class="scroll" href="/Instagrim/Profile/<%=lg.getUsername()%>" style="color:black;">Profile</a>
                              <%}}%>       
                        </li>
                        <li>
                            <%if (lg!= null){
                                if(lg.getlogedin()==true){%>
                                    <a class="scroll" href="/Instagrim/Upload" style="color:black;">Upload</a>
                              <%}
                                else{%><a class="scroll" href="/Instagrim/Login" style="color:black;">Upload</a><%}
                             }
                             else{%><a class="scroll" href="/Instagrim/Login" style="color:black;">Upload</a><%}%>                  
                        </li>
                        <li>
                            <%if (lg!= null){
                                if(lg.getlogedin()==true){%>
                                    <a class="scroll" href="/Instagrim/Images/<%=lg.getUsername()%>" style="color:black;">Your Images</a>
                              <%}
                                else{%><a class="scroll" href="/Instagrim/Login" style="color:black;">Your Images</a><%}       
                             }
                            else{%> <a class="scroll" href="/Instagrim/Login" style="color:black;">Your Images</a><%}%>
                        </li>
                        <li>
                           <%if (lg!= null){
                                if(lg.getlogedin()==false){%>
                            <a class="scroll" href="/Instagrim/Register" style="color:black;">Register</a>
                                <%}
                            }
                            if (lg==null){%>
                                <a class="scroll" href="/Instagrim/Register" style="color:black;">Register</a>
                            <%}%>
                        </li>
                        <li>
                            
                        <%
                            if(lg!= null) {
                                if(lg.getlogedin()==true){%>
                                <div class ="col-lg-6">
                                    <p style="font-size: 0.75em;"><%=lg.getUsername()%></p>
                                </div>
                                <div class ="col-lg-6">
                                    <a class="scroll" href="/Instagrim/Logout" style=" font-size: 0.75em; color:black;">Logout</a></div><%}
                                else{%><a class="scroll" href="/Instagrim/Login" style="color:black;">Login</a><%
                                }
                            }
                            else{
                                %><a class="scroll" href="/Instagrim/Login" style="color:black;">Login</a><%
                            }%>
                        </li>                       
                    </ul>
                </div>
            </div>
                <div class="container-fluid">
                    <div class="col-xs-12" style="background-color:#888888;">
                    </div>
                </div>
            </div>
        </nav>
        <section>
            <div class ="container centerpeice">
                <div class="col-xs-12">
                    <p style=" display: block; overflow: auto; text-align: center; color:black;">WELCOME</p>
                </div>
                <div class="col-xs-12">
                    <p style=" display: block; overflow: auto; text-align: center; color:black;">WELCOME</p>
                      <%if(request.getAttribute("alertMsg")=="Logout successful"){%>
                            <p style=" display: block; overflow: auto; text-align: center; color:black;">LOGOUT SUCCESSFUL</p>
                        <%}%>
                </div>
            </div>
        </section>
    </body>
</html>


<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
            <h1>InstaGrim ! </h1>
            <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul>

               
                <li><a href="upload.jsp">Upload</a></li>
                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>

                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                    <%}
                            }else{
                                %>
                 <li><a href="register.jsp">Register</a></li>
                <li><a href="login.jsp">Login</a></li>
                <%
                                        
                            
                    }%>
            </ul>
        </nav>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <li>&COPY; Andy C</li>
            </ul>
        </footer>
    </body>
</html>
--%>