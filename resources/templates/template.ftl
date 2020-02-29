<#-- @ftlvariable name="welcome" type="model.User" -->

<#macro mainLayout title="Template">
    <!DOCTYPE html>
    <html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title> Dental Auxiliary Placement Services </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#-- Vanilla Bootstrap -->
    <link rel="stylesheet" href="webjars/bootstrap/4.4.1/css/bootstrap.min.css" >
    <script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/popper.js/1.14.3/umd/popper.min.js"></script>
</head>
<body>


<div id="layout">
    <div class="content-subhead" id="main">
        <div class="header">
            <h1>${title}</h1>
        </div>
        <#nested />
    </div>
</div>

</body>



</html>
</#macro>
