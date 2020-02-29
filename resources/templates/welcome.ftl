<#-- @ftlvariable name="user" type="model.User" -->
<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
    <p> Welcome ${user.first_name} to the first kotlin app! :D </p>
    <#--noinspection HtmlUnknownTarget-->
    <a href="/logout" class="links">Logout </a>
</@layout.mainLayout>
