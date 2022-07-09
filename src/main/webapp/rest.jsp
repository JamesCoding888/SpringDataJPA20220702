<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
	<!-- Head -->
	<%@include file="WEB-INF/view/include/head.jspf" %>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
</head>
<body style="padding: 20px" ng-app="myApp">

<div id="layout">
    <!-- Menu toggle -->
   	<%@include file="/WEB-INF/view/include/toggle.jspf" %>

   	<!-- Menu  -->
   	<%@include file="/WEB-INF/view/include/menu.jspf" %>

   

    <div id="main">
        <div class="header">
            <h1>User</h1>
            <h2>Rest</h2>
        </div>
	</div>
    <table>
    	<td valign="top">
    		<!-- User 表單 -->
    		<form class="pure-form">
    			<fieldset>User Form</fieldset>
    		</form>
    	</td>
    	<td valign="top">
    		<!-- User 列表 -->
    		<form class="pure-form">
    			<fieldset>User List</fieldset>
    			<table class="pure-table pure-table-bordered">
    				<thead>
    					<tr>
    						<th>ID</th>
    						<th>Name</th>
    						<th>Password</th>
    						<th>Birth</th>
    					</tr>
    				</thead>
    				<tbody>
    					  				
    				</tbody>
    			</table>
    		</form>
    	</td>
    </table>
</div>
<!-- Foot  -->
<%@include file="/WEB-INF/view/include/foot.jspf" %>

</body>
</html>  