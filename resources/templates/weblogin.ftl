<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="presenter" type="presenters.WebLoginPresenter" -->
<#import "clouds.ftl" as layout />
<@layout.mainLayout title="WebLogin">
    <link rel="stylesheet" href="${presenter.theme.css}" >
    <body class="text-center">
    <#--noinspection HtmlUnknownTarget-->
    <form class="form-login" action="/weblogin" method="post" enctype="application/x-www-form-urlencoded">
        <#--noinspection CheckImageSize-->
        <img class="mb-4" src="/static/images/daps_logo.png" width="80" height="80" alt="logo">
        <h3 class="h3 mb-3 font-weight-normal">Dental Auxiliary Placement Services</h3>
        <#if error??>
            <p class="error" style="color: indianred">${error}</p>
        </#if>
        <div class="form-group">
        <label for="emailId" class="sr-only">Email address</label>
        <input type="text" id="emailId" name="emailId"  class="form-control" placeholder="Email address" autofocus="">
        </div>
        <#--noinspection HtmlUnknownTarget-->
        <a href="/register" class="links">Register </a>
        <div class="form-group">
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password">
        </div>
        <#--noinspection HtmlUnknownTarget,HtmlUnknownTarget-->
        <a href="/reset_password" class="links">Forgot Password? </a>
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
