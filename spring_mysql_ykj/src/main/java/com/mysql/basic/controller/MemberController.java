package com.mysql.basic.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.basic.entity.Member;
import com.mysql.basic.repository.MemberDAO;

@Controller
public class MemberController {
	@Autowired
	MemberDAO memberDAO;

	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	@RequestMapping(value = "/member/userMenu", method = RequestMethod.GET)
	public String userMenu() {
		return "/member/userMenu";
	}

	@GetMapping("/member/list")
	public String list(Model model) {
		ArrayList<Member> memberList = memberDAO.getMemberList();
		model.addAttribute("memberList", memberList);
		return "/member/list";
	}

	@GetMapping("/member/joinForm")
	public String joinForm() {
		return "/member/joinForm";
	}

	@PostMapping("/member/joinPro")
	public String joinPro(Member member) {
		System.out.println("Member = " + member);
		memberDAO.memberJoin(member);
		return "redirect:/member/list";
	}

	@GetMapping("/member/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}

	@PostMapping("/member/loginPro")
	public String loginPro(Member member, Model model, HttpSession session) {
		int check = memberDAO.checkMember(member);
		if (check == 1) {
			session.setAttribute("log", member.getId());
		}
		model.addAttribute("check", check);
//		model.addAttribute("id", member.getId());
		return "/member/loginPro";
	}

	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/member/index";
	}

	@GetMapping("/member/modifyForm")
	public String modifyform(Model model, HttpSession session) {
//		if (session.getAttribute("log") == null) {
//			return "/";
//		}
		if (session.getAttribute("log") != null) {
			Member member = memberDAO.getOneMember((String) session.getAttribute("log"));
			model.addAttribute("member", member);
		}
		return "/member/modifyForm";
	}

	@PostMapping("/member/modifyPro")
	public String modifyPro(Member member, HttpSession session) {
		if (session.getAttribute("log") == null) {
			return "/member/index";
		}
		System.out.println("modify Member = " + member);
		member.setId((String) session.getAttribute("log"));
		int check = memberDAO.updateMember(member);
		if (check == 0) {
			System.out.println("������Ʈ ����");
		} else {
			System.out.println("������Ʈ ����");
		}
		return "redirect:/member/list";
	}

	@GetMapping("/member/delete")
	public String delete(HttpSession session) {
		String id = (String) session.getAttribute("log");
		int check = memberDAO.deleteMember(id);
		if (check == 0) {
			System.out.println("���� ����");
		} else {
			System.out.println("���� ����");
			session.invalidate();
		}
		return "/member/index";
	}
}
