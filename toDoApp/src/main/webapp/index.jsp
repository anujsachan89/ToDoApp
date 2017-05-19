<html>
<head>
<link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.css" > 
<script src="bower_components/jquery/dist/jquery.js"></script>
<script src="bower_components/bootstrap/dist/js/bootstrap.js"></script>
<script src= "bower_components/angular/angular.js"></script>
<script src= "bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<link rel="stylesheet" href="assets/style.css" >

 <style>
        .navbar { border-radius:0; }
    </style>

</head>

<body ng-app="routerApp">
<!-- THIS IS WHERE WE WILL INJECT OUR CONTENT ============================== -->
<div class="container">
    <div ui-view></div>
</div>


</body>

<script src="js/app.js" charset="utf-8"></script>
<script src = "controller/loginCtrl.js"></script>
<script src = "controller/signupCtrl.js"></script>
<script src = "controller/taskCtrl.js"></script>

</html>
