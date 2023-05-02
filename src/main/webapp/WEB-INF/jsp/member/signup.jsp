<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<!-- U01TX0FVVEgyMDIzMDMyMzE1NTY1ODExMzYxOTc= -->
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
					<h2>Sign Up</h2>
				</div>
				<div class="card-body">
					<form method="post" autocomplete="off" class="form-horizontal form-material"><!-- autocomplete는 자동 완성  -->
						<h4 class="my-5"><i class="fas fa-list-ul"></i> 필수정보</h4>
						<div class="mb-3 mt-3 form-group">
							<label for="id" class="form-label d-block">ID</label> <input type="text" class="form-control border-0 border-bottom" id="id" placeholder="Enter id" name="id">
						</div>
						<div class="mb-3 form-group">
							<label for="pwd" class="form-label">Password</label> <input type="password" class="form-control border-0 border-bottom"" id="pwd" placeholder="Enter password" name="pw">
						</div>
						<div class="mb-3 form-group" >
							<label for="pwdChk" class="form-label">Password Confirm</label> <input type="password" class="form-control border-0 border-bottom" id="pwdChk" placeholder="Enter password repeat">
						</div>
						<h4 class="my-5"><i class="fas fa-list-ul"></i> 부가정보</h4>
						<div class="mb-3 form-group">
							<label for="name" class="form-label">Name</label> <input type="text" class="form-control border-0 border-bottom"" id="name" placeholder="Enter your name" name="name">
						</div>
						<div class="mb-3 form-group">
							<label for="email" class="form-label">Email</label> <input type="text" class="form-control border-0 border-bottom"" id="email" placeholder="Enter your email" name="email">
						</div>
						<div class="mb-3">
							<label for="addr" class="form-label">Address</label>
							 <div class="input-group form-group">
								 <input type="text" class="form-control border-0 border-bottom" id="addr" placeholder="" name="addr" readonly>
								 <button class="btn btn-primary" type="button" id="btnAddr">주소검색</button>
							 </div>
							 <div class="form-group">
							 <input type="text" class="form-control border-0 border-bottom mt-2" id="addrDetail" placeholder="상세주소" name="addrDetail">
							 </div>
						</div>
						<div class="mb-3">
							<p class="text-danger">${param.msg}</p>
						</div>
						
						<button type="submit" class="btn btn-primary">Sign Up</button>
					</form>
				</div>
			</div>
		</div>
	</main>
	<!-- The Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog modal-dialog-scrollable">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">주소 검색</h4>
					<br>
					<form id="jusoSearchFrm">
						<input type="hidden" name="currentPage" value="1"/> <!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
						<input type="hidden" name="countPerPage" value="10"/><!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
						<input type="hidden" name="resultType" value="json"/> <!-- 요청 변수 설정 (검색결과형식 설정, json) --> 
						<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDIzMDMyMzE1NTY1ODExMzYxOTc="/><!-- 요청 변수 설정 (승인키) -->
						<div class="input-group">
							<input type="text" name="keyword" value="" class="form-control w-75"/>
							<button class="btn btn-primary">검색</button>
						</div>
					</form>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
				
				
					
					<hr>
						<div class="result-search card">
							
						</div>
				</div>
				
				<ul class="pagination justify-content-center mt-3">
				  <li class="page-item"><a class="page-link" href="#">Previous</a></li>
				  <li class="page-item active"><a class="page-link" href="#">1</a></li>
				  <li class="page-item"><a class="page-link" href="#">2</a></li>
				  <li class="page-item"><a class="page-link" href="#">3</a></li>
				  <li class="page-item"><a class="page-link" href="#">4</a></li>
				  <li class="page-item"><a class="page-link" href="#">5</a></li>
				  <li class="page-item"><a class="page-link" href="#">Next</a></li>
				</ul>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<%@ include file="../common/footer.jsp" %>
<script>
$(function () {
	$("#btnAddr").click(function() {
		$("#myModal").modal("show").find(":text").focus();
		$("#addr").val("");
		$("#addrDetail").prop("readonly", true).val("");
	});
	
	$(".result-search").on("click","a",function() {
		event.preventDefault();		
		let str = $(this).text();
		$("#myModal").modal("hide");
		$("#addr").val(str);
		$("#addrDetail").prop("readonly", false).val("");
	});
	$("#jusoSearchFrm").submit(function(){
		console.log("event");
		
		let data = $(this).serialize();
		
		$.post({
			url :"https://business.juso.go.kr/addrlink/addrLinkApiJsonp.do",
			data : data,
			dataType : "jsonp",
			crossDomain: true,
			success : function(data){
				// $(".result-search").html(data);
				if(!(data.results.common.totalCount/1)){
					$(".result-search").html("일치하는 검색 결과가 없습니다.");
					return;
				}
				console.log(data);
				let result="";
				
				let arr = data.results.juso;
				for(var i in arr){
					result +=`
						<div class="card-body border p-3 \${i%2 ? 'bg-secondary':''}">
							<p class="card-text text-truncate">
								<span class="badge bg-primary d-inline-block me-2" style="width:45px">지번 </span>
								<a href="" class="\${i%2 ? 'text-white' : 'text-secondary'}">\${arr[i].jibunAddr}</a>
							</p>
							<p class="card-text text-truncate" >
								<span class="badge bg-primary d-inline-block me-2">도로명 </span>
								<a href="" class="\${i%2 ? 'text-white' : 'text-secondary'}">\${arr[i].roadAddr}</a>
							</p>
						</div>
					`;
					console.log(i, arr[i].jibunAddr, arr[i].roadAddr);
					$(".result-search").html(result);
				}
			}
		})
		console.log($(this).serialize());
		return false;
	})
})

</script>
</body>
</html>