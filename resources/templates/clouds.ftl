<#macro mainLayout title="Clouds">
<!DOCTYPE html>
<html lang="en">
<head>
    <title> Dental Auxiliary Placement Services </title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#-- Vanilla Bootstrap -->
    <link rel="stylesheet" href="webjars/bootstrap/4.4.1/css/bootstrap.min.css" >
    <script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <#--    App Sepcific CSS & JS-->
    <link rel="stylesheet" href="/static/css/clouds.css">
    <link rel="stylesheet" href="/static/css/login.css">
</head>
<div id="background-wrap" style="z-index: -1">
    <div class="x1">
        <div class="cloud"></div>
    </div>
    <div class="x2">
        <div class="cloud"></div>
    </div>
    <div class="x3">
        <div class="cloud"></div>
    </div>
    <div class="x4">
        <div class="cloud"></div>
    </div>
</div>
<body>
<div id="layout" style="z-index: 1">
    <div class="content-subhead" id="main">
        <div class="header">
        </div>
        <#nested />
    </div>
</div>
</body>
</html>
    </#macro>
