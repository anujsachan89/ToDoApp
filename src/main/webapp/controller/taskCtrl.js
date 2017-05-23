angular.module('routerApp').controller("taskCtrl",taskCtrl)

function taskCtrl($scope, $http) {
    $scope.formData = {};

    // when landing on the page, get all todos and show them
    $http.get('http://localhost:8080/toDoApp/todoList')
        .then(function(data) {
		if(data.data.status!==-1)
			{
		            $scope.todos = data.data.list;
			}else{
		            $scope.todos = [];
			}
        })
        .catch(function(data) {
            console.log('Error: ' + data);
        });

    // when submitting the add form, send the text to todo Controller
    $scope.createTodo = function() {
	console.log($scope.formData);
        $http.post('http://localhost:8080/toDoApp/createtask', $scope.formData)
            .then(function(data) {
                $scope.formData = {}; // clear the form so our user is ready to enter another
                $scope.todos = data;
                console.log(data);
            })
            .catch(function(data) {
                console.log('Error: ' + data);
            });
    };

    // delete a todo after checking it
    $scope.deleteTodo = function(id) {
        $http.delete('http://localhost:8080/toDoApp/delete/' + id)
            .then(function(data) {
                $scope.todos = data;
                console.log(data);
            })
            .catch(function(data) {
                console.log('Error: ' + data);
            });
    };
}
