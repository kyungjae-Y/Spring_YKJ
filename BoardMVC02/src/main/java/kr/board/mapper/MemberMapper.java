package kr.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.board.entity.Member;

@Mapper // - Mybatis API
public interface MemberMapper {
	public Member memLogin(Member member); // 로그인체크

	public Member registerCheck(String memID);

	public int register(Member member); // 회원등록(성공1, 실패0)

	public int memUpdate(Member member); // 수정하기

	public Member getMember(String memID);

	public void memProfileUpdate(Member mvo);
}