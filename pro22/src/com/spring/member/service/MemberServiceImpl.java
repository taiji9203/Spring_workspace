package com.spring.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.spring.member.dao.MemberDAO;

public class MemberServiceImpl implements MemberService {
	
	// 각가의 클래스에는 자기외에 다른 객체를 주입하는 방식이 다 있습니다. 
	// 참고 그림 캡처 한 부분을 참고 하시면. 
	private MemberDAO memberDAO;

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		//2번째 위치이고 (현재 위치는 서비스부분,) ->  memberDAO
		membersList = memberDAO.selectAllMembers();
		return membersList;
	}

}