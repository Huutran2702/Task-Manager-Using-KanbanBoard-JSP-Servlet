<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07/12/2021
  Time: 4:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Sign Up Form by Colorlib</title>

  <!-- Font Icon -->
  <link rel="stylesheet" href="/assets/fonts/material-icon/css/material-design-iconic-font.min.css">

  <!-- Main css -->
  <link rel="stylesheet" href="/assets/css/style.css">
  <link rel="stylesheet" href="./bootstrap5/css/bootstrap.min.css">
  <style>
    .main{
      padding: 50px 0;
    }
  </style>
</head>
<body>
<%
  String message = (String) request.getAttribute("message");
  if (message==null) {
    message = "";
  }
  String styleName = (String) request.getAttribute("style");

%>
<div class="main">


  <!-- Sing in  Form -->
  <section class="sign-in">
    <div class="container">
      <div class="signin-content">
        <div class="signin-image">
          <figure><img src="/assets/images/signin-image.jpg" alt="sign in image"></figure>
          <a href="/kanban?action=signup" class="signup-image-link">Create an account</a>
        </div>

        <div class="signin-form">
          <h2 class="form-title">Sign in</h2>
          <div class="alert <%=styleName%>">
            <strong><%=message%></strong>
          </div>
          <form action="/kanban?action=home" method="POST" class="register-form" id="login-form">
            <div class="form-group">
              <label for="your_email"><i class="zmdi zmdi-account material-icons-name"></i></label>
              <input type="text" name="your_email" id="your_email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required placeholder="Your Email"/>
            </div>
            <div class="form-group">
              <label for="your_pass"><i class="zmdi zmdi-lock"></i></label>
              <input type="password" name="your_pass" id="your_pass" required placeholder="Password"/>
            </div>
            <div class="form-group">
              <input type="checkbox" name="remember-me" id="remember-me" class="agree-term" />
              <label for="remember-me" class="label-agree-term"><span><span></span></span>Remember me</label>
            </div>
            <div class="form-group form-button">
              <input type="submit" name="signin" id="signin" class="form-submit" value="Log in"/>
            </div>
          </form>

        </div>
      </div>
    </div>
  </section>

</div>

<!-- JS -->
<script src="/assets/vendor/jquery/jquery.min.js"></script>
<script src="/assets/js/main.js"></script>
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>
