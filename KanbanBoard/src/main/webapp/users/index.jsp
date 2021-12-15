<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13/12/2021
  Time: 1:45 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>UserList</title>
<%--  <link rel="stylesheet" href="./css/style.css">--%>
  <link rel="stylesheet" href="users/css/style.css">
  <link rel="stylesheet" href="users/css/table-style.css">
  <link rel="stylesheet" href="users/css/logout.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
  #btn-logout,#btn-username {
    color: white;
    font-size: medium;
    padding: 3px;
  }
  #btn-board {
    color: white;
    font-size: medium;
    width: 100px;
    padding: 3px;
  }
  .btn {
    font-weight: bold;
    font-size: 15px;
  }
</style>
</head>

<body>
<!-- Masthead -->
<header class="masthead">

  <div class="boards-menu">

    <button id="btn-board" class="boards-btn btn"><i class="fab fa-trello boards-btn-icon"></i>Boards</button>


  </div>

  <div class="logo">

    <h1><i class="fab fa-trello logo-icon" aria-hidden="true"></i>Trello</h1>

  </div>

  <div class="user-settings">


    <button id="btn-username" class="user-settings-btn btn" aria-label="User Settings">
      <c:if test="${account != null}">

        <span> <c:out value="${account}"/> </span>
      </c:if>
    </button>
    <button id="btn-logout" class="user-settings-btn btn" onclick="window.location.href='/kanban'" aria-label="User Settings">
      <i class="fas fa-user-circle" aria-hidden="true">
          Log out
      </i>

    </button>

  </div>

</header>
<!-- End of masthead -->



<section>
  <!--for demo wrap-->
  <h1>User List</h1>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Status</th>
        <th></th>
      </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody>
      <c:forEach var="user" items="${users}">
        <tr>
          <td><c:out value="${user.id}"/></td>
          <td><c:out value="${user.name}"/></td>
          <td><c:out value="${user.email}"/></td>
          <td><c:out value="${user.role}"/></td>
          <td><c:out value="${user.status}"/></td>
          <c:choose>
            <c:when test="${user.status=='LOCK'}">
              <td>
                <button type="button" class="btn btn-outline-primary"
                        data-bs-toggle="modal" data-bs-target="#confirm-active"
                       style="text-align: center;width: 70px" id="btn-active-${user.email}"
                        onclick="active('${user.email}')">ACTIVE
                </button>
              </td>
            </c:when>
            <c:otherwise>
              <td>
                <button type="button" class="btn btn-outline-danger"
                        data-bs-toggle="modal" data-bs-target="#confirm-lock"
                        style="text-align: center;width: 70px" id="btn-lock-${user.email}"
                        onclick="lock('${user.email}')"> LOCK
                </button>
              </td>
            </c:otherwise>
          </c:choose>


        </tr>
      </c:forEach>

      </tbody>
    </table>
  </div>
</section>
<!-- End of lists container -->
<div class="modal fade" id="confirm-lock">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <!-- Modal body -->
      <div class="modal-body">
        <div class="row">
          <i class="fas fa-exclamation-circle fa-7x" style="color: red; margin-left: 200px;"></i>
        </div>

        <div id="textEx">
          <form action="/status?action=lock" method="post">
            <p>Are you sure you want to lock the selected user ?</p>
            <input type="text" id="lockUserEmail" name="lockUserEmail" style="display: none"/>
            <button type="submit" class="btn btn-primary" data-bs-dismiss="modal"  style="font-size: large">Yes
            </button>
            <button type="button" class="btn btn-danger" id="btn-cancel" data-bs-dismiss="modal"
                    style="font-size: large">Cancel
            </button>
          </form>

        </div>

      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="confirm-active">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <!-- Modal body -->
      <div class="modal-body">
        <div class="row">
          <i class="fas fa-exclamation-circle fa-7x" style="color: red; margin-left: 200px;"></i>
        </div>

        <div id="textEx">
          <form action="/status?action=active" method="post">
            <p>Are you sure you want to active the selected user ?</p>
            <input type="text" id="activeUserEmail" name="activeUserEmail" style="display: none"/>
            <button type="submit" class="btn btn-primary" data-bs-dismiss="modal" style="font-size: large">Yes
            </button>
            <button type="button" class="btn btn-danger" id="btn-cancel" data-bs-dismiss="modal"
                    style="font-size: large">Cancel
            </button>
          </form>

        </div>

      </div>
    </div>
  </div>
</div>
</body>
<script src="users/js/index.js"></script>
<script src="users/js/table.js"></script>
<script src="users/js/logout.js"></script>
<script>
  function lock(email) {
    document.getElementById("lockUserEmail").value = email;
  }
  function active(email) {
    document.getElementById("activeUserEmail").value = email;
  }
</script>
</html>
