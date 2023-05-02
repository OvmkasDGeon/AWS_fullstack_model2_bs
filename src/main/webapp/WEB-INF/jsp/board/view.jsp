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
				<h2>Board View</h2>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="mb-3 mt-3">
						<label for="title" class="form-label"><i class="fas fa-heading"></i> title</label> <input
							type="text" class="form-control" id="title"
							placeholder="Enter title" name="title" value="${board.title}" readonly>
					</div>
					<div class="mb-3">
						<label for="content" class="form-label"><i class="fas fa-align-left"></i> content</label> 
						<textarea class="form-control"  id="content"	placeholder="Enter content" name="content" rows="10" readonly>${board.content}</textarea>
					</div>
					<div class="mb-3 mt-3">
						<label for="write" class="form-label"><i class="fas fa-user-edit"></i> write</label> <input
							type="text" class="form-control" id="writer"
							placeholder="Enter write" name="writer" value="${board.writer}" readonly>
					</div>
					<div class="mb-3 mt-3">
						<label for="write" class="form-label"><i class="fas fa-file-upload"></i> files</label>
						<c:forEach items="${board.attachs}" var="attach">
						<p><a href="${pageContext.request.contextPath}/download?${attach.queryString}">${attach.origin}</a></p>
						
						</c:forEach>
					</div>
					<div class="text-center">
						<a href="modify?bno=${board.bno}&${cri.fullQueryString}"	class="btn btn-warning">수정</a>
						<a href="remove?bno=${board.bno}&${cri.fullQueryString}"	class="btn btn-danger btn-remove">삭제</a>
						<a href="list?${cri.fullQueryString }"	class="btn btn-outline-primary">목록으로</a>
						<!-- 뒤로가기 인라인 스타일 -->
					</div>
					<div class="mb-3" id="replyArea">
						<p for="writer" class="form-label mb-4 border-bottom pb-3">
							<i class="fas fa-comment-dots"></i> replies</p>
						<div class="p-5 row mb-5">
							<textarea class="form-control mb-2" id="commentArea" placeholder="Enter comments" rows="3" ></textarea>
							<button type="button" class="btn btn-primary opacity-75">댓글 작성</button>
						</div>
						<ul class="container replies">
							<li class="list-unstyled px-4">
								<div class="mb-4 small text-center">
									<p>댓글이 없습니다.</p>
								</div>
							</li>
						</ul>
					</div>
				</form>
			</div>
		</div>
	</main>
	</div>
	<%@ include file="../common/footer.jsp"%>
	<script>
		$(".btn-remove").click(function() {
			return confirm("정말 삭제 하시겠습니까?");
		});
		let contextPath = "${pageContext.request.contextPath}";
		let replyPath = contextPath + "/reply";
		let bno = "${board.bno}";
		let writer = "${member.id}";
		showList();
		function showList() {
		$.ajax({
			url: replyPath,
			data : {bno:bno},
			success : list
		});
		}
		
	function list(replies) {
        //let jsonstr = '[{"rno":1,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":864},{"rno":2,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":864},{"rno":4,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":864}]';
        //let replies = JSON.parse(jsonstr);
        
        let str = "";
        if(!replies.length){
        	str += `
        		<li class="list-unstyled px-4">
				<div class="mb-4 small text-center">
					<p>댓글이 없습니다.</p>
				</div>
			</li>`;
			$(".replies").html(str);
			return ;
        }
        for (let i in replies) {
          let r = replies[i];
          /* console.log(r.rno, r.content, r.regDate, r.writer, r.bno); */
          //작성자와 세션의 id가 같은지 boolean으로 결과
		  let isMine = writer === r.writer;
		  /* let isMine = true; */
		  /* let isMine = ${member.id} === r.writer; */
          //아래 el문법 아님 class내에 data-rno가 아니라 별도의 속성이라고 생각하고 작성해야됨 )
          str += ` <li class="list-unstyled px-4" data-rno="\${r.rno}" data-writer="\${r.writer}">
              <div class="row">
                <a class="text-muted small mb-3 col text-decoration-none" href="">
                  <span class="fs-6 fw-bold">\${r.writer}</span>
                  <span class="mx-5">\${r.regDate}</span>
                </a>
                <div class="col text-end">`;
              
              str += isMine ? '<a href="" class="text-danger"><i class="fas fa-trash-alt "></i></a>' : ''
              str += `</div>
              </div>
              <div class="mb-4 small">
                <p>\${r.content}</p>
              </div>
            </li>`;
        }
        $(".replies").html(str);
    }
	
	 //댓글작성(세션 체크해야됨)
    $("#commentArea").next().click(function (){
      //console.log($("#commentArea").val());
      let content = $("#commentArea").val();
      if(!writer){
    	  alert("로그인 후 작성하세요");
    	  location.href=contextPath + "/member/login?href=" + encodeURIComponent(location.pathname + location.search+"#replyArea");
    	  
    	  return;
      }else if(!content){
    	  alert("댓글 내용을 입력하세요");
    	  return;
      }
      console.log({bno:bno, content:content, writer:writer});
      $.ajax({
    	  url : replyPath,
    	  data : {bno:bno, content:content, writer:writer},
    	  method : "POST",
    	  success : function(data){
    		  alert("댓글이 성공적으로 작성되었습니다.");
    		  /* 처리하고 입력문 비울때  */
    		  $("#commentArea").val("");/* textarea는 value로 빈문자열을 해줘야 하고 나머지는 html로 처리하면 된다 */ 
    		  showList();
    	  }
      })
      
    })
    //댓글 삭제(세션 체크해야됨)
    $(".replies").on("click","li a.text-danger", function() {
      event.preventDefault();
      if(!confirm("댓글을 삭제하시겠습니까?")){
    	  return false;
      }
      let rno = $(this).closest("li").data("rno");
      //작성자 가져오기
      let rid = $(this).closest("li").data("writer");
      //세션값 가져오기
      <%-- let member = '<%=session.getAttribute("member")%>'; --%>
      /* let id  = '${member.id}'; */
      $.ajax({
    	  url : replyPath + "?rno=" + rno,
    	  data : {rno:rno}, //문자열 타입
    	  method : "DELETE",
    	  success : function(data){
    		  /* console.log(data); */
    		  /* alert(member);
    		  alert(id); */
    		  if(data==1){
	    		  showList();
    		  }
    	  }
      })
    })
    //함수 호출
    //list();
	</script>
</body>

</html>