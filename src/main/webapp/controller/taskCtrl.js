angular.module('routerApp').controller("taskCtrl",taskCtrl);
angular.module('routerApp').controller("todoEditCtrl",todoEditCtrl);
angular.module('routerApp').directive('contenteditable',contenteditable); 
function taskCtrl($scope, $http,$state,$uibModal,$document) {
    $scope.formData = {};
    $scope.todos = [];
    $scope.user={};
    
    var bodyRef = angular.element($document[0].body);
    
    $(".slides").sortable({
        placeholder: 'slide-placeholder',
       axis: "z",
       revert: 150,
       
       start: function(e, ui){
           
           placeholderHeight = ui.item.outerHeight();
           ui.placeholder.height(placeholderHeight + 15);
           $('<div class="slide-placeholder-animator" data-height="' + placeholderHeight + '"></div>').insertAfter(ui.placeholder);
       
       },
       change: function(event, ui) {
           
           ui.placeholder.stop().height(0).animate({
               height: ui.item.outerHeight() + 15
           }, 300);
           
           placeholderAnimatorHeight = parseInt($(".slide-placeholder-animator").attr("data-height"));
           
           $(".slide-placeholder-animator").stop().height(placeholderAnimatorHeight + 15).animate({
               height: 0
           }, 300, function() {
               $(this).remove();
               placeholderHeight = ui.item.outerHeight();
               $('<div class="slide-placeholder-animator" data-height="' + placeholderHeight + '"></div>').insertAfter(ui.placeholder);
           });
           
       },
       stop: function(e, ui) {
           
           $(".slide-placeholder-animator").remove();
           
       },
   });
    
    
    $scope.changeCardColor = function(color, e,index,id) {
        
        
        var obj;
        
        for (i = 0; i < $scope.todos.length; i++)
        	{
             if($scope.todos[i].id==id)
            	{
            	obj=$scope.todos[i];
            	}
        	}
       console.log(obj);
       obj.cardColor=color;
       $http.post("/toDoApp/update/"+id,obj);
      
        /*console.log($scope.todos[index].reminder);
        console.log(day);
        console.log(index);*/
        /*toDoService.updateToDoItem(id,obj);*/
   /*     toDoService.updateToDoItem(id,obj);*/
        console.log("color changed");
        $(e.target).closest(".card").css("background-color", color);
    };
    
    
    
    $scope.openPopup=function(obj,index){
    	bodyRef.addClass('ovh');
        // Just provide a template url, a controller and call
// 'showModal'.
    	$uibModal.open({
             templateUrl: "template/popup.html",
             bodyClass:"my-bodyClass",
             appendElement:"<div style='margin-top: 150;margin-left: 199px;margin-right: 100;'></div>",
             controller: function($scope,close){
           	
           	 $scope.todo=obj;
           	 $scope.index=index;
           	 $scope.close = function(result) {
           	 close(result, 500); // close, but give 500ms for
// bootstrap to animate
           	 };
             },
             controllerAs:"$modalCtrl"
           }).then(function(modal) {
           	
               modal.element.modal();
               modal.close.then(function(result) {
                 $scope.yesNoResult = result ? "You said Yes" : "You said No";
               });
             });
       };
    
    
    // when landing on the page, get all todos and show them
    $(document).ready(function() {
        $('#list').click(function(event) {
            event.preventDefault();
            $('#products .item').addClass('list-group-item');
        });
        $('#grid').click(function(event) {
            event.preventDefault();
            $('#products .item').removeClass('list-group-item');
            $('#products .item').addClass('grid-group-item');
        });
    });
    
    
    //sidebar toggle in and toggle out
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });

    
    
    $http.get('/toDoApp/getUser')
        .then(function(data) {
		if(data.status==200)
			{
		            $scope.user= data.data;
			}else{
		            $scope.user = null;
			}
        })
        .catch(function(data) {
            console.log('Error: ' + data);
        });
    
    $http.get('/toDoApp/todoList')
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
	var todoData={}
	todoData.title=$scope.title;
	todoData.description = $scope.description;
	todoData.reminder = $scope.reminder;
	
	console.log(todoData);

        $http.post('/toDoApp/createtask',todoData)
            .then(function(data) {
            	$state.reload();
//            	$scope.todos.push($scope.formData);
            	
                $scope.formData = {}; // clear the form so our user is ready to enter another
            })
            .catch(function(data) {
                console.log('Error: ' + data);
            });
    };

    // delete a todo after checking it
    $scope.checkSelected=function(){
    	$scope.isSelected =$scope.todos.some(function(myObj){
    		return myObj.selected===true;
    	});
    };
    $scope.deleteTodo = function(id) {

/*    	var selected =$scope.todos.filter(function(myObj){
    		return myObj.selected===true;
    	});
    	selected.forEach(function(Obj){*/
        $http.delete('/toDoApp/delete/' + id)
            .then(function(data) {
            	var index = $scope.todos.findIndex(function(element, index, array) {
          		  if (element.id === id) {
          		    return true;
          		  }
          		});
          	if (index > -1) {
          		$scope.todos.splice(index, 1);
          	} 
          	//IO Resource
          	
            })
            .catch(function(data) {
                console.log('Error: ' + data);
            });
//    	});
    };
    $scope.triggerEditMode=function(todoData){
        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          templateUrl: "template/popup.html",
          controller: 'todoEditCtrl',
          controllerAs:"$ctrl",
          size: 'md',
          windowClass: 'center-modal',
          resolve:{
        	  todoData:todoData
          }
        });
        

        modalInstance.result.then(function () {
          $log.info('Modal dismissed at: ' + new Date());
        }).catch(function(){
        	
        })
    }
}
function todoEditCtrl($scope, $uibModalInstance,todoData,$http){
	var self =this;
	this.title=todoData.title;
	this.description=todoData.description;
	$scope.updateTodo=todoData;
	$scope.updateTodo.reminder = new Date($scope.updateTodo.reminder||"");
	      $scope.cancel = function () {
	        $uibModalInstance.dismiss('cancel');
	      };
	 this.ok=function(){
		console.log($scope.updateTodo);
		$http.post("/update/"+$scope.updateTodo.id,$scope.updateTodo).then(function(response){
			console.log(response.data);
			 $uibModalInstance.close({});
		});
	 
	 };
}
/***  ***/
function contenteditable() {
	  return {
	    restrict: "A",
	    require: "ngModel",
	    link: function(scope, element, attrs, ngModel) {

	      function read() {
	        ngModel.$setViewValue(element.html());
	      }

	      ngModel.$render = function() {
	        element.html(ngModel.$viewValue || "");
	      };

	      element.bind("blur keyup change", function() {
	        scope.$apply(read);
	      });
	    }
	  };
	}
routerApp.filter("nl2br", function($filter) {
	 return function(data) {
	   if (!data) return data;
	   return data.replace(/\n\r?/g, '<br />');
	 };
	});