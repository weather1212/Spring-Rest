package com.hoseong.spring.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.util.UploadFileUtils;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	// pom.xml annotation-api dependency 추가
	// xml에 설정된 리소스 참조
	// bean의 id가 uploadPath인 태그를 참조
	@Resource(name = "uploadPath")
	String uploadPath;

	// 업로드 흐름 : 업로드 버튼 클릭 => 임시 디렉토리에 업로드 => 지정된 디렉토리에 저장 => 파일 정보가 file에 저장
	
	/****************************** # 일반적인 방식의 업로드 처리  *********************************/
	
	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		// upload/uploadForm.jsp (업로드 페이지)로 포워딩
	}

	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.POST)
	public ModelAndView updateForm(MultipartFile file, ModelAndView mav) throws Exception {

		logger.info("파일이름 : " + file.getOriginalFilename());
		logger.info("파일크기 : " + file.getSize());
		logger.info("컨텐트 타입: " + file.getContentType());

		String saveName = file.getOriginalFilename(); // 파일의 원본 이름

		saveName = uploadFile(saveName, file.getBytes());	// uuid_원본이름 저장

		File target = new File(uploadPath, saveName);

		// 임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
		// FileCopyUtils.copy(바이트배열, 파일객체)
		FileCopyUtils.copy(file.getBytes(), target);

		mav.setViewName("upload/uploadResult");
		mav.addObject("saveName", saveName);

		return mav; // uploadResult.jsp(결과화면)로 포워딩
	}

	// 파일명 랜덤생성 메서드
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		// uuid 생성 (Universal Unique Identifier, 범용 고유 식별자)
		UUID uuid = UUID.randomUUID();

		// 랜덤 생성 + 파일 이름 저장
		String saveName = uuid.toString() + "_" + originalName;

		return saveName;
	}
	
	
	/****************************** # 일반적인 방식의 업로드 처리  *********************************/
	
	/****************************** # ajax 방식의 업로드 처리  *********************************/
	
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		// uploadAjax.jsp로 포워딩
	}
	
	// produces = "text/plain; charset=utf-8" : 파일 한글 처리
	@ResponseBody
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("파일이름 : " + file.getOriginalFilename());
		logger.info("파일크기 : " + file.getSize());
		logger.info("컨텐트 타입: " + file.getContentType());
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
	}

}
