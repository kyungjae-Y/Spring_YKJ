package com.board.mapper;

import java.util.List;

import com.board.entity.Member;

public interface MemberMapper {
	public List<Member> getUserLists();
	
	public void userInsert(Member m);
	
	public Member userInfo(int idx);
	
	public void userDelete(int idx);
	
	public void userUpdate(Member m);
}
