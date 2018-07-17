var app = angular.module('Application',
    [
        'ui.router',
        'ngAnimate',
        'toggle-switch',
        'jcs-autoValidate',
        'fm',
        'ui.select',
        'ngSanitize',
        'counter',
        'FBAngular',
        'ui.bootstrap',
        'angularCSS',
        'smart-table',
        'lrDragNDrop',
        'localytics.directives',
        'angularFileUpload',
        'ngLoadingSpinner',
        'ngStomp',
        'luegg.directives',
        'chart.js',
        'monospaced.elastic',
        'pageslide-directive',
        'ui.bootstrap.contextMenu',
        'kdate',
        'ui.sortable',
        'timer',
        'angular.filter',
        'datetime'
    ]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$cssProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider, $cssProvider, $httpProvider) {

        $urlRouterProvider.otherwise("/menu");

        $locationProvider.html5Mode(true);

        $httpProvider.interceptors.push('errorInterceptor');

        /**************************************************************
         *                                                            *
         * Menu State                                                 *
         *                                                            *
         *************************************************************/
        $stateProvider.state("menu", {
            url: "/menu",
            css: [
                '/ui/css/theme-black.css',
                '/ui/css/style.css'
            ],
            templateUrl: "/ui/partials/menu/menu.html",
            controller: "menuCtrl"
        });
    }
]);


