<#-- @ftlvariable name="presenter" type="presenters.WebSettingsPresenter" -->
<#-- @ftlvariable name="user" type="model.User" -->
<#-- @ftlvariable name="validator" type="presenters.WebSettingsPresenter.Validator" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="password_updated" type="java.lang.String" -->
<#-- @ftlvariable name="user_updated" type="java.lang.String" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Settings">
    <link rel="stylesheet" href="${presenter.theme.css}">
    <main class="content">
        <div class="header">
            <h1 class="header-title">
                Settings
            </h1>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3 col-xl-2">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Settings</h5>
                        </div>
                        <div class="list-group list-group-flush" role="tablist">
                            <a class="list-group-item list-group-item-action active" data-toggle="list" href="#account"
                               role="tab">
                                Account
                            </a>
                            <a class="list-group-item list-group-item-action" data-toggle="list" href="#password"
                               role="tab">
                                Password
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-md-9 col-xl-10">
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="account" role="tabpanel">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title mb-0">Private info</h5>
                                </div>
                                <div class="card-body">
                                    <#if error??>
                                        <p style="color: indianred">${error}</p>
                                    </#if>
                                    <#if user_updated??>
                                        <p style="color: green">${user_updated}</p>
                                    </#if>
                                    <#if password_updated??>
                                        <p style="color: green">${password_updated}</p>
                                    </#if>
                                    <#--noinspection HtmlUnknownTarget-->
                                    <form action="/websettings" method="post"
                                          enctype="application/x-www-form-urlencoded">
                                        <div class="form-row">
                                            <div class="form-group col-md-6">
                                                <label for="firstname">First name</label>
                                                <input type="text" class="form-control" id="firstname" name="firstname"
                                                       placeholder="${user.first_name}">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="lastname">Last name</label>
                                                <input type="text" class="form-control" id="lastname" name="lastname"
                                                       placeholder="${user.last_name}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                   placeholder="${user.email}"
                                                   pattern="${validator.email_pattern.toString()}">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </form>
                                </div>
                            </div>

                        </div>
                        <div class="tab-pane fade" id="password" role="tabpanel">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Password</h5>
                                    <#if error??>
                                        <p style="color: indianred">${error}</p>
                                    </#if>
                                    <#if password_updated??>
                                        <p style="color: green">${password_updated}</p>
                                    </#if>
                                    <#--noinspection HtmlUnknownTarget-->
                                    <form action="/websettings" method="post"
                                          enctype="application/x-www-form-urlencoded">
                                        <div class="form-group">
                                            <label for="current_password">Current password</label>
                                            <input type="password" class="form-control" id="current_password"
                                                   name="current_password" required
                                                   pattern="${validator.password_pattern.toString()}"
                                                   oninvalid="setCustomValidity('${validator.password_validation_message()}')"
                                                   onchange="try{setCustomValidity('')}catch(e){}">
                                            <#--noinspection HtmlUnknownTarget-->
                                            <small><a href="/reset_password">Forgot your password?</a></small>
                                        </div>
                                        <div class="form-group">
                                            <label for="new_password">New password</label>
                                            <input type="password" class="form-control" id="new_password"
                                                   name="new_password"
                                                   placeholder="Enter your password" required
                                                   pattern="${validator.password_pattern.toString()}"
                                                   oninvalid="setCustomValidity('${validator.password_validation_message()}')"
                                                   onchange="try{setCustomValidity('')}catch(e){}">
                                        </div>
                                        <div class="form-group">
                                            <label for="verify_password">Verify password</label>
                                            <input type="password" class="form-control" id="verify_password"
                                                   name="verify_password"
                                                   placeholder="Enter your password" required
                                                   pattern="${validator.password_pattern.toString()}"
                                                   oninvalid="setCustomValidity('${validator.password_validation_message()}')"
                                                   onchange="try{setCustomValidity('')}catch(e){}">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</@ui.dashboardUI>
