package com.spring.ex04;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.ex01.MemberVO;

@WebServlet("/mem4.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = new MemberVO();
		String action = request.getParameter("action");
		String nextPage = "";

		if (action == null || action.equals("listMembers")) {
			List<MemberVO> membersList = dao.selectAllMemberList();
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
		} else if (action.equals("selectMemberById")) {
			String id = request.getParameter("value");
			memberVO = dao.selectMemberById(id);
			request.setAttribute("member", memberVO);
			nextPage = "test03/memberInfo.jsp";
		} 
		
		//검색하기 위한 창 매핑 부분.
		else if (action.equals("searchForm")) {
			//중간에 데이터 작업 부분은 다 지움. 
			// 이유는 해당 폼으로 가는 뷰만 있으면 되어서.
			nextPage = "test03/searchMember.jsp";
		} 
		
		// 회원 가입 창으로 가는 부분이 없어서 임시로 매핑 주소 추가 연습해보기. 
		else if (action.equals("joinForm")) {
			//중간에 데이터 작업 부분은 다 지움. 
			// 이유는 해당 폼으로 가는 뷰만 있으면 되어서.
			nextPage = "test03/memberForm.jsp";
		} 
		//뷰에서 수정 링크 부분 클릭 시, 해당 아이디를 조회해서
		// 해당 아이디에 맞는 정보를 , 회원 수정창에서 가져오기까지.
		//url 매핑의 주소: updateForm
		else if (action.equals("updateForm")) {
			// 해당 링크의 ? 뒤에 속성 부분에 이름을 id로 해서
			// 가져올 때도 id로 가져오기.
			String id = request.getParameter("id");
			// id에 맞는 정보를 가져와서 임시 객체에 담아두기. 
			memberVO = dao.selectMemberById(id);
			// 뷰에서 사용하려면 request 에 데이터를(한명의 회원의 정보) 담아두기 
			request.setAttribute("member", memberVO);
			// 뷰에서 사용할 때 member. 해당 getter로 가져오기.  
			nextPage = "test03/modMember.jsp";
		}
		
		else if (action.equals("selectMemberByPwd")) {
			int pwd = Integer.parseInt(request.getParameter("value"));
			List<MemberVO> membersList = dao.selectMemberByPwd(pwd);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			//action : insertMember 매핑 주소가 해당하면.
		}else if(action.equals("insertMember")) {
			/* 사용자들로 부터 입력 받은 내용 각각을 임시 객체에 담는 작업 */
			String id=request.getParameter("id");
            String pwd=request.getParameter("pwd");
            String name=request.getParameter("name");
            String email = request.getParameter("email");
            memberVO.setId(id);
            memberVO.setPwd(pwd);
            memberVO.setName(name);
            memberVO.setEmail(email);
            /* 사용자들로 부터 입력 받은 내용 각각을 임시 객체에 담는 작업 */
            
            // 실제 디비에 해당 회원의 정보를 전달하는 과정.
            // 디비에 회원 정보를 추가, 저장된 상태. 
            dao.insertMember(memberVO);
            
            // 결과 뷰 페이지의 정보를 담았다.              
            nextPage="/mem4.do?action=listMembers";
       }else if(action.equals("insertMember2")) {
    	   // 이부분에 날짜의 형식을 지정하는 코드를 사용해서
    	   // 해당 객체에 넣어야 함. 
    	   // 디비 상에서 해당 컬럼에서 sysdate 디폴트 하는 방법 있지만. 
           String id=request.getParameter("id");
           String pwd=request.getParameter("pwd");
           String name=request.getParameter("name");
           String email = request.getParameter("email");      
           
           //날짜 형식은 jsp 수업 때 , 게시판에 글 등록시 사용했던 코드.
           // 시간 형식 추가작업.
           java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd");
		   String joinDate = formatter.format(new java.util.Date());
			
			   // 문자열 -> Date
//	        Date joinDate = formatter.parse(date);
			
           
           Map<String, String> memberMap=new HashMap<String, String>();
           memberMap.put("id", id);
           memberMap.put("pwd", pwd);
           memberMap.put("name", name);
           memberMap.put("email", email);
        // 시간 형식 추가작업.
           memberMap.put("joinDate", joinDate);
           dao.insertMember2(memberMap);
           nextPage="/mem4.do?action=listMembers";
      }
       // 회원 수정창에 입력 후, 버튼 클릭시, 업데이트를 처리하는 부분. 
       else if(action.equals("updateMember")){
          String id=request.getParameter("id");
          String pwd=request.getParameter("pwd");
          String name=request.getParameter("name");
          String email = request.getParameter("email");
          memberVO.setId(id);
          memberVO.setPwd(pwd);
          memberVO.setName(name);
          memberVO.setEmail(email);
          dao.updateMember(memberVO);
          nextPage="/mem4.do?action=listMembers";     
      }else if(action.equals("deleteMember")){
  	      String id=request.getParameter("id");
	      dao.deleteMember(id);
	      nextPage="/mem4.do?action=listMembers";
      }else if(action.equals("searchMember")){
          String name=request.getParameter("name");
          String email=request.getParameter("email");
          memberVO.setName(name);
          memberVO.setEmail(email);
          List<MemberVO> membersList =dao.searchMember(memberVO);
          request.setAttribute("membersList",membersList);
          nextPage="test03/listMembers.jsp";
       }else if(action.equals("foreachSelect")) {
		  List<String> nameList = new ArrayList<String>();
		  nameList.add("서태지");
		  nameList.add("이상용");
		  nameList.add("홍길동");
		  
		  List<MemberVO> membersList=dao.foreachSelect(nameList);
		  request.setAttribute("membersList",membersList);
		  nextPage="test03/listMembers.jsp"; 

       }
       //다수의 정보를 
       else if(action.equals("foreachInsert")) {
          List<MemberVO> memList = new ArrayList<MemberVO>();
          memList.add(new MemberVO("m1", "1234", "m1", "m1@test.com"));
          memList.add(new MemberVO("m2", "1234", "m2", "m2@test.com"));
          memList.add(new MemberVO("m3", "1234", "m3", "m3@test.com"));
          
          //해당
          int result=dao.foreachInsert(memList);
          nextPage="/mem4.do?action=listMembers";
          
          // 검색어를 like 연산자를 통해서 해당 내용을 검색
	    }else if(action.equals("selectLike")) {
	      String name="서태지";
	      
	      // 다른 곳에 데이터 넘기기, 
		  List<MemberVO> membersList=dao.selectLike(name);
		  request.setAttribute("membersList",membersList);
		  nextPage="test03/listMembers.jsp";
	   }
		
		// 해당 nextPage 에 포워딩 하는 작업. 
	   RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);  
	   dispatch.forward(request, response);


	}
}