package com.spring.ex03;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	// 공통 작업 , 디비 연결 위한 기본 설정.
	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
	
	
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		// 조회된 모든 회원을 담기 위한 임시 리스트 
		List<MemberVO> memlist = null;
		
		// mem.do 에서 했던 member.xml에 있는 아이디 부분. 
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}

	// 검색된 회원 1명을 MemberVO 형식으로 리턴함. 
	public MemberVO selectMemberById(String id){
	      sqlMapper=getInstance();
	SqlSession session=sqlMapper.openSession();
	
	// 매개변수 2번째 항목에, 동적인 데이터를 넣는 방식입니다. 
	//member.xml 에서 동적으로 아이디를 받는 부분은 표기를 "#{동적으로 사용할 아이디}" 예) id=#{id}		
	      MemberVO memberVO=session.selectOne("mapper.member.selectMemberById",id);
	      return memberVO;		
	   }
	
	//패스워드가 동일한 회원들을 리턴함. 
	public List<MemberVO> selectMemberByPwd(int pwd) {
	sqlMapper = getInstance();
	SqlSession session = sqlMapper.openSession();
	List<MemberVO> membersList = null;
	membersList= session.selectList("mapper.member.selectMemberByPwd", pwd);
	return membersList;
	}



}