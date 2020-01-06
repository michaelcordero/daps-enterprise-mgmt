<#-- @ftlvariable name="user" type="com.daps.ent.model.User" -->
<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
    <p> Welcome ${user.displayName} to the first kotlin app! :D </p>
</@layout.mainLayout>
