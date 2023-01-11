package com.spring.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mem.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		//한글 깨짐 방지. 
		request.setCharacterEncoding("utf-8");
		// 응답 한글 깨짐 방지. 
		response.setContentType("text/html;charset=utf-8");
		
		
		MemberDAO dao = new MemberDAO();
		
		// 해당 디비에서 회원들의 목록을 가져온 결과
		List<MemberVO> membersList = dao.selectAllMemberList();
		
		//List<HashMap<String, String>> membersList = dao.selectAllMemberList();
		//해당 membersList 키: 값 형태로 request 에 담아두기.
		request.setAttribute("membersList", membersList);
		
		//지금은 mav 형식이 아니라, 기존에 배웠던 , 모델2 mvc 디스패쳐 서블릿 형식을 수동으로 설정. 
		// springMVC 에 가게되면, 알아서 방금 하는 디스패쳐 서블릿 작업을 알아서 해준다. 
		RequestDispatcher dispatch = request.getRequestDispatcher("test01/listMembers.jsp");
		dispatch.forward(request, response);
	}
}