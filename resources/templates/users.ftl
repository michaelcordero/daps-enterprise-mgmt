<#-- @ftlvariable name="users" type="java.util.List<model.User>" -->
<#import "template.ftl" as layout />

<@layout.mainLayout title="Users">
    <div class="user table">
        <h3 class="content-subhead">Users</h3>
        <ul>
            <#list users as user>
                <li> UserFirstName: ${user.first_name} | UserLastName: ${user.last_name}
                    | UserEmail:  ${user.email} | UserPassword: ${user.passwordHash } | Role: ${user.role}</li>
                <#else>
                    <p> No users </p>
            </#list>
        </ul>
    </div>
</@layout.mainLayout>
