<#-- @ftlvariable name="user" type="model.User" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Welcome">
    <p> Welcome <#if user??> ${user} </#if> to the first kotlin app! :D </p>
</@ui.dashboardUI>
