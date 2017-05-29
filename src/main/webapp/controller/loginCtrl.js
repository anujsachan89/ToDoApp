var myApp = angular.module('routerApp');
myApp.controller("loginCtrl",function($scope,$state, loginService){
console.log("Login Controller Working..");
this.reset=function(){
	console.log($scope.user);
	$scope.user={};
	console.log($scope.user);
}
this.login = function () {

		var httpObje = loginService.login($scope.user);
		
		httpObje.then(function (data) {
			if( data.data.status == 1 ){
				
				$state.go("home.todo");
			}
			else
			{
				$scope.emailError = data.data.emailError;
				
				var message = data.data.message;
				$scope.errorMessage = message;
				console.log($scope.errorMessage);	
			}
		})
		.catch( function(error){
			console.log(error);
		});
	}	
});


myApp.service("loginService",function ($http) {
	this.login = function(user){ 
		return $http({
			url:"/login",
			method:"post",
			data:user
		});
	}
});
