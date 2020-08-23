<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="validator" type="presenters.RegisterPresenter.Validator" -->
<#-- @ftlvariable name="presenter" type="presenters.RegisterPresenter" -->
<#import "clouds.ftl" as layout />

<@layout.mainLayout title="Register">
    <body>
    <link rel="stylesheet" href="/static/css/main.css">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <div class="card mx-auto" style="width: 35rem; margin-top: 15rem">
        <div class="card-body">
            <div class="m-sm-4">
                <#--noinspection HtmlUnknownTarget-->
                <form action="/register" method="post" enctype="application/x-www-form-urlencoded">
                    <#if error??>
                        <p style="color: indianred">${error}</p>
                    </#if>
                    <div class="form-group">
                        <label for="first_name">First Name</label>
                        <input class="form-control form-control-lg" type="text" id="first_name" name="first_name"
                               placeholder="Enter your first name" required
                               pattern="${validator.user_name_pattern.toString()}">
                    </div>
                    <div class="form-group">
                        <label for="last_name">Last Name</label>
                        <input class="form-control form-control-lg" type="text" id="last_name" name="last_name"
                               placeholder="Enter your last name" required
                               pattern="${validator.user_name_pattern.toString()}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input class="form-control form-control-lg" type="email" id="email" name="email"
                               placeholder="Enter your email" required pattern="${validator.email_pattern.toString()}">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input class="form-control form-control-lg" type="password" id="password" name="password"
                               placeholder="Enter your password" required
                               pattern="${validator.password_pattern.toString()}"
                               oninvalid="setCustomValidity('${validator.password_validation_message()}')"
                               onchange="try{setCustomValidity('')}catch(e){}">
                    </div>
                    <div class="form-group">
                        <label for="key">Registration Key</label>
                        <input class="form-control form-control-lg" type="text" id="key" name="key" maxlength="19"
                               placeholder="Please enter registration key" required
                               pattern="${validator.registration_key_pattern.toString()}">
                    </div>
                    <div class="text-center mt-3">
                        <input class="btn btn-lg btn-primary" type="submit" value="Register">
                    </div>
                </form>
            </div>
        </div>
    </div>
    </body>
</@layout.mainLayout>
