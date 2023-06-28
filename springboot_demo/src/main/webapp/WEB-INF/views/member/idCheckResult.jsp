<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
	.jumbotron,.navbar{
		display:none;
	}
</style>
<!--  ------------------------------------ -->
<script src="/js/userCheck.js"></script>
<!--  ------------------------------------ -->
 

<c:if test="${result eq 'fail'}">
<div class="container m2" style="margin-top:2em">
	<h3>ID로 [<span class="text-danger"> ${userid} </span>]은 이미 사용중 입니다</h3>
	<br>
	
	<form name="idf" action="idCheck" method="post">
		<label for="userid">아이디</label>
		<input type="text" name="userid" id="userid" placeholder="User ID" autofocus="autofocus" class="form-control">
		<button  class="btn btn-success" type="button" onclick="id_check()">확  인</button>
	</form>
</div>   
</c:if>
<c:if test="${result eq 'success'}">
<div class="container" style="margin-top:2em">
	<h3>ID로 [<span class="text-primary">${userid}</span>]은 사용 가능합니다</h3>
	<br>
	<button class="btn btn-success" onclick="set_id('${userid}')">닫  기</button>
</div> 
</c:if>