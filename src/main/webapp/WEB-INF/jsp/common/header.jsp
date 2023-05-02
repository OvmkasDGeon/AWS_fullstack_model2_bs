<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
        <header class="topbar" data-navbarbg="skin5">
            <nav class="navbar top-navbar navbar-expand-md navbar-dark">
                <div class="navbar-header" data-logobg="skin6">
                    <!-- ============================================================== -->
                    <!-- Logo -->
                    <!-- ============================================================== -->
                    <a class="navbar-brand justify-content" href="${pageContext.request.contextPath}/">
                        <!-- Logo icon -->
                       <b class="logo-icon">
                            <!-- Dark Logo icon -->
                            <img src="${pageContext.request.contextPath}/img/tj_logo_sm.jpg" alt="homepage" />
                        </b> 
                        <!--End Logo icon -->
                        <!-- Logo text -->
                        <span class="logo-text">
                            <!-- dark Logo text -->
                            <img src="${pageContext.request.contextPath}/img/tj_text_sm.png" alt="homepage" />
                        </span>
                    </a>
                    <!-- ============================================================== -->
                    <!-- End Logo -->
                    <!-- ============================================================== -->
                    <!-- ============================================================== -->
                    <!-- toggle and nav items -->
                    <!-- ============================================================== -->
                    <a class="nav-toggler waves-effect waves-light text-dark d-block d-md-none"
                        href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
                </div>
                <!-- ============================================================== -->
                <!-- End Logo -->
                <!-- ============================================================== -->
                <div class="navbar-collapse collapse" id="navbarSupportedContent" data-navbarbg="skin5">
                    <!-- Links -->
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/board/list?category=2">자유게시판</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/board/list">공지사항</a>
                        </li>
                         <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/board/list?category=3">자료실</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/board/list?category=4">갤러리</a>
                        </li>
                    </ul>
                    <!-- ============================================================== -->
                    <!-- Right side toggle and nav items -->
                    <!-- ============================================================== -->
                    <ul class="navbar-nav ms-auto d-flex align-items-center px-5">
                    	<c:if test="${empty member}">
	                        <li>
	                            <a href="${pageContext.request.contextPath}/member/login" class="profile-pic small">로그인</a>
	                        </li>
	                        <li>
	                            <a href="${pageContext.request.contextPath}/member/signup" class="profile-pic small">회원가입</a>
	                        </li>
                        </c:if>
                        <c:if test="${not empty member}">
	 						<li class="dropdown dropstart">
	                            <a class="profile-pic dropdown-toggle" href="#" data-bs-toggle="dropdown">
	                                <img src="${pageContext.request.contextPath}/plugins/images/users/varun.jpg" alt="user-img" width="36"
	                                    class="img-circle"><span class="text-white font-medium">${member.id}</span></a>
	                        	<ul class="dropdown-menu p-0 ">
	                        		<li class="small"><a href="${pageContext.request.contextPath}/member/mypage" class="dropdown-item">마이페이지</a></li>
	                        		<li class="small"><a href="${pageContext.request.contextPath}/member/logout" class="dropdown-item">로그아웃</a></li>
	                        	</ul>
	                        </li>
                        </c:if>
                        <!-- ============================================================== -->
                        <!-- User profile and search -->
                        <!-- ============================================================== -->
                    </ul>
                </div>
            </nav>
        </header>
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->