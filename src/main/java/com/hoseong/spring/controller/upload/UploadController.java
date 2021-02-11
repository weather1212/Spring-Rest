package com.hoseong.spring.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.board.BoardService;
import com.hoseong.spring.util.MediaUtils;
import com.hoseong.spring.util.UploadFileUtils;

@Controller
public class UploadController {
	
	@Inject
	BoardService boardService;

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	// pom.xml annotation-api dependency 추가
	// xml에 설정된 리소스 참조
	// bean의 id가 uploadPath인 태그를 참조
	@Resource(name = "uploadPath")
	String uploadPath;

	// 업로드 흐름 : 업로드 버튼 클릭 => 임시 디렉토리에 업로드 => 지정된 디렉토리에 저장 => 파일 정보가 file에 저장

	/******************************
	 * # 일반적인 방식의 업로드 처리
	 *********************************/

	// 일반적인 업로드 매핑(GET)
	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		// upload/uploadForm.jsp (업로드 페이지)로 포워딩
	}

	// 일반적인 업로드 매핑(POST)
	@RequestMapping(value = "/upload/uploadForm", method = RequestMethod.POST)
	public ModelAndView updateForm(MultipartFile file, ModelAndView mav) throws Exception {

		logger.info("파일이름 : " + file.getOriginalFilename());
		logger.info("파일크기 : " + file.getSize());
		logger.info("컨텐트 타입: " + file.getContentType());

		String saveName = file.getOriginalFilename(); // 파일의 원본 이름

		saveName = uploadFile(saveName, file.getBytes()); // uuid_원본이름 저장

		// 파일 경로, 파일명을 받아 파일 객체 생성
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

	/******************************
	 * # 일반적인 방식의 업로드 처리END
	 *********************************/

	/******************************
	 * # ajax 방식의 업로드 처리
	 *********************************/

	// Ajax방식 파일 업로드 페이지 매핑(GET)
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		// uploadAjax.jsp로 포워딩
	}

	// Ajax방식 파일 업로드 페이지 매핑(POST)
	// produces = "text/plain; charset=utf-8" : 파일 한글 처리
	@ResponseBody
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("파일이름 : " + file.getOriginalFilename());
		logger.info("파일크기 : " + file.getSize());
		logger.info("컨텐트 타입: " + file.getContentType());

		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
	}

	// 이미지 표시 매핑
	@ResponseBody // view를 리턴하지 않고 data를 리턴
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws IOException {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 추출한 확장자를 MediaUtils 클래스에서 이미지파일 여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			// 헤더 구성 객체 (외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			// InputStream 생성
			in = new FileInputStream(uploadPath + fileName);
			// 이미지파일 이면
			if (mType != null) {
				headers.setContentType(mType);
			} else { // 이미지파일이 아니면
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				// 다운로드용 컨텐트 타입
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				// 바이트배열을 스트링으로 : new String(fileName.getBytes("utf-8"),"iso-8859-1")
				// *iso-8859-1 서유럽언어, 큰 따옴표 내부에 "\" 내용 \" "
				// 파일의 한글 깨짐 방지
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
				// headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			// 바이트배열, 헤더, HTTP상태코드
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// HTTP상태 코드()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close(); // 스트림 닫기
		}
		return entity;
	}

	// 파일 삭제 매핑
	@ResponseBody // view를 리턴하지 않고 data를 리턴
	@RequestMapping(value = "/upload/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		// 파일의 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 이미지 파일 여부 검사
		MediaType mType = MediaUtils.getMediaType(formatName);
		// 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
		if (mType != null) { // 이미지 파일이면
			// 썸네일 이미지 파일 추출
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			// 썸네일 이미지 삭제
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
		}
		// 원본파일 삭제
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		// 레코드 삭제
		boardService.deleteFile(fileName);

		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}

}
