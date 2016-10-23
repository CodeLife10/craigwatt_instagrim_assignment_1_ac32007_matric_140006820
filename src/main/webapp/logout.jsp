<%-- 
    Document   : logout.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
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
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        
    </head>
    <body>
        <jsp:useBean
	id="logBean"
	scope="request"
	class="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn" />
        
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
                <div class="row">
                    <div class ="col-xs-12">
                        <h1>Logout</h1>
                        </br>
                    </div>
                </div>
                <div class="row">

                    <div class="col-xs-6">
                            <div style="text-align: right;">

                            <form method="POST"  action="Logout">
                                <input type="submit" value="Yes">
                            </form>
                                <a class="qwerty" href="/Instagrim">No</a>
                            </div>
                    </div>
                    <div class="col-xs-6">
                        <div style="text-align:left;">
                            <a style="color: black;" class="qwerty" href="/Instagrim">No</a>
                        </div>
                    </div>     
                </div>
            </div>
            <div class=""
        </section>
        
        <!--
        <header>
        <h1>InstaGrim ! </h1>
        <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul>
                
                <li><a href="/Instagrim/Images/majed">Sample Images</a></li>
            </ul>
        </nav>
       
        <article>
            <h3>Login</h3>
            <form method="POST"  action="Login">
                <ul>
                    <li>User Name <input type="text" name="username"></li>
                    <li>Password <input type="password" name="password"></li>
                </ul>
                <br/>
                <input type="submit" value="Login"> 
            </form>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
        -->
        
    </body>
</html>
