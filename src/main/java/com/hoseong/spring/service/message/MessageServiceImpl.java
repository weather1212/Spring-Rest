package com.hoseong.spring.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoseong.spring.dao.message.MessageDAO;
import com.hoseong.spring.dao.message.PointDAO;
import com.hoseong.spring.vo.message.MessageVO;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDAO messageDAO;

	@Autowired
	PointDAO pointDAO;

	// 메시지 작성(DB저장, 포인트 적립)
	@Transactional	//트랜잭션처리 대상 메서드
	@Override
	public void addMessage(MessageVO vo) {
		// 공통업무 - 로그확인
		
		// 핵심업무 - 메시지 저장, 회원 포인트 적립
		// 메시지를 테이블에 저장
		messageDAO.create(vo);
		// 메시지를 발송한 사용자에게 10포인트 적립
		pointDAO.updatePoint(vo.getSender(), 10);
	}
	
	// 메시지 열람
	@Override
	public MessageVO readMessage(String userid, int mid) {
		// TODO Auto-generated method stub
		return null;
	}

}
