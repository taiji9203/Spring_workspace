package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;

public class MemberControllerImpl extends MultiActionController implements MemberController {
	
	//DI 주입 방식 중 하나인데. 인터페이스 형으로 일단, 선언만 함. (현재 상태 null 상태)
	private MemberService memberService;

	// 그리고, 세터를 이용해서 해당 값(메모리 위치값) 참조형 변수에 할당함. 
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

//  /member/listMembers.do -> listMembers 요청명의 주소와 동일한 메서드를 찾습니다. 
	// 여기 메서드  입니다. listMembers
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//21장에 구체적으로 콘솔에 다 찍어서 확인하는 작업이 있음. 복습때 1번이상 정독 권함. 
		String viewName = getViewName(request);
		//궁금하면 viewName 한번 찍어 보면 됨. 
		System.out.println("viewName  : " + viewName);
		
		//이제 해당 디비에 있는 내용에 접근하는 시작 부분. 
		// 컨트롤로에서 해당 서비스로 호출 하는 부분. 각 다른 파일로 이동하는 절차를 공부하면됨. 
		// 1)MemberControllerImpl ->2) MemberServiceImpl ->3) MemberDAOImpl ->4) dataSource
		// 해당 메서드를 다 호출 후, 다시 되돌아 올때, 해당 메서드의 반환 타입으로 되돌아오는 순서는 역순입니다. 
		List membersList = memberService.listMembers();
		
		//해당뷰에 값을 던지겠다. listMembers 해당 뷰에 던지겠다. 
		ModelAndView mav = new ModelAndView(viewName);
		
		// 데이터를 던지는 부분입니다. 
		mav.addObject("membersList", membersList);
		return mav;
	}
	
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String fileName = uri.substring(begin, end);
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
		}
		return fileName;
	}
}