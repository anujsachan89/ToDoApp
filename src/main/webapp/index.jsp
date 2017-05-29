

<html>
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

  <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<!-- <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>-->

<link rel="stylesheet"
	href="bower_components/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="bower_components/angular-ui-bootstrap-datetimepicker/datetimepicker.css"/>
<link rel="stylesheet" href="bower_components/angular-bootstrap/ui-bootstrap-csp.css"/>
<script src="bower_components/jquery/dist/jquery.js"></script>
<script src="bower_components/jquery/dist/jquery-ui.js"></script>
<script src="bower_components/bootstrap/dist/js/bootstrap.js"></script>
<script src="bower_components/angular/angular.js"></script>

<script
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<!-- 	<script src="bower_components/bootstrap-ui-datetime-picker/dist/datetime-picker.js"></script> -->
<script src="bower_components/angular-animate/angular-animate.js"></script>
<script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
<script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script> 
<script src="bower_components/angular-ui-bootstrap-datetimepicker/datetimepicker.js"></script>

<link rel="stylesheet" href="assets/style.css">

<style>
.navbar {
	border-radius: 0;
}
</style>

</head>

<body ng-app="routerApp">
	<!-- THIS IS WHERE WE WILL INJECT OUR CONTENT ============================== -->
	<div class="container">
		<div ui-view></div>
	</div>


</body>

<script src="js/app.js" charset="utf-8"></script>
<script src="controller/loginCtrl.js"></script>
<script src="controller/signupCtrl.js"></script>
<script src="controller/taskCtrl.js"></script>
<!--   <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script> -->

</html>
