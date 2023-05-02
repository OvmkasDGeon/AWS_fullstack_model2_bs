<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/head.jsp"%>
</head>
<body>
<div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
	<%@include file="../common/header.jsp"%>
	<main class="container p-3 pb-5">
		<div class="card">
			<div class="card-header p-5">
				<h2>Board Write</h2>
			</div>
			<div class="card-body">
				<form method="post" enctype="multipart/form-data">
					<div class="mb-3 mt-3">
						<label for="category" class="form-label"><i class="fas fa-list-ol"></i> category</label>
						<select name="category" id="category" class="form-select">
							<c:forEach items="${categories}" var="cate" varStatus="stat">
								<option value="${stat.count}" ${stat.count == param.category ? 'selected':'' }>${cate}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mb-3 mt-3">
						<label for="title" class="form-label"><i class="fas fa-heading"></i> title</label> <input
							type="text" class="form-control" id="title"
							placeholder="Enter title" name="title">
					</div>
					<div class="mb-3">
						<label for="content" class="form-label"><i class="fas fa-align-left"></i> content</label> 
						<textarea class="form-control" id="content"	placeholder="Enter content" name="content" rows="10" ></textarea>
					</div>
					<div class="mb-3 mt-3">
						<label for="write" class="form-label"><i class="fas fa-user-edit"></i> write</label> <input
							type="text" class="form-control" id="writer"
							placeholder="Enter writer" name="writer" value="${member.id}" readonly>
					</div>
					<div class="mb-3 mt-3">
						<label for="file" class="form-label"><i class="fas fa-file-upload"></i> files</label> <input
							type="file" class="form-control" id="file" name="file" multiple>
					</div>
					<div class="text-center">
						<button class="btn btn-primary">글쓰기</button>
						<a href="javascript:history.back()"
							class="btn btn-outline-primary">목록으로</a>
						<!-- 뒤로가기 인라인 스타일 -->
					</div>
				</form>
			</div>
		</div>
	</main>
	</div>
	<%@ include file="../common/footer.jsp"%>
</body>

</html>