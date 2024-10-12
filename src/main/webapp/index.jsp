<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>File Upload Management</title>
    <style>
        body {
            background-color: #eef2f3;
        }
        .container {
            margin-top: 50px;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
            padding: 20px;
            background-color: #fff;
        }
        .alert {
            margin-top: 20px;
        }
        .navbar {
            justify-content: center;
        }
        .navbar-brand {
            font-size: 2rem;
        }
        .btn-back {
            background-color: dodgerblue;
            color: white;
            border: none;
        }
        .btn-back:hover {
            background-color: #5a6268;
            color: white;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand font-weight-bold" href="#">File Management</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
</nav>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h4>Upload New Files</h4>
        </div>
        <div class="card-body">
            <form action="/upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="files">Select files (or folder):</label>
                    <input type="file" name="files" class="form-control" webkitdirectory multiple required>
                </div>
                <button type="submit" class="btn btn-success">Upload</button>
            </form>
            <c:if test="${not empty uploadMessage}">
                <div class="alert alert-success">${uploadMessage}</div>
            </c:if>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-header">
            <h4>Available Files</h4>
        </div>
        <div class="card-body">
            <ul class="list-group">
                <c:forEach var="file" items="${files}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span>${file}</span>
                        <div>
                            <form action="/delete" method="post" style="display:inline;">
                                <input type="hidden" name="fileName" value="${file}">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>
                            <form action="/download" method="get" style="display:inline;">
                                <input type="hidden" name="fileName" value="${file}">
                                <button type="submit" class="btn btn-info btn-sm">Download</button>
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${not empty fileMessage}">
                <div class="alert alert-info mt-3">${fileMessage}</div>
            </c:if>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="/indexjsp" class="btn btn-back btn-sm">Back to Homepage</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js
