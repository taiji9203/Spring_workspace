package com.spring.ex03;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.ex01.MemberVO;

@WebServlet("/mem3.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		// 디비 작업을 위한 객체
		MemberDAO dao = new MemberDAO();
		// 디비에서 불러온 내용을 담을 임시 객체.
		MemberVO memberVO = new MemberVO();

		// action : 해당 url 매핑의 주소를 분류 하기위한 키워드
		String action = request.getParameter("action");
		// nextPage : 직접 해당 뷰 페이지에 포워딩 시킬 뷰 위치.
		String nextPage = "";

		if (action == null || action.equals("listMembers")) {
			// 디비에서 전체 회원 목록을 다 받은 리스트.
			List<MemberVO> membersList = dao.selectAllMemberList();
			// 해당 내장객체에 membersList 키값으로 실제 리스트를 등록하는 과정.
			// 뷰에서 가져와서 작업이 가능. -> 페이지 간에 데이터를 전달하는 방식.
			request.setAttribute("membersList", membersList);
			// nextPage 여기 페이지로 직접 포워딩 하겠다.
			// viewResolver 부서 없이 직접 해당 뷰로 포워딩 중.
			nextPage = "test02/listMembers.jsp";
		} else if (action.equals("selectMemberById")) {
			// value : 아이디 부분을 , 뷰에서 입력된 또는 하드코딩된 값을 기준으로
			String id = request.getParameter("value");

			// 회원을 검색해서, 해당 회원의 아이디만 가져오는 작업.
			// 해당 아이디를 기준으로 디비 조회 후 해당 회원의 정보를 가져와서
			// 임시 객체에 담는 과정.
			memberVO = dao.selectMemberById(id);

			// 동일 과정.
			// 해당 내장객체에 membersList 키값으로 실제 리스트를 등록하는 과정.
			// 뷰에서 가져와서 작업이 가능. -> 페이지 간에 데이터를 전달하는 방식.
			request.setAttribute("member", memberVO);
			// nextPage 여기 페이지로 직접 포워딩 하겠다.
			// viewResolver 부서 없이 직접 해당 뷰로 포워딩 중.
			nextPage = "test02/memberInfo.jsp";
			
		} else if (action.equals("selectMemberByPwd")) {
			
			// value : pwd 부분을 , 뷰에서 입력된 또는 하드코딩된 값을 기준으로
			int pwd = Integer.parseInt(request.getParameter("value"));
			
			// 해당 패스워드를 사용하는 모든 멤버의 목록을 담는 작업. 
			List<MemberVO> membersList = dao.selectMemberByPwd(pwd);

			//동일 작업
			request.setAttribute("membersList", membersList);
			nextPage = "test02/listMembers.jsp";
		}

		// 해당 RequestDispatcher 로 직접 뷰 페이지에 포워딩 하는 작업. 
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);

	}
}