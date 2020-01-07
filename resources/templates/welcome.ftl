<#-- @ftlvariable name="user" type="com.daps.ent.model.User" -->
<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
    <p> Welcome ${user.first_name} to the first kotlin app! :D </p>
    <a href="/logout" class="links">Logout </a>
</@layout.mainLayout>
