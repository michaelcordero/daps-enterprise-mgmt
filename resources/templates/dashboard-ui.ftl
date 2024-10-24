<#macro dashboardUI title="Dashboard UI">
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="DAPS Enterprise Management System">
    <title>Dental Auxiliary Placement Service</title>
    <link rel="stylesheet" href="/static/css/main.css">
<#--    Pre-compiled Datatables Editor CSS -->
<#--    <link rel="stylesheet" href="/static/css/editor.dataTables.min.css">-->
    <link rel="stylesheet" href="/static/css/editor.bootstrap4.min.css">
<#--    Spark UI-->
    <script src="/static/js/settings.js"></script>
    <script src="/static/js/app.js"></script>
</head>
<body>
<div class="splash active">
    <div class="splash-icon"></div>
</div>

<div class="wrapper">
    <nav id="sidebar" class="sidebar">
        <a class="sidebar-brand" href="#">
            <#--                <svg>-->
            <#--                    <use xlink:href="#ion-ios-pulse-strong"></use>-->
            <#--                </svg>-->
            <#--                <img src="/static/images/daps_logo.png" alt="daps logo">-->
            Dental Auxiliary Placement Services
        </a>
        <div class="sidebar-content">
            <div class="sidebar-user">
<#--                <img src="/static/images/avatars/avatar.jpg" class="img-fluid rounded-circle mb-2" alt="${user.first_name + user.last_name}" />-->
<#--                <div class="font-weight-bold">${user.first_name + user.last_name}</div>-->
<#--                <small>CEO</small>-->
            </div>

            <ul class="sidebar-nav">
                <li class="sidebar-header">
                    Main
                </li>
                <li class="sidebar-item">
                    <a href="/welcome" class="sidebar-link">
                        <i class="align-middle mr-2 fas fa-fw fa-home">l</i> <span class="align-middle">Home</span>
                    </a>
<#--                    <ul id="dashboards" class="sidebar-dropdown list-unstyled collapse show" data-parent="#sidebar">-->
                        <#--                            <li class="sidebar-item active"><a class="sidebar-link" href="/static/docs/dashboard-default.html">Default</a></li>-->
                        <#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/dashboard-analytics.html">Analytics</a></li>-->
                        <#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/dashboard-e-commerce.html">E-commerce</a></li>-->
<#--                    </ul>-->
                </li>

                <li class="sidebar-header">
                    Elements
                </li>
                <li class="sidebar-item">
                    <a href="#charts" data-toggle="collapse" class="sidebar-link collapsed">
                        <i class="align-middle mr-2 fas fa-fw fa-chart-pie">l</i> <span class="align-middle">Charts</span>
                        <#--                            <span class="sidebar-badge badge badge-pill badge-primary">New</span>-->
                    </a>
                                            <ul id="charts" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                                                <li class="sidebar-item"><a class="sidebar-link" href="/web-traditional-charts">Traditional Charts</a></li>
                                                <li class="sidebar-item"><a class="sidebar-link" href="/web-apex-charts">Apex Charts</a></li>
                                            </ul>
                </li>

                <li class="sidebar-item">
                    <a href="#tables" data-toggle="collapse" class="sidebar-link collapsed">
                        <i class="align-middle mr-2 fas fa-fw fa-table"></i> <span class="align-middle">Tables</span>
                    </a>
                    <ul id="tables" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                        <li class="sidebar-item"><a class="sidebar-link" href="/webbillings">Billings</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webclients">Clients</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webclientnotes">Client Notes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webclientpermnotes">Client Perm Notes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webdapsaddresses">DAPS Addresses</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webdapsstaffmessages">DAPS Staff Messages</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webdapsstaff">DAPS Staff</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webinterviewguide">Interview Guide</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webpasteerrors">Paste Errors</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webpayments">Payments</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webpermnotes">Perm Notes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webpermreqnotes">Perm Req Notes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webtempnotes">TempNotes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webtempsavailableforwork">Temps Available For Work</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webtemps">Temps</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webusers">Users</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webworkordernotes">WO Notes</a></li>
                        <li class="sidebar-item"><a class="sidebar-link" href="/webworkorders">Work Orders</a></li>
                    </ul>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link" href="#">
                        <i class="align-middle mr-2 far fa-fw fa-calendar-alt"></i> <span class="align-middle">Calendar</span>
                    </a>
                </li>

                <li class="sidebar-header">
                    Extras
                </li>
                <li class="sidebar-item">
                    <a href="#documentation" data-toggle="collapse" class="sidebar-link collapsed">
                        <i class="align-middle mr-2 fas fa-fw fa-book"></i> <span class="align-middle">Documentation</span>
                    </a>
                    <ul id="documentation" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                        <li class="sidebar-item"><a class="sidebar-link" href="#">Getting Started</a></li>
<#--                        <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/docs-plugins.html">Plugins</a></li>-->
                        <li class="sidebar-item"><a class="sidebar-link" href="#">Changelog</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="main">
        <nav class="navbar navbar-expand navbar-theme">
            <a class="sidebar-toggle d-flex mr-2">
                <i class="hamburger align-self-center"></i>
            </a>

<#--            <form class="form-inline d-none d-sm-inline-block">-->
<#--                <input class="form-control form-control-lite" type="text" placeholder="Search projects...">-->
<#--            </form>-->

            <div class="navbar-collapse collapse">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown active">
                        <a class="nav-link dropdown-toggle position-relative" href="#" id="messagesDropdown" data-toggle="dropdown">
                            <i class="align-middle fas fa-envelope-open"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right py-0" aria-labelledby="messagesDropdown">
                            <div class="dropdown-menu-header">
                                <div class="position-relative">
                                    0 New Messages
                                </div>
                            </div>
                            <div class="dropdown-menu-footer">
                                <a href="#" class="text-muted">Show all messages</a>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item dropdown ml-lg-2">
                        <a class="nav-link dropdown-toggle position-relative" href="#" id="alertsDropdown" data-toggle="dropdown">
                            <i class="align-middle fas fa-bell"></i>
                            <#--                                <span class="indicator"></span>-->
                        </a>
                        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right py-0" aria-labelledby="alertsDropdown">
                            <div class="dropdown-menu-header">
                                0 New Notifications
                            </div>
                            <div class="dropdown-menu-footer">
                                <a href="#" class="text-muted">Show all notifications</a>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item dropdown ml-lg-2">
                        <a class="nav-link dropdown-toggle position-relative" href="#" id="userDropdown" data-toggle="dropdown">
                            <i class="align-middle fas fa-cog"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="/websettings"><i class="align-middle mr-1 fas fa-fw fa-cogs"></i> Settings</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/weblogout"><i class="align-middle mr-1 fas fa-fw fa-arrow-alt-circle-right"></i> Sign out</a>
                        </div>
                    </li>
                </ul>
            </div>

        </nav>
        <main class="content" id="dashboard-ui">
            <div class="container-fluid">

                <div class="header">
                    <h1 class="header-title">
                    </h1>
                </div>
            </div>
            <#nested />
        </main>
        <footer class="footer">
            <div class="container-fluid">
                <div class="row text-muted">
                    <div class="col-8 text-left">
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <a class="text-muted" href="#">Support</a>
                            </li>
                            <li class="list-inline-item">
                                <a class="text-muted" href="#">Privacy</a>
                            </li>
                            <li class="list-inline-item">
                                <a class="text-muted" href="#">Terms of Service</a>
                            </li>
                            <li class="list-inline-item">
                                <a class="text-muted" href="#">Contact</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-4 text-right">
                        <p class="mb-0">
                            DAPS &copy; 1988 - 2020
                        </p>
                    </div>
                </div>
            </div>
        </footer>
    </div>

</div>

<svg width="0" height="0" style="position:absolute">
    <defs>
        <symbol viewBox="0 0 512 512" id="ion-ios-pulse-strong">
            <path
                    d="M448 273.001c-21.27 0-39.296 13.999-45.596 32.999h-38.857l-28.361-85.417a15.999 15.999 0 0 0-15.183-10.956c-.112 0-.224 0-.335.004a15.997 15.997 0 0 0-15.049 11.588l-44.484 155.262-52.353-314.108C206.535 54.893 200.333 48 192 48s-13.693 5.776-15.525 13.135L115.496 306H16v31.999h112c7.348 0 13.75-5.003 15.525-12.134l45.368-182.177 51.324 307.94c1.229 7.377 7.397 11.92 14.864 12.344.308.018.614.028.919.028 7.097 0 13.406-3.701 15.381-10.594l49.744-173.617 15.689 47.252A16.001 16.001 0 0 0 352 337.999h51.108C409.973 355.999 427.477 369 448 369c26.511 0 48-22.492 48-49 0-26.509-21.489-46.999-48-46.999z">
            </path>
        </symbol>
    </defs>
</svg>

</body>
</#macro>
