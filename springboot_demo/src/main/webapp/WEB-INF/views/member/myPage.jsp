<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ==security taglib 추가 (pom.xml에 관련 라이브러리 등록 해야 함) ========================== -->
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- ================================================================================ -->
<style>
.myPageTable {
	width: 70%;
	margin: 1em auto;
}

td {
	padding: 7px;
}

td:last-child {
	text-align: left;
}
</style>
<div class="container m2 py-5">
	<h1 class="mb-5 text-center">MyPage-<sec:authentication property="principal.member.name"/> 님 정보</h1>
	<sec:authorize access="!isAuthenticated()">
		<div class="alert alert-danger">
			<h3>회원 인증 페이지 - 로그인 해야 들어올 수 있는 페이지입니다</h3>
		</div>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
	<%-- <p>principal : <sec:authentication property="principal" /></p> --%>
		<table border="0" class="table table-striped mt-3">
			<tr>
				<td width="25%">아이디</td>
				<td width="75%"><b><sec:authentication property="principal.username"/></b></td>
			</tr>
			<tr>
				<td>연락처</td>
				<td><b><sec:authentication property="principal.member.allHp"/></b></td>
			</tr>
			<tr>
				<td>주소</td>
				<td><b><sec:authentication property="principal.member.allAddr"/></b></td>
			</tr>

			<tr>
				<td>회원상태-ROLE</td>
				<td><b><sec:authentication property="principal.member.role"/></b></td>
			</tr>
			<tr>
				<td colspan="2">
					<form name='f' method='post' action='modify'>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <!-- 이게 안나옴 -->
						<input type="hidden" name="idx" value="<sec:authentication property="principal.member.idx"/>">
						<button class="btn btn-success">정보수정|탈퇴</button>
					</form>
				</td>
			</tr>
		</table>
		</sec:authorize>
		<hr>
		<DIV class="text-center">
		<sec:authorize access="hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')">
			<a href="/index">메인으로 이동</a>| <a href="/user/userMain">User Page</a>
		</sec:authorize>		
		<sec:authorize access="hasAuthority('ROLE_ADMIN')">
			|<a href="/admin/adminMain">Admin Page</a>
		</sec:authorize>

		</DIV>
		<hr>
		
		


	
</div>
