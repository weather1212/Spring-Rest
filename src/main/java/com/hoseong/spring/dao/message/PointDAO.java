package com.hoseong.spring.dao.message;

public interface PointDAO {
	// 회원 포인트 갱신
	public void updatePoint(String userid, int userpoint);
}
