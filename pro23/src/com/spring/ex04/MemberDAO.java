package com.spring.ex04;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

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
	
	// 전체 회원 조회
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist = null;
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}

	// 해당 아이디로 검색하고 , 1명의 회원 조회.
	public MemberVO selectMemberById(String id){
	      sqlMapper=getInstance();
	SqlSession session=sqlMapper.openSession();
	      MemberVO memberVO=session.selectOne("mapper.member.selectMemberById",id);
	      return memberVO;		
	   }

	// 해당 패스워드를 사용하는 회원들 조회.
	public List<MemberVO> selectMemberByPwd(int pwd) {
	sqlMapper = getInstance();
	SqlSession session = sqlMapper.openSession();
	List<MemberVO> membersList = null;
	membersList= session.selectList("mapper.member.selectMemberByPwd", pwd);
	return membersList;
	}

	// 회원 가입시 호출되는 메서드. 
	// 매개변수 memberVO 에는 사용자들로 부터 입력 받은 값들이 들어 있습니다. 
	public int insertMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		
		// 2번째 매개변수 부분에 객체 형식으로 들어갑니다. 
		// 기존의 jdbc에서 회원가입시 각 항목을 각각 입력한 반면에
		// 마이바티스에서는 객체 형식으로 한번에 다 입력 하고 있음. 
		result = session.insert("mapper.member.insertMember", memberVO);
		session.commit();
		return result;
	}
	
	//2번째 형식, 입력 값의 타입이 해쉬맵 형이다. 
	public int insertMember2(Map<String,String> memberMap){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        
        
        int result= result=session.insert("mapper.member.insertMember2",memberMap);
        session.commit();	
        return result;		
	}

	public int updateMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = session.update("mapper.member.updateMember", memberVO);
		session.commit();
		return result;
	}   

	
    public int deleteMember(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		result = session.delete("mapper.member.deleteMember", id);
		session.commit();
		return result;
    } 
    
    public List<MemberVO>  searchMember(MemberVO  memberVO){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        List list=session.selectList("mapper.member.searchMember",memberVO);
        return list;		
    } 

    // 매개변수에 하드코딩된 이름이 입력된 리스트가 매개변수로 들어옴.
    // 현재, name : a1, b1, a4이 담긴 리스트 상태입니다
    public List<MemberVO>  foreachSelect(List nameList){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        List list=session.selectList("mapper.member.foreachSelect",nameList);
        return list;		
    }
    
    public int  foreachInsert(List memList){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        int result = session.insert("mapper.member.foreachInsert",memList);
        session.commit();
        return result ;		
     }
    
    // 이름으로 검색하기.
    public List<MemberVO>  selectLike(String name){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        //실제 디비 검색작업은 여기서, 매개변수 2번째 자리
        List list=session.selectList("mapper.member.selectLike",name);
        return list;		
    }


}