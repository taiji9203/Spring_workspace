package com.spring.ex04;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
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
//           java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd");
//			String joinDate = formatter.format(new java.util.Date());
//			
			   // 문자열 -> Date
//	        Date joinDate = formatter.parse(date);
           
           // 오라클 디비에 날짜 형식이 date이고, 기본 yyyy/MM/dd 형식이고.
           // 디비 입력시 sysdate로 입력하면, 오라클에 기본 형식으로 저장.
           // 화면에 출력할 때는, sysdate로 되어 있음. 
           // 화면에 출력할 때 원하는 형식으로 변경 해보기. 
           
           // 결론,
           // 디비로 함수 이용해서 원하는 형식을 변환하는 방법으로 변경.
           // 디비에서 받고 또 자바로 처리하는 부분 2번일이라서 효율적이지 못함. 
           // 디비에서 sql  문장에서 to_char 함수를 이용해서 변환된 날짜를 받아서
           // 바로 출력하는게 더 효율적.
           // 만약, 원하는 날짜 타입이 추가로 더 있다면.
           // 해당 select 로 각 형식의 예를 더 만들어서, 아이디만 변경해서 사용하기로.
			
           
           Map<String, String> memberMap=new HashMap<String, String>();
           memberMap.put("id", id);
           memberMap.put("pwd", pwd);
           memberMap.put("name", name);
           memberMap.put("email", email);
        // 시간 형식 추가작업.
//           memberMap.put("joinDate", joinDate);
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
    	  //뷰에서 입력 받은 데이터 가져오는 코드.
          String name=request.getParameter("name");
          String email=request.getParameter("email");
          
          // 임시 객체에 이름과 메일을 담고있다. 
          memberVO.setName(name);
          memberVO.setEmail(email);
          
          // memberVO 임시 객체에 데이터는 이름 과 메일 만 있고. 
          // 이 객체를 이제 해당 member.xml로 호출해서, 리스트 타입으로 반환 받기. 
          List<MemberVO> membersList =dao.searchMember(memberVO);
          
          // 검색 조건에 해당하는 리스트를 해당 뷰에서 가져가서 사용할수 있도록 설정하기. 
          request.setAttribute("membersList",membersList);
          
          // 해당 뷰 페이지 정보 저장. 
          nextPage="test03/listMembers.jsp";
          
       }
      // 각자 자기 디비에 있는 이름을 입력합니다. 
		// 이름 입력 해야하는데 아이디로 잘못 입력해서 다시 시도. 
		// 검색 조건에 다수의 정보를 한번에 검색을 했고.
      else if(action.equals("foreachSelect")) {
		  List<String> nameList = new ArrayList<String>();
		  nameList.add("홍길동2");
		  nameList.add("이상용");
		  nameList.add("lsy2");
		  
		  List<MemberVO> membersList=dao.foreachSelect(nameList);
		  request.setAttribute("membersList",membersList);
		  nextPage="test03/listMembers.jsp";
	   }
      // 다수의 정보를 한번에 추가하는 작업. 
      else if(action.equals("foreachInsert")) {
          List<MemberVO> memList = new ArrayList<MemberVO>();
          memList.add(new MemberVO("m1", "1234", "m1", "m1@test.com"));
          memList.add(new MemberVO("m2", "1234", "m2", "m2@test.com"));
          memList.add(new MemberVO("m3", "1234", "m3", "m3@test.com"));
          
          // 해당 디비 여러개를 한번에 추가하기. 
          int result=dao.foreachInsert(memList);
          
          nextPage="/mem4.do?action=listMembers";
	    }
      // 검색어를 like 연산자를 통해서 해당 내용을 검색.
      else if(action.equals("selectLike")) {
    	  // 각자 디비의 이름 샘플.
	      String name="lsy2";
	      
	      // 다른 곳에 데이터 넘기기, 지금은 문자열 형식으로 해당 이름 전달. 
		  List<MemberVO> membersList=dao.selectLike(name);
		  
		  request.setAttribute("membersList",membersList);
		  nextPage="test03/listMembers.jsp";
	   }
		
		// 해당 nextPage 에 포워딩 하는 작업. 
	   RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);  
	   dispatch.forward(request, response);


	}
}