package com.hoseong.spring.controller.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hoseong.spring.service.message.MessageService;
import com.hoseong.spring.vo.message.MessageVO;

@RestController
@RequestMapping("/messages/*")
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	// ResponseEntity	: HTTP상태코드 + 데이터전달
	// @RequestBody		: 클라이언트 => 서버 (json 데이터가 입력될 때)
	// @ResponseBody	: 서버 => 클라이언트 (json) RestController에서는 생략가능
//	@PostMapping("/")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageVO vo) {
		ResponseEntity<String> entity = null;
		try {
			messageService.addMessage(vo);
			// new ResponseEntity<자료형>(리턴값, HTTP상태코드);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
