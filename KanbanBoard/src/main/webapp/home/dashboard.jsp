<%@ page import="dao.WorkDAO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08/12/2021
  Time: 5:26 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="home/dashboard/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/home/dashboard/css/dropdown.css">
    <style>
        #btn-logout, #btn-username {
            color: white;
            font-size: medium;
            padding: 3px;
            width: 80px;
        }

        #add-new-wl {
            color: #0d6efd !important;
            border-color: #0d6efd !important;
        }

        #add-new-wl:hover {
            background-color: white;
        }
        #labelSelect {
            text-align: center;
            padding-top: 4px;
        }
        #select-workspace {
            padding: 4px;
            background-color: #2D8CFF;
            color: white;
            border: none;
            font-size: 13px;
            border-radius: 5px;
        }
        hr {
            color: black;
        }
        #ip_newWorkList {
        background-color: rgb(226,228,230);
        border-radius: 6px;
        }
        .displayNone {
            display: none;
        }
        #alert {
            position: absolute;
            top: 41px;
            margin-left: 500px;
            width: 200px;
            text-align: center;
            font-size: 13px;
            height: 60px;
        }
    </style>
</head>
<body>
<!-- Masthead -->
<header class="masthead">

    <div class="boards-menu">

        <button class="boards-btn btn"><i class="fab fa-trello boards-btn-icon"></i>Boards</button>

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
        <button id="btn-logout" class="user-settings-btn btn" onclick="window.location.href='/kanban'"
                aria-label="User Settings">
            <i class="fas fa-user-circle" aria-hidden="true">
                Log out
            </i>

        </button>
    </div>

</header>
<!-- End of masthead -->


<!-- Board info bar -->
<section class="board-info-bar">

    <div class="board-controls">

<%--        <button class="board-title btn">--%>
<%--            <h2>Web Development</h2>--%>
<%--        </button>--%>

<%--        <button class="star-btn btn" aria-label="Star Board">--%>
<%--            <i class="far fa-star" aria-hidden="true"></i>--%>
<%--        </button>--%>

<%--        <button class="personal-btn btn">Personal</button>--%>

<%--        <button class="private-btn btn"><i class="fas fa-briefcase private-btn-icon" aria-hidden="true"></i>Private--%>
<%--        </button>--%>
        <ul class="nav__menu">
            <li class="nav__menu-item"><a>Home</a></li>
            <li class="nav__menu-item"><a>Workspaces</a>
                <ul class="nav__submenu">
                            <c:forEach var="workspace" items="${workspaces}">
                                <li class="nav__submenu-item"> <a style="text-decoration: none; color: white" href="${pageContext.request.contextPath}/workspace?action=select&id=${workspace.id}"> <c:out value="${workspace.name}"/></a></li>
                            </c:forEach>
                </ul>
            </li>
            <li class="nav__menu-item"><a>Favorite</a>
                <ul class="nav__submenu">
                    <c:forEach var="workspace" items="${favoriteWorkspace}">
                        <li class="nav__submenu-item"> <a style="text-decoration: none; color: white" href="${pageContext.request.contextPath}/workspace?action=select&id=${workspace.id}"> <c:out value="${workspace.name}"/></a></li>
                    </c:forEach>
                </ul>
            </li>
            <li class="nav__menu-item"><a>About</a>
                <ul class="nav__submenu">
                    <li class="nav__submenu-item"> <a data-bs-toggle="modal" data-bs-target="#editWorkspace" onclick="editWorkspace('${thisWsp.name}',${thisWsp.id})" >Edit Workspace</a></li>
                    <li class="nav__submenu-item"> <a data-bs-toggle="modal" data-bs-target="#confirm-delete" onclick="deleteWorkspace(${thisWsp.id})">Delete Workspace</a></li>
                    <li class="nav__submenu-item"> <a data-bs-toggle="modal" data-bs-target="#addWorkspace">Add Workspace</a></li>
                    <li class="nav__submenu-item"> <a data-bs-toggle="modal" data-bs-target="#shareWorkspace" onclick="shareWorkspaceToUser(${thisWsp.id})">Share Workspace</a></li>
                </ul>
            </li>
            <li class="nav__menu-item"><a>Blog</a></li>
            <li class="nav__menu-item"><a>Contact</a></li>
             <c:if test="${alert != null}">
                <div class="alert alert-danger" role="alert" id="alert" >
                    <c:out value="${alert}"/>
                </div>
            </c:if>
        </ul>
    </div>


</section>
<!-- End of board info bar -->

<!-- Lists container -->
<section class="lists-container">

    <c:forEach var="worklist" items="${worklists}">

        <div class="list">

            <h3 class="list-title"><c:out value="${worklist.name}"/></h3>

            <ul class="list-items">
                <c:forEach var="work" items="${works.get(worklist.id)}">
                    <li><c:out value="${work.name}"/></li>
                </c:forEach>

            </ul>
            <div id="icon-create" onclick="createWorkspace()">
                <button class="add-card-btn btn">Add a card</button>
            </div>
        </div>
    </c:forEach>
<div id="btn_add_new_worklist" >
    <button class="add-list-btn btn" onclick="addWorklist()" >Add a list</button>
</div>




</section>
<!-- End of lists container -->
<%@include file="dashboard/modal/addWorkspace.jsp" %>
<%@include file="dashboard/modal/deleteWorkspace.jsp" %>
<%@include file="dashboard/modal/editWorkspaceModal.jsp" %>
<%@include file="dashboard/modal/shareWorkspace.jsp" %>

<script src="${pageContext.request.contextPath}/home/dashboard/js/dropdown.js"></script>
<script src="${pageContext.request.contextPath}/home/dashboard/js/index.js"></script>
</body>
</html>
