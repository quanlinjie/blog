<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
	<input type="hidden" id="id" value="${board.id }" />
		<div class="form-group">
			<label for="title">제목 </label> 
			<input value="${board.title }" type="text" class="form-control" id="title" placeholder="Enter the Title">
		</div>

		<div class="form-group">
			<label for="content">내용</label>
			<textarea class="form-control summernote" rows="5" id="content">${ board.content }</textarea>
		</div>
	</form>

	<button id="btn-update" class="btn btn-primary">수정하기</button>
</div>

<script>
      $('.summernote').summernote({
        placeholder: '내용을 입력하세요.',
        tabsize: 2,
        height: 300
      });
    </script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>

