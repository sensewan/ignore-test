package com.project.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class IndexController {
	
	@Autowired
	private MemDAO m_dao;

	//                        ↱뒤에 아무것도 입력하지 않은 상태임 즉 프로젝트 실행하면 바로 실행됨
	@RequestMapping(value = {"/", "/index"})
	public String index() {
		
		return "index";  // views/index.jsp 를 의미
	}
	
	
	// ↱브라우저에서 /login이 입력될경우 실행됨
	@RequestMapping("/login")
	public String login() {
		
		return "login";  //views/index.jsp를 의미
	}
	
	// 생략하면 기본으로 get방식임 /   ↱ post방식으로 받아오는 방법 (위에랑 같은 "/login"이지만 구별이 됨)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(MemVO vo) {
		ModelAndView mv = new ModelAndView();
		
		System.out.println(vo.getM_id() +"/"+ vo.getM_pw());
		
		return mv;
	}
	
	
	@RequestMapping(value = "/loginChk", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginChk(MemVO vo1, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String m_id = vo1.getM_id();
		String m_pw = vo1.getM_pw();
		
		//System.out.println("파라미터확인->"+m_id +"//"+ m_pw);
		
		MemVO vo = m_dao.login(m_id, m_pw);
		
		//System.out.printf("로그인 성공하자.."+vo.getM_id()+"-"+vo.getM_pw()+"-"+vo.getM_name());
		
		String Msg = "성공";
		String fa ="fail";
		
		if (vo !=null) {
			map.put("Msg", Msg);
			//map.put("log", vo);	
			HttpSession session = request.getSession();
			session.setAttribute("login", vo);
			
		}else {
			map.put("fa", fa);
		}
		
		return map;
	}
	
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, String> logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("login");
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("home", "index");
		
		return map;
		
	}
	
	
}






