package com.hoseong.spring.dao.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO {

	@Autowired
	SqlSession sqlSession;

	// 회원 포인트 갱신(메시지 발신)
	@Override
	public void updatePoint(String userid, int userpoint) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userid", userid);
		map.put("userpoint", userpoint);
		
		sqlSession.update("pointMapper.updatePoint", map);
	}

}
