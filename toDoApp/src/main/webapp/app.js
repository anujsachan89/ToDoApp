var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/login');
    
    $stateProvider
        
        // HOME STATES AND NESTED VIEWS ========================================
        .state('home', {
            url: '/home',
            templateUrl: 'template/home.html'
        })
            .state('login', {
            url: '/login',
            templateUrl: 'template/login.html',
            controller: "loginCtrl",
            controllerAs:"ctrl"
        })
 	.state('signup', {
            url: '/signup',
            templateUrl: 'template/signup.html',
            controller: "SignupCtrl",
            controllerAs:"ctrl"
        })
        // nested list with custom controller
        .state('home.todo', {
            url: '/todo',
            templateUrl: 'template/todo.html',
            controller:"taskCtrl"
            
        })

        
});
