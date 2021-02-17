package com.hoseong.spring.service.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.shop.CartDAO;
import com.hoseong.spring.vo.shop.CartVO;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartDAO cartDAO;

	// 1. 장바구니 추가
	@Override
	public void insert(CartVO vo) {
		cartDAO.insert(vo);
	}

	// 2. 장바구니 목록
	@Override
	public List<CartVO> listCart(String userId) {
		return cartDAO.listCart(userId);
	}

	// 3. 장바구니 삭제
	@Override
	public void delete(int cartId) {
		cartDAO.delete(cartId);
	}

	// 4. 장바구니 수정
	@Override
	public void modifyCart(CartVO vo) {
		cartDAO.modifyCart(vo);
	}

	// 5. 장바구니 금액 합계
	@Override
	public int sumMoney(String userId) {
		return cartDAO.sumMoney(userId);
	}

	// 6. 장바구니 상품 확인
	@Override
	public int countCart(int productId, String userId) {
		return cartDAO.countCart(productId, userId);
	}

	// 7. 장바구니 상품 수량 추가
	@Override
	public void updateCart(CartVO vo) {
		cartDAO.updateCart(vo);
	}

}
