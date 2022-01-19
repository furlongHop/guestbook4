package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@RequestMapping(value="/guest")
@Controller
public class guestbookController {

	//필드
	@Autowired
	private GuestbookDao guestbookDao;
	
	//생성자
	
	//메소드 g/s
	
	//메소드 일반
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String addList(Model model) {
		System.out.println("list");
		
		List<GuestbookVo> gbList = guestbookDao.getList();
		
		model.addAttribute("gbList", gbList);
		
		return "addList";
	}
	
	@RequestMapping(value="/add", method={RequestMethod.GET,RequestMethod.POST})
	public String add(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("add");
		
		guestbookDao.guestInsert(guestbookVo);
		
		
		return "redirect:/guest/list";
	}
	
	@RequestMapping(value="/deleteForm", method={RequestMethod.GET, RequestMethod.POST})
	public String deleteForm() {
		System.out.println("deleteForm");
		
	
		return "deleteForm";
	}
	
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("no") int no, @RequestParam("password") String pass) {
		System.out.println("delete");
		
		//deleteForm에서 넘어온 파라미터 값 no와 password가 모두 일치할 경우 게시글 삭제(쿼리문으로 판별)
		guestbookDao.delete(no, pass);
		
		
		return "redirect:/guest/list";
	}
}
