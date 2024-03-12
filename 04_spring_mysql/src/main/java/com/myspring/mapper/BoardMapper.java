package com.myspring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.myspring.entity.Board;

@Mapper // - Mybatis API
public interface BoardMapper {
	public List<Board> getLists();

	@Insert("insert into myboard(title, content, writer values(#{title}, #{content}, #{writer}")
	public void boardInsert(Board vo);

	public Board boardContent(int idx);

	@Delete("delete from myboard where idx=#{idx}")
	public void boardDelete(int idx);

	@Update("update myboard set count=count+1 where idx=#{idx}")
	public void boardUpdate(Board vo);

	public void boardCount(int idx);
}
