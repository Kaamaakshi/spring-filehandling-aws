<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Welcome to File Management</title>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 100px;
            text-align: center;
            border: 1px solid #ced4da; /* Border around the container */
            border-radius: 0.25rem; /* Rounded corners */
            padding: 20px; /* Padding inside the border */
            background-color: #fff;
        }
        .welcome-message {
            font-size: 2rem;
            font-weight: bold;
            margin-bottom: 20px;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="welcome-message">Welcome to the File Management System!</div>
    <p>This application allows you to upload, download, and delete files easily.</p>
    <p>To get started, click the button below to upload your files.</p>
    <a href="/indexjsp" class="btn btn-primary btn-lg">Go to File Management</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
