<#-- @ftlvariable name="emailId" type="java.lang.String" -->
<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
    <p> Welcome <#if emailId??> ${emailId} </#if> to the first kotlin app! :D </p>
    <#--noinspection HtmlUnknownTarget-->
    <a href="/weblogout" class="links">Logout </a>
</@layout.mainLayout>
