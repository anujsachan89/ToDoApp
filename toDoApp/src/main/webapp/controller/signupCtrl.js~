var myApp = angular.module('routerApp');
myApp.controller("SignupCtrl", function ($scope,$state,SignupService) {
            console.log("Signup Controller Working..");
            var self= this;
               this.Signup = function() {
                   var user = self.user;
                   console.log(user)
		// user.email = $scope.email;
		// user.password = $scope.password;
        // user.name = $scope.name;
        // user.mobile = $scope.mobile;

        // console.log($scope.user);
        var httpObj = SignupService.signup(user)
        
        httpObj.then(function(data) {
            console.log(data);
            
            var resp = data.data;
            if( resp.status == 1)
            {
            	$state.go("login");
            }
            else
            {
            	console.log(resp);
            	var errors = resp.errorlist;
            	for( var i in errors)
            	{
            		var er = errors[i];
            		var fId = er.field;
            		$scope[fId] = er.defaultMessage; 
            	}
            	return false;
            }
        })
        .catch(function(error) {
            console.log(error);
        })
    }
});

myApp.service('SignupService',function($http){
	this.signup = function(user){ 
		return $http({
			url:"http://localhost:8080/toDoApp/register",
			method:"post",
			data:user
		});
	}
	});
