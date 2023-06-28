<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
    	.myPageTable{
    		width: 70%;
    		margin: 1em auto;
    	}
    	td{
    		padding:7px;
    	}
    	td:last-child{
    		text-align:left;
    		
    	}
    </style>
    <div class="container m2">
    	<h1>MyPage-${loginUser.name} 님 정보</h1>
    	<p>회원 인증 페이지 - 로그인 해야 들어올 수 있는 페이지입니다</p>
    	<table border="0" class="table table-striped mt-3">
    		<tr>
    			<td width="25%">아이디</td>
    			<td width="75%"><b>${loginUser.userid}</b></td>
    		</tr>
    		<tr>
    			<td>연락처</td>
    			<td><b>${loginUser.allHp}</b></td>
    		</tr>
    		<tr>
    			<td>주소</td>
    			<td><b>${loginUser.allAddr}</b></td>
    		</tr>
    		<tr>
    			<td>마일리지</td>
    			<td><b>${loginUser.mileage} 점</b></td>
    		</tr>
    		<tr>
    			<td>회원상태</td>
    			<td><b>${loginUser.mstateStr}</b></td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<form name='f' method='post' action='modify'>
    					<input type="hidden" name="idx" value="${loginUser.idx}">
    					<button class="btn btn-success">정보수정|탈퇴</button>
    				</form>    				
    			</td>
    		</tr>
    	</table>
    	
    </div>
