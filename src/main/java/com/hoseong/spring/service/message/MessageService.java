package com.hoseong.spring.service.message;

import com.hoseong.spring.vo.message.MessageVO;

public interface MessageService {
	// 메시지 작성(DB저장, 포인트적립)
	public void addMessage(MessageVO vo);

	// 메시지 열람
	public MessageVO readMessage(String userid, int mid);
}
