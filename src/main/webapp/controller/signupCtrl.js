var myApp = angular.module('routerApp');
myApp.controller("SignupCtrl", function ($scope,$state,SignupService) {
            console.log("Signup Controller Working..");
            var self= this;
            self.user = {};
            this.reset=function(){
            	console.log(self.user);
            	self.user={};
            	console.log(self.user);
            }
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
          	  $('.container').stop().removeClass('active');
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
			url:"/toDoApp/register",
			method:"post",
			data:user
		});
	}
	});
