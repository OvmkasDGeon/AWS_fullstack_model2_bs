<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!-- 체크박스를 위한 것 -->
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="../common/head.jsp" %>
</head>
<body>
<div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
  <%@include file="../common/header.jsp" %>
  <main class="container p-3 pb-5">
    <div class="card">
    <div class="card-header p-5">
    	<h2>${categories[page.cri.category-1]}</h2>
    </div>
      <div class="card-body">
        <div class="clearfix my-3 list-header">
          <div class="float-start">
            <c:set var="amounts" value="5,10,25,50,100"/>
            <select class="form-select amount">
            <c:forTokens items="${amounts}" delims="," var="amount">
              <option value="${amount}" ${page.cri.amount == amount ? 'selected':''}>${amount}개씩 보기</option>
            </c:forTokens>
            </select>
          </div>
          <form>
                 <!-- <select class="form-select">
          	<option>제목</option>
          	<option>내용</option>
          	<option>작성자</option>
          	<option>제목+내용</option>
          	<option>제목+작성자</option>
          	<option>내용+작성자</option>
          	<option>제목+내용+작성자</option>          	
          </select> -->
          <input type="hidden" name="page" value="1">
          <input type="hidden" name="amount" value="${page.cri.amount}">
          <input type="hidden" name="category" value="${page.cri.category}">
          <%-- <p>${fn:contains(fn:join(page.cri.type, ','), 'title') ? 'checked' : ''}</p>--%><!-- 상단에 지시자 적어야됨 EL문법내에 사용하는 것 -->          
          <div class="float-start mx-5 row">
            <div class="col">
          	  <input class="form-check-input" type="checkbox" id="check1" name="type" value="title" ${fn:contains(fn:join(page.cri.type, ','), 'title') ? 'checked' : ''} >
          	  <label class="form-check-label" for="check1">제목</label>
          
          	  <input class="form-check-input" type="checkbox" id="check2" name="type" value="content" ${fn:contains(fn:join(page.cri.type, ','), 'content') ? 'checked' : ''}>
          	  <label class="form-check-label" for="check2">내용</label>
          
          	  <input class="form-check-input" type="checkbox" id="check3" name="type" value="writer" ${fn:contains(fn:join(page.cri.type, ','), 'writer') ? 'checked' : ''}>
          	  <label class="form-check-label" for="check3">작성자</label>
            </div>
            <div class="input-group mb-3 col" >
              <input type="text" class="form-control" placeholder="Search" name="keyword" value="${page.cri.keyword}">
              <button class="btn btn-success" type="submit"><i class="fas fa-search"></i></button>
            </div>
          </div>  
          </form>    
          		<!-- 같은 경로이기때문에write라고 쓴다 sevletname @WebServlet이다-->  
          <a href="write?category=${page.cri.category}" class="float-end btn btn-primary">write</a>
        </div>
        <div class="row">
          <c:forEach  var="board" items="${boards}" varStatus="stat">
          <a href="view?bno=${board.bno}&${page.cri.fullQueryString}" class="text-decoration-none col-12 col-sm-4 col-lg-3 d-block my-3 text-center">
          <c:if test="${board.content == null}">
          	<img src="${pageContext.request.contextPath}/img/no-image.jpg" class="img-thumbnail">
          </c:if>
          <c:if test="${board.content != null}">
          	<img src="${pageContext.request.contextPath}/display?fullpath=${board.content}" class="img-thumbnail">
          </c:if>
          
          	<div class="text-white small bg-secondary d-flex justify-content-between p-3">
             <div class="text-truncate">${board.title}</div>
                <div class="ext-truncate">${board.writer}</div>
                </div>
          </a>
          </c:forEach>
        </div>
        <ul class="pagination mt-3 justify-content-center">
        <c:if test="${page.doublePrev}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.startPage-1}&${page.cri.queryString}"><i class="fas fa-angle-double-left"></i></a></li>
        </c:if>
        <c:if test="${page.prev}">
          <li class="page-item"><a class="page-link " href="list?pageNum=${page.cri.pageNum-1}&${page.cri.queryString}"><i class="fas fa-angle-left"></i></a></li>
        </c:if>
        <c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">
          <li class="page-item"><a class="page-link ${page.cri.pageNum== i ? 'active' : ''}" href="list?pageNum=${i}&${page.cri.queryString}">${i}</a></li>
        </c:forEach>
        <c:if test="${page.next}">
          <li class="page-item"><a class="page-link" href="list?pageNum=${page.cri.pageNum+1}&${page.cri.queryString}"><i class="fas fa-angle-right"></i></a></li>
        </c:if>
        <c:if test="${page.doubleNext}">
          <li class="page-item"><a class="page-link " href="list?pageNum=${page.endPage+1}&${page.cri.queryString}"><i class="fas fa-angle-double-right"></i></a></li>
        </c:if>
        </ul>

      </div>
    </div>
  </main>
  </div>
 <%@ include file="../common/footer.jsp" %>
</body>
<script>
/* 개씩 보기의 값이 change되었을 때 pageNum, amount, category 등을 가지고 있는 것 */
$(".amount").change(function (){
	/* console.log($(this).val()); */
	let page = "${page.cri.pageNum}";
	let amount = $(this).val();
	let category = "${page.cri.pageNum}";
	let type = "${page.cri.typeStr}";
	/* let keyword = "${page.cri.keyword}"; */
	/* console.log(type); */
	let obj = {pageNum:page, amount:amount, category:category};
	location.search = $.param(obj) + type;
});
/* 제목,내용,작성자에 대한 기본 체크 박스 */
$(".list-header :checkbox:checked").length ? '' : $(".list-header :checkbox:eq(0)").prop("checked", true);
$(".list-header form").submit(function (){
	//alert($(this).find(":checkbox:checked").length);
	if(!$(this).find(":checkbox:checked").length || !$(this).find(":text").val().trim() ){
		alert("검색 타입을 지정하고 검색어를 입력하세요");
		return false;
	}
	//|| $(this).find(":text").val().trim()
})
</script>
</html>