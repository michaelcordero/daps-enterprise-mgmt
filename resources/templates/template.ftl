<#-- @ftlvariable name="welcome" type="model.User" -->

<#macro mainLayout title="Template">
    <!DOCTYPE html>
    <html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title> Dental Auxiliary Placement Services </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
