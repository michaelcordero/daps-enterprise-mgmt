<#-- @ftlvariable name="user" type="model.User" -->
<#-- @ftlvariable name="presenter" type="presenters.WelcomePresenter" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Welcome">
    <link rel="stylesheet" href="${presenter.theme.css}" >
    <div class="container-fluid">
        <div class="header">
            <h1 class="header-title">Welcome <#if user??>  ${user.first_name + " " +user.last_name}! </#if></h1>
        </div>
    </div>
</@ui.dashboardUI>
