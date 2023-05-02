package kr.co.ovmkas.jsp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//두고두고 쓸 수 있음
//한글입력에 대한 처리
@WebFilter("/*")//모든 요청에 필터를 다 적용하는 것 
public class EncodingFilter implements Filter{

	//service의 역할과 동일
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
	}
}
