package com.spring.account;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED)
public class AccountService {
	
	// 현위치 2번 ->
	// DI
	private AccountDAO accDAO;

	public void setAccDAO(AccountDAO accDAO) {
		this.accDAO = accDAO;
	}

	// 단위기능 2개를 한개의 메서드로 묶어 놓음.
	// service -> dao 3번으로 요청
	public void sendMoney() throws Exception {
		// updateBalance1 메서드가 호출되고 난 후
		accDAO.updateBalance1();
		// update
		accDAO.updateBalance2();
	}
}


