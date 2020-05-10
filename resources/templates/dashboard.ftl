<#-- @ftlvariable name="presenter" type="presenters.DashboardPresenter" -->
<#-- @ftlvariable name="user" type="model.User" -->
<#import "spark-template.ftl" as layout />

<@layout.mainLayout title="Table">
    <body>
    <div class="splash active">
        <div class="splash-icon"></div>
    </div>

    <div class="wrapper">
        <nav id="sidebar" class="sidebar">
            <a class="sidebar-brand" href="/static/docs/index.html">
<#--                <svg>-->
<#--                    <use xlink:href="#ion-ios-pulse-strong"></use>-->
<#--                </svg>-->
<#--                <img src="/static/images/daps_logo.png" alt="daps logo">-->
                DAPS
            </a>
            <div class="sidebar-content">
                <div class="sidebar-user">
                    <img src="/static/images/avatars/avatar.jpg" class="img-fluid rounded-circle mb-2" alt="${user.first_name + user.last_name}" />
                    <div class="font-weight-bold">${user.first_name + user.last_name}</div>
                    <small>CEO</small>
                </div>

                <ul class="sidebar-nav">
                    <li class="sidebar-header">
                        Main
                    </li>
                    <li class="sidebar-item active">
                        <a href="#dashboards" data-toggle="collapse" class="sidebar-link">
                            <i class="align-middle mr-2 fas fa-fw fa-home">l</i> <span class="align-middle">Dashboards</span>
                        </a>
                        <ul id="dashboards" class="sidebar-dropdown list-unstyled collapse show" data-parent="#sidebar">
<#--                            <li class="sidebar-item active"><a class="sidebar-link" href="/static/docs/dashboard-default.html">Default</a></li>-->
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/dashboard-analytics.html">Analytics</a></li>-->
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/dashboard-e-commerce.html">E-commerce</a></li>-->
                        </ul>
                    </li>
                    <li class="sidebar-item">
                        <a href="#pages" data-toggle="collapse" class="sidebar-link collapsed">
                            <i class="align-middle mr-2 fas fa-fw fa-file">l</i> <span class="align-middle">Pages</span>
                        </a>
                        <ul id="pages" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-settings.html">Settings</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-clients.html">Clients
<#--                                    <span class="sidebar-badge badge badge-pill badge-primary"> </span>--></a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-invoice.html">Invoice</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-pricing.html">Pricing</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-tasks.html">Tasks</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-chat.html">Chat
<#--                                    <span class="sidebar-badge badge badge-pill badge-primary"> New </a></li></span>--></a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/pages-blank.html">Blank Page</a></li>
                        </ul>
                    </li>

                    <li class="sidebar-header">
                        Elements
                    </li>
                    <li class="sidebar-item">
                        <a href="#charts" data-toggle="collapse" class="sidebar-link collapsed">
                            <i class="align-middle mr-2 fas fa-fw fa-chart-pie">l</i> <span class="align-middle">Charts</span>
<#--                            <span class="sidebar-badge badge badge-pill badge-primary">New</span>-->
                        </a>
<#--                        <ul id="charts" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">-->
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/charts-chartjs.html">Chart.js</a></li>-->
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/charts-apexcharts.html">ApexCharts</a></li>-->
<#--                        </ul>-->
                    </li>

                    <li class="sidebar-item">
                        <a href="#tables" data-toggle="collapse" class="sidebar-link collapsed">
                            <i class="align-middle mr-2 fas fa-fw fa-table"></i> <span class="align-middle">Tables</span>
                        </a>
                        <ul id="tables" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                            <li class="sidebar-item"><a class="sidebar-link" href="/webclients">Clients</a></li>
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/tables-bootstrap.html">Bootstrap</a></li>-->
<#--                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/tables-datatables.html">DataTables</a></li>-->
                        </ul>
                    </li>

                    <li class="sidebar-header">
                        Extras
                    </li>
                    <li class="sidebar-item">
                        <a href="#documentation" data-toggle="collapse" class="sidebar-link collapsed">
                            <i class="align-middle mr-2 fas fa-fw fa-book"></i> <span class="align-middle">Documentation</span>
                        </a>
                        <ul id="documentation" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/docs-getting-started.html">Getting Started</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/docs-plugins.html">Plugins</a></li>
                            <li class="sidebar-item"><a class="sidebar-link" href="/static/docs/docs-changelog.html">Changelog</a></li>
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

                <form class="form-inline d-none d-sm-inline-block">
                    <input class="form-control form-control-lite" type="text" placeholder="Search projects...">
                </form>

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
                                <a class="dropdown-item" href="#"><i class="align-middle mr-1 fas fa-fw fa-user"></i> View Profile</a>
<#--                                <a class="dropdown-item" href="#"><i class="align-middle mr-1 fas fa-fw fa-comments"></i> Contacts</a>-->
<#--                                <a class="dropdown-item" href="#"><i class="align-middle mr-1 fas fa-fw fa-chart-pie"></i> Analytics</a>-->
                                <a class="dropdown-item" href="#"><i class="align-middle mr-1 fas fa-fw fa-cogs"></i> Settings</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="/weblogout"><i class="align-middle mr-1 fas fa-fw fa-arrow-alt-circle-right"></i> Sign out</a>
                            </div>
                        </li>
                    </ul>
                </div>

            </nav>
            <main class="content">
                <div class="container-fluid">

                    <div class="header">
                        <h1 class="header-title">
                            Welcome back, ${user.first_name + user.last_name}!
                        </h1>
                        <p class="header-subtitle">You have 0 new messages and 0 new notifications.</p>
                    </div>
                </div>
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
<#--                                <a href="/static/docs/dashboard-default.html" class="text-muted">DAPS </a>-->
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
    <script src="/static/js/app.js"></script>

    <script>
        $(function() {
            // Line chart
            new Chart(document.getElementById("chartjs-dashboard-line"), {
                type: 'line',
                data: {
                    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                    datasets: [{
                        label: "Orders",
                        fill: true,
                        backgroundColor: window.theme.primary,
                        borderColor: window.theme.primary,
                        borderWidth: 2,
                        data: [3, 2, 3, 5, 6, 5, 4, 6, 9, 10, 8, 9]
                    },
                        {
                            label: "Sales ($)",
                            fill: true,
                            backgroundColor: "rgba(0, 0, 0, 0.05)",
                            borderColor: "rgba(0, 0, 0, 0.05)",
                            borderWidth: 2,
                            data: [5, 4, 10, 15, 16, 12, 10, 13, 20, 22, 18, 20]
                        }
                    ]
                },
                options: {
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    tooltips: {
                        intersect: false
                    },
                    hover: {
                        intersect: true
                    },
                    plugins: {
                        filler: {
                            propagate: false
                        }
                    },
                    elements: {
                        point: {
                            radius: 0
                        }
                    },
                    scales: {
                        xAxes: [{
                            reverse: true,
                            gridLines: {
                                color: "rgba(0,0,0,0.0)"
                            }
                        }],
                        yAxes: [{
                            ticks: {
                                stepSize: 5
                            },
                            display: true,
                            gridLines: {
                                color: "rgba(0,0,0,0)",
                                fontColor: "#fff"
                            }
                        }]
                    }
                }
            });
        });
    </script>
    <script>
        $(function() {
            // Pie chart
            new Chart(document.getElementById("chartjs-dashboard-pie"), {
                type: 'pie',
                data: {
                    labels: ["Chrome", "Firefox", "IE", "Other"],
                    datasets: [{
                        data: [4401, 4003, 1589],
                        backgroundColor: [
                            window.theme.primary,
                            window.theme.warning,
                            window.theme.danger,
                            "#E8EAED"
                        ],
                        borderColor: "transparent"
                    }]
                },
                options: {
                    responsive: !window.MSInputMethodContext,
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    cutoutPercentage: 75
                }
            });
        });
    </script>
    <script>
        $(function() {
            // Bar chart
            new Chart(document.getElementById("chartjs-dashboard-bar"), {
                type: 'bar',
                data: {
                    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                    datasets: [{
                        label: "This year",
                        backgroundColor: window.theme.primary,
                        borderColor: window.theme.primary,
                        hoverBackgroundColor: window.theme.primary,
                        hoverBorderColor: window.theme.primary,
                        data: [54, 67, 41, 55, 62, 45, 55, 73, 60, 76, 48, 79],
                        barPercentage: .75,
                        categoryPercentage: .5
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            gridLines: {
                                display: false
                            },
                            stacked: false,
                            ticks: {
                                stepSize: 20
                            }
                        }],
                        xAxes: [{
                            stacked: false,
                            gridLines: {
                                color: "transparent"
                            }
                        }]
                    }
                }
            });
        });
    </script>
    <script>
        $(function() {
            var mapData = {
                "US": 298,
                "SA": 200,
                "DE": 220,
                "FR": 540,
                "CN": 120,
                "AU": 760,
                "BR": 550,
                "IN": 200,
                "GB": 120,
            };
            $('#world_map').vectorMap({
                map: 'world_mill',
                backgroundColor: "transparent",
                zoomOnScroll: false,
                regionStyle: {
                    initial: {
                        fill: '#e4e4e4',
                        "fill-opacity": 0.9,
                        stroke: 'none',
                        "stroke-width": 0,
                        "stroke-opacity": 0
                    }
                },
                series: {
                    regions: [{
                        values: mapData,
                        scale: [window.theme.primary],
                        normalizeFunction: 'polynomial'
                    }]
                },
            });
            setTimeout(function() {
                $(window).trigger('resize');
            }, 350)
        })
    </script>
    <script>
        $(function() {
            $('#datatables-dashboard-projects').DataTable({
                pageLength: 6,
                lengthChange: false,
                bFilter: false,
                autoWidth: false
            });
        });
    </script>
    <script>
        $(function() {
            $('#datetimepicker-dashboard').datetimepicker({
                inline: true,
                sideBySide: false,
                format: 'L'
            });
        });
    </script>

    </body>
</@layout.mainLayout>
