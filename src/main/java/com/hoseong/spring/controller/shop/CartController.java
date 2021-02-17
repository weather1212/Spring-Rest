package com.hoseong.spring.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.shop.CartService;
import com.hoseong.spring.vo.shop.CartVO;

@Controller
@RequestMapping("shop/cart/*")
public class CartController {

	@Autowired
	CartService cartService;

	// 1. 장바구니 추가
	@RequestMapping("insert")
	public String insert(@ModelAttribute CartVO vo, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		vo.setUserId(userId);
		
		// 장바구니에 기존 상품이 있는지 검사
		int count = cartService.countCart(vo.getProductId(), userId);
		if (count == 0) { // 없으면 insert
			cartService.insert(vo);
		} else { // 있으면 update
			cartService.updateCart(vo);
		}

		return "redirect:/shop/cart/list";
	}

	// 2. 장바구니 목록
	@RequestMapping("list")
	public ModelAndView list(HttpSession session, ModelAndView mav) {

		String userId = (String) session.getAttribute("userId"); // session에 저장된 userId
		Map<String, Object> map = new HashMap<String, Object>();

		List<CartVO> list = cartService.listCart(userId); // 장바구니 정보
		int sumMoney = cartService.sumMoney(userId); // 장바구니 전체 금액 호출

		// 장바구니 전체 금액에 따라 배송비 구분
		// 배송료(10만원 이상 => 무료, 미만 => 2500원)
		int fee = sumMoney >= 100000 ? 0 : 2500;
		map.put("list", list); // 장바구니 정보를 map에 저장
		map.put("count", list.size()); // 장바구니 상품의 유무
		map.put("sumMoney", sumMoney); // 장바구니 전체 금액
		map.put("fee", fee); // 배송비
		map.put("allSum", sumMoney + fee); // 주문 상품 전체금액

		mav.setViewName("shop/cartList");
		mav.addObject("map", map);

		return mav;
	}

	// 3. 장바구니 삭제
	@RequestMapping("delete")
	public String delete(@RequestParam int cartId) {
		cartService.delete(cartId);

		return "redirect:/shop/cart/list";
	}

	// 4. 장바구니 수정
	@RequestMapping("update")
	public String update(@RequestParam int[] amount, @RequestParam int[] productId, HttpSession session) {
		String userId = (String) session.getAttribute("userId");

		// 레코드의 개수 만큼 반복문 실행
		for (int i = 0; i < productId.length; i++) {
			CartVO vo = new CartVO();
			vo.setUserId(userId);
			vo.setAmount(amount[i]);
			vo.setProductId(productId[i]);
			cartService.modifyCart(vo);
		}
		return "redirect:/shop/cart/list";
	}

}
