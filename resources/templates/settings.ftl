<#import "dashboard-ui.ftl" as ui />
<@ui.dashboardUI title="Settings">
<link rel="stylesheet" href="${presenter.theme.css}">
<main class="content">
    <div class="header">
        <h1 class="header-title">
            Settings
        </h1>
    </div>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-3 col-xl-2">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Settings</h5>
                            </div>
                            <div class="list-group list-group-flush" role="tablist">
                                <a class="list-group-item list-group-item-action active" data-toggle="list" href="#account" role="tab">
                                    Account
                                </a>
                                <a class="list-group-item list-group-item-action" data-toggle="list" href="#password" role="tab">
                                    Password
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-9 col-xl-10">
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="account" role="tabpanel">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">Private info</h5>
                                    </div>
                                    <div class="card-body">
                                        <form>
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="firstname">First name</label>
                                                    <input type="text" class="form-control" id="firstname" placeholder="First name">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="lastname">Last name</label>
                                                    <input type="text" class="form-control" id="lastname" placeholder="Last name">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="email" class="form-control" id="email" placeholder="Email">
                                            </div>
                                            <button type="submit" class="btn btn-primary">Save changes</button>
                                        </form>

                                    </div>
                                </div>

                            </div>
                            <div class="tab-pane fade" id="password" role="tabpanel">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Password</h5>

                                        <form>
                                            <div class="form-group">
                                                <label for="inputPasswordCurrent">Current password</label>
                                                <input type="password" class="form-control" id="inputPasswordCurrent">
                                                <small><a href="/reset_password">Forgot your password?</a></small>
                                            </div>
                                            <div class="form-group">
                                                <label for="inputPasswordNew">New password</label>
                                                <input type="password" class="form-control" id="inputPasswordNew">
                                            </div>
                                            <div class="form-group">
                                                <label for="inputPasswordNew2">Verify password</label>
                                                <input type="password" class="form-control" id="inputPasswordNew2">
                                            </div>
                                            <button type="submit" class="btn btn-primary">Save changes</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
</main>
</@ui.dashboardUI>
