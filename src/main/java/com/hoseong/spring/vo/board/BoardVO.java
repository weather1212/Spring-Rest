package com.hoseong.spring.vo.board;

import java.util.Date;

public class BoardVO {

	private int bno; // 게시글 번호
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	private String writer; // 게시글 작성자
	private Date regdate; // 게시글 작성일자 util.Date
	private int viewcnt; // 게시글 조회수

	private String userName; // 게시글 회원 이름 = 게시글 작성자
	private int recnt; // 해당 게시글의 댓글 수

	private String show; // *게시글 삭제 상태 유무(y,n)
	
	private String[] files;	// 게시글의 첨부파일 이름

	private int attachFl;	//게시글의 첨부파일 존재 여부

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRecnt() {
		return recnt;
	}

	public void setRecnt(int recnt) {
		this.recnt = recnt;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	public int getAttachFl() {
		return attachFl;
	}

	public void setAttachFl(int attachFl) {
		this.attachFl = attachFl;
	}

}
