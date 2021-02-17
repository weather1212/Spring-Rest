package com.hoseong.spring.dao.shop;

import java.util.List;

import com.hoseong.spring.vo.shop.CartVO;

public interface CartDAO {

	// 1. 장바구니 추가
	public void insert(CartVO vo);

	// 2. 장바구니 목록
	public List<CartVO> listCart(String userId);

	// 3. 장바구니 삭제
	public void delete(int cartId);

	// 4. 장바구니 수정
	public void modifyCart(CartVO vo);

	// 5. 장바구니 금액 합계
	public int sumMoney(String userId);

	// 6. 장바구니 동일한 상품 레코드 확인
	public int countCart(int productId, String userId);

	// 7. 장바구니 상품 수량 변경
	public void updateCart(CartVO vo);
}
