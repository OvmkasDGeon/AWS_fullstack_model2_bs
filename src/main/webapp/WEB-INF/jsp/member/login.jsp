<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@ include file="../common/head.jsp" %>
</head>

<body>
	<main class="container p-3 pb-5 ">
		<div class="col-lg-4 col-md-6 mx-auto">
			<div class="mt-5">
				<h3 class="text-center my-5">
					<a href="${pageContext.request.contextPath}/">더조은 아카데미</a>
				</h3>
			</div>
			<div class="card">
				<div class="card-header p-5">
					<h2>LOGIN</h2>
				</div>
				<div class="card-body">
					<form method="post">
						<div class="mb-3 mt-3">
							<label for="id" class="form-label">ID</label> <input type="text"
								class="form-control" id="id" placeholder="Enter id" name="id">
						</div>
						<div class="mb-3">
							<label for="pwd" class="form-label">Password</label> <input
								type="password" class="form-control" id="pwd"
								placeholder="Enter password" name="pw">
						</div>
						<div class="mb-3">
							<p class="text-danger">${param.msg}</p>
						</div>
						<div class="form-check mb-3">
							<label class="form-check-label"> <input
								class="form-check-input" type="checkbox" name="remember">
								Remember me
							</label>
						</div>
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="../common/footer.jsp" %>
</body>

</html>