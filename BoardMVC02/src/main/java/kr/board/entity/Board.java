package kr.board.entity;

import lombok.Data;

@Data
public class Board {
	private int idx; // 踰덊샇
	private String memID; // �쉶�썝ID
	private String title; // �젣紐�
	private String content; // �궡�슜
	private String writer; // �옉�꽦�옄
	private String indate; // �옉�꽦�씪
	private int count; // 議고쉶�닔
}
