<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "clouds.ftl" as layout />

<@layout.mainLayout title="Login">
    <body class="text-center">
    <link rel="stylesheet" href="/static/css/main.css">
    <form class="form-login" action="/login" method="post" enctype="application/x-www-form-urlencoded">
        <img class="mb-4" src="/static/images/daps_logo.png" width="80" height="80" alt="logo">
        <h3 class="h3 mb-3 font-weight-normal">DAPS Enterprise</h3>
        <#if error??>
            <p class="error" style="color: indianred">${error}</p>
        </#if>
        <div class="form-group">
        <label for="emailId" class="sr-only">Email address</label>
        <input type="text" id="emailId" name="emailId"  class="form-control" placeholder="Email address" autofocus="">
        </div>
        <a href="/register" class="links">Register </a>
        <div class="form-group">
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password">
        </div>
        <a href="/password" class="links">Forgot Password? </a>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign In">
        <p class="mt-5 mb-3 text-muted">© 1988-2020</p>
    </form>
    </body>
</@layout.mainLayout>
