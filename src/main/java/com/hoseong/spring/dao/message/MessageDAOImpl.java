package com.hoseong.spring.dao.message;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.message.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	// 메시지 작성
	@Override
	public void create(MessageVO vo) {
		sqlSession.insert("messageMapper.create", vo);
	}

	// 메시지 열람
	@Override
	public MessageVO readMessage(int mid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 메시지 열람시간 갱신
	@Override
	public void updateMessage(int mid) {
		// TODO Auto-generated method stub

	}

}
