package kr.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

// mvc2
@RequestMapping("/member")
@Controller
public class MemberController {
	@Autowired
	MemberMapper memberMapper;

	@ModelAttribute("cp")
	public String getContextPath(Model model, HttpServletRequest request) {
		model.addAttribute("cp", request.getContextPath());
		return request.getContextPath();
	}

	@GetMapping("/memJoin.do")
	public String memJoin() {
		return "member/join"; // join.jsp
	}

	@RequestMapping("/memRegisterCheck.do")
	public @ResponseBody int memRegisterCheck(@RequestParam("memID") String memID) {
		Member m = memberMapper.registerCheck(memID);
		if (m != null || memID.equals("")) {
			return 0; // �씠誘� 議댁옱�븯�뒗 �쉶�썝, �엯�젰遺덇�
		}
		return 1; // �궗�슜媛��뒫�븳 �븘�씠�뵒
	}

	// �쉶�썝媛��엯 泥섎━
	@RequestMapping("/memRegister.do")
	public String memRegister(Member m, String memPassword1, String memPassword2, RedirectAttributes rttr,
			HttpSession session) {
		System.out.println("==== memRegister.do ====");
		if (m.nullValueCheck()) {
			// �늻�씫硫붿꽭吏�瑜� 媛�吏�怨� 媛�湲�? =>媛앹껜諛붿씤�뵫(Model, HttpServletRequest, HttpSession)
			rttr.addFlashAttribute("msgType", "�떎�뙣 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "紐⑤뱺 �궡�슜�쓣 �엯�젰�븯�꽭�슂.");
			return "redirect:/member/memJoin.do"; // ${msgType} , ${msg}
		}
		if (!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "�떎�뙣 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "鍮꾨�踰덊샇媛� �꽌濡� �떎由낅땲�떎.");
			return "redirect:/member/memJoin.do"; // ${msgType} , ${msg}
		}
		m.setMemProfile(""); // �궗吏꾩씠誘몃뒗 �뾾�떎�뒗 �쓽誘� ""
		// �쉶�썝�쓣 �뀒�씠釉붿뿉 ���옣�븯湲�
		int result = memberMapper.register(m);
		if (result == 1) { // �쉶�썝媛��엯 �꽦怨� 硫붿꽭吏�
			rttr.addFlashAttribute("msgType", "�꽦怨� 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "�쉶�썝媛��엯�뿉 �꽦怨듯뻽�뒿�땲�떎.");
			// �쉶�썝媛��엯�씠 �꽦怨듯븯硫�=>濡쒓렇�씤泥섎━�븯湲�
			session.setAttribute("mvo", m); // ${!empty mvo}
			return "redirect:/";
		} else {
			rttr.addFlashAttribute("msgType", "�떎�뙣 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "�씠誘� 議댁옱�븯�뒗 �쉶�썝�엯�땲�떎.");
			return "redirect:/member/memJoin.do";
		}
	}

	// 濡쒓렇�븘�썐 泥섎━
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// 濡쒓렇�씤 �솕硫댁쑝濡� �씠�룞
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm"; // memLoginForm.jsp
	}

	// 濡쒓렇�씤 湲곕뒫 援ы쁽
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		if (m.getMemID() == null || m.getMemID().equals("") || m.getMemPassword() == null
				|| m.getMemPassword().equals("")) {
			rttr.addFlashAttribute("msgType", "�떎�뙣 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "紐⑤뱺 �궡�슜�쓣 �엯�젰�빐二쇱꽭�슂.");
			return "redirect:/memLoginForm.do";
		}
		Member mvo = memberMapper.memLogin(m);
		if (mvo != null) { // 濡쒓렇�씤�뿉 �꽦怨�
			rttr.addFlashAttribute("msgType", "�꽦怨� 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "濡쒓렇�씤�뿉 �꽦怨듯뻽�뒿�땲�떎.");
			session.setAttribute("mvo", mvo); // ${!empty mvo}
			return "redirect:/"; // 硫붿씤
		} else { // 濡쒓렇�씤�뿉 �떎�뙣
			rttr.addFlashAttribute("msgType", "�떎�뙣 硫붿꽭吏�");
			rttr.addFlashAttribute("msg", "�떎�떆 濡쒓렇�씤 �빐二쇱꽭�슂.");
			return "redirect:/member/memLoginForm.do";
		}
	}

	// �쉶�썝�젙蹂댁닔�젙�솕硫�
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		return "member/memUpdateForm";
	}

	// �쉶�썝�젙蹂댁닔�젙
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m, RedirectAttributes rttr, String memPassword1, String memPassword2,
			HttpSession session) {
		// �떎�뒿
		return "redirect:/";

	}
}
