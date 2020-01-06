<#import "clouds.ftl" as layout />

<@layout.mainLayout title="Register">
    <link rel="stylesheet" type="text/css" href="/static/css/register.css" >
    <body>
    <main class="main h-100 w-100">
        <div class="container h-100">
            <div class="row h-100">
                <div class="col-sm-10 col-md-8 col-lg-6 mx-auto d-table h-100">
                    <div class="d-table-cell align-middle">
                        <div class="text-center mt-4">
                            <h1 class="h2">User Credentials</h1>
                            <p class="lead">
                                Please enter your desired user credentials
                            </p>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="m-sm-4">
                                    <form action="/register" method="post" enctype="application/x-www-form-urlencoded">
                                        <div class="form-group">
                                            <label for="first_name">First Name</label>
                                            <input class="form-control form-control-lg" type="text" id="first_name" name="first_name" placeholder="Enter your first name">
                                        </div>
                                        <div class="form-group">
                                            <label for="last_name">Last Name</label>
                                            <input class="form-control form-control-lg" type="text" id="last_name" name="last_name" placeholder="Enter your last name">
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <input class="form-control form-control-lg" type="email" id="email" name="email" placeholder="Enter your email">
                                        </div>
                                        <div class="form-group">
                                            <label for="password">Password</label>
                                            <input class="form-control form-control-lg" type="password" id="password" name="password" placeholder="Enter password">
                                        </div>
                                        <div class="text-center mt-3">
                                            <input class="btn btn-lg btn-primary" type="submit" value="Register">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </main>
    </body>
</@layout.mainLayout>
