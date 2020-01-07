<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="emailId" type="java.lang.String" -->

<#import "clouds.ftl" as layout />

<@layout.mainLayout title="Login">
    <body class="text-center">
    <form class="form-login" action="/login" method="post" enctype="application/x-www-form-urlencoded">
        <img class="mb-4" src="/static/images/daps_logo.png" width="80" height="80" alt="logo">
        <h3 class="h3 mb-3 font-weight-normal">DAPS Enterprise</h3>
        <#if error??>
            <p class="error">${error}</p>
        </#if>
        <label for="inputEmail" class="sr-only">Email address</label>
        <#if emailId??>
            <input type="email" id="inputEmail" value="${emailId}" class="form-control" placeholder="Email address" required="" autofocus="">
            <#else >
                <input type="email" id="inputEmail" value="" class="form-control" placeholder="Email address" required="" autofocus="">
        </#if>
        <a href="/register" class="links">Register </a>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <a href="/password" class="links">Forgot Password? </a>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            <p class="mt-5 mb-3 text-muted">© 1988-2020</p>
    </form>
    </body>
</@layout.mainLayout>
