<#macro mainLayout title="Clouds">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title> Dental Auxiliary Placement Services </title>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <#-- Vanilla Bootstrap -->
<#--        <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>-->
<#--        <script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>-->
<#--        <script src="/webjars/popper.js/2.0.2/umd/popper.min.js"></script>-->
<#--        <link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css">-->
        <#--    App Sepcific CSS & JS-->
        <link rel="stylesheet" href="/static/css/clouds.css">
        <link rel="stylesheet" href="/static/css/weblogin.css">
        <link rel="stylesheet" href="/static/css/main.css">
<#--            <link href="/static/css/modern.css" rel="stylesheet">-->
        <link href="/static/css/classic.css" rel="stylesheet">
<#--            <link href="/static/css/dark.css" rel="stylesheet">-->
<#--            <link href="/static/css/light.css" rel="stylesheet">-->
        <script src="/static/js/settings.js"></script>
        <script src="/static/js/app.js"></script>
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
