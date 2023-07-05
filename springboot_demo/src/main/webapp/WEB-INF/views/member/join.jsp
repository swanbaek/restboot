<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- js파일 참조-------------------------------- -->
<script type="text/javascript" src="/js/userCheck.js"></script>
<!-- ------------------------------------------ -->
<div class="container">
	<h1 class="text-center text-secondary mb-3">
	Signup
	<!-- <img src="/img/signup.png"> -->
	</h1>
	<form name="mf" action="signup" method="post">
	<!-- ---일반회원: role(USER), 관리자(ADMIN)------------------------------- -->
	<input type="hidden" name="role" value="USER">
	<!--  ---------------------------------------------------------- -->
	<div class="row">
	<div class="col-8 offset-2">
	<table class='table'>
		<tr>
			<td width="20%" class="m1"><b>이  름</b></td>
			<td width="80%" class="m2">
			<input type="text" name="name" id="name" class="form-control"
			 placeholder="Name">
			<br>
			<span class="ck">*이름은 한글만 가능해요</span>
			</td>
		</tr>
		<tr>
			<td width="20%" class="m1"><b>아이디</b></td>
			<td width="80%" class="m2">
			<div class="row">
				<div class="col-6">
					<input type="text" name="userid" id="userid"  class="form-control"
					 placeholder="User ID">
				 </div>
				 <div class="col-4">
				 	<button type="button" onclick="open_idcheck()" class="btn btn-success">아이디 중복 체크</button>
				 </div>
			</div> 
			<br>
			<span class="ck">*아이디는 영문자,숫자,_,!만 사용 가능해요</span>
			</td>
		</tr>
		<tr>
			<td width="20%" class="m1"><b>비밀번호</b></td>
			<td width="80%" class="m2">
			<input type="password" name="passwd" id="pwd"  class="form-control"
			 placeholder="Password">
			<br>
			<span class="ck">*비밀번호는 문자,숫자,!,.포함해서 4~8자 이내</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" class="m1"><b>비밀번호 확인</b></td>
			<td width="80%" class="m2">
			<input type="password" name="passwd2" id="pwd2"  class="form-control"
			 placeholder="Re Password">
			<br>
			</td>
		</tr>
		<tr>
			<td width="20%" class="m1"><b>연락처</b></td>
			<td width="80%" class="m2">
			<div class="row">
				<div class="col-3">
			<input type="text" name="hp1" id="hp1"  class="form-control" placeholder="HP1" maxlength="3">
				</div>
				<div class="col-3">
			<input type="text" name="hp2" id="hp2"  class="form-control" placeholder="HP2" maxlength="4">
				</div>
				<div class="col-3">
			<input type="text" name="hp3" id="hp3"  class="form-control" placeholder="HP3" maxlength="4">
				</div>
			</div>
			
			<br>
			<span class="ck">*앞자리(010|011)중에 하나-(숫자 3~4자리)-(숫자 4자리)
			만 가능해요
			</span>
			</td>
		</tr>
		<tr>
			<td width="20%" class="m1"><b>우편번호</b></td>
			<td width="80%" class="m2">
			<div class="row">
				<div class="col-6">
					<input type="text" name="post" id="post" class="form-control"
					 placeholder="Post" maxlength="5">
				 </div>
				 <div class="col-4">
					<button type="button" onclick=""  class="btn btn-success">우편번호 찾기</button>
				</div>
			</div> 
			<br>			
			</td>
		</tr>
		
		<tr>
			<td width="20%" class="m1"><b>주  소</b></td>
			<td width="80%" class="m2">
			<input type="text" name="addr1" id="addr1"  class="form-control"
			 placeholder="Address1" style="margin-bottom:5px;">
			<br>
			<input type="text" name="addr2" id="addr2"  class="form-control"
			 placeholder="Address2">
			</td>
		</tr>
		<tr>
			<td colspan="2" class="m2" style="text-align:center">
				<button type="button" onclick="member_check()" class="btn btn-primary">회원가입</button>
				<button type="reset" class="btn btn-danger">다시쓰기</button>
			</td>
		</tr>
	
	</table>
	</div>
	</div>
	</form>
</div>


