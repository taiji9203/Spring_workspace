package com.spring.ex02;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	//싱글톤 패턴으로 해당 sqlMapper 객체를 가져오는 반복되는 메서드.
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

	// 이름 가져오기
	public String  selectName() {
		// 객체에 주소값 할당.
		sqlMapper = getInstance();
		//session 객체를 생성해서 디비 작업 준비
		SqlSession session = sqlMapper.openSession();
		//selectOne : 하나의 행을 가져온다. 
		String name = session.selectOne("mapper.member.selectName");
		return name;
	} 
		
	public int  selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int pwd = session.selectOne("mapper.member.selectPwd");
		return pwd;
	}


}