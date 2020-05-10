<#-- @ftlvariable name="emailId" type="java.lang.String" -->
<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Welcome">
    <p> Welcome <#if emailId??> ${emailId} </#if> to the first kotlin app! :D </p>
</@ui.dashboardUI>
