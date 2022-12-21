<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
 <form>
 <input type="hidden" id="id" value="${principal.user.id }" />
		<div class="form-group">
			<label for="username">UserName:</label> 
			<input value="${principal.user.username }" type="text" class="form-control" placeholder="Enter UserName" id="username" readonly>
		</div>

		<div class="form-group">
			<label for="password">Password:</label>
			 <input type="password" class="form-control" id="password" placeholder="Enter password">
		</div>

		<div class="form-group">
			<label for="email">Email:</label>
			 <input value="${principal.user.email }" type="email" class="form-control" id="email" placeholder="Enter email">
		</div>
		
	</form>
	<button id="btn-update" class="btn btn-primary">회원정보수정</button>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>