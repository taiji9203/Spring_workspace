package com.spring.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class AccountController  extends MultiActionController  {
	
	// 현재 1번, -> AccountService 2번 위치
	// DI 주입
	   private AccountService accService ; 
	   public void setAccService(AccountService accService){
	     this.accService = accService;
	   }	

	   public ModelAndView sendMoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      ModelAndView mav=new ModelAndView();
	      // 현위치 1번 -> 2번 작업요청
	      accService.sendMoney();
	      mav.setViewName("result");
	      return mav;
	   }
}
