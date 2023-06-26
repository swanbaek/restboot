<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<div class="container m2">
	<script>
		const login_check=function(){
			if(!loginF.userid.value){
				alert('아이디를 입력하세요');
				loginF.userid.focus();
				return false;
			}
			if(!loginF.pwd.value){
				alert('비밀번호를 입력하세요');
				loginF.pwd.focus();
				return false;
			}
			return true;
		}
	</script>

	

	<h1 style='color:green;text-align:center'>Login</h1>
	<div id="loginDiv" class="row mt-4">
	<div class="col-8 offset-2">
		<form name="loginF" action="loginProc" method="post" onsubmit="return login_check()">
		<!--  action은 .loginProcessingUrl("/user/loginProc")에 기술된 url을 기술해야 한다
		
		.and().formLogin()// 폼 로그인 방식을 사용할 것임
				.loginPage("/user/login") //커스텀 페이지로 로그인 페이지를 변경한다////////////
				.loginProcessingUrl("/user/loginProc")
				.usernameParameter("userid")
				.passwordParameter("passwd")
			 -->
			<table border="0" class="table">
				<tr>
					<td width="20%" class="m1"><b>아이디</b></td>
					<td width="80%" class="m2">
						<input type="text" name="userid" class="form-control"							
						 id="userid" placeholder="ID">
					</td>
				</tr>
				<tr>
					<td width="20%" class="m1"><b>비밀번호</b></td>
					<td width="80%" class="m2">
						<input type="password" name="passwd" id="pwd"  class="form-control" placeholder="Password">
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
											
						<button class="btn btn-outline-primary">로그인</button>
						<!-- submit버튼 -->
					</td>
				</tr>
			</table>
		</form>	
		</div><!-- .col end -->
	</div><!-- .row end -->
</div><!--  .container end -->
