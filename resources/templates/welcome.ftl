<#-- @ftlvariable name="user" type="model.User" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Welcome">
    <div class="container-fluid">
        <div class="header">
            <h1 class="header-title">Welcome <#if user??>  ${user.first_name + " " +user.last_name} </#if></h1>
        </div>
    </div>
</@ui.dashboardUI>
