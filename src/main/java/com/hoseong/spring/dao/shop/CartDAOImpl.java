package com.hoseong.spring.dao.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.shop.CartVO;

@Repository
public class CartDAOImpl implements CartDAO {

	@Autowired
	SqlSession sqlSession;

	// 1. 장바구니 추가
	@Override
	public void insert(CartVO vo) {
		sqlSession.insert("cartMapper.insertCart", vo);
	}

	// 2. 장바구니 목록
	@Override
	public List<CartVO> listCart(String userId) {
		return sqlSession.selectList("cartMapper.listCart", userId);
	}

	// 3. 장바구니 삭제
	@Override
	public void delete(int cartId) {
		sqlSession.delete("cartMapper.deleteCart", cartId);
	}

	// 4. 장바구니 수정
	@Override
	public void modifyCart(CartVO vo) {
		sqlSession.update("cartMapper.modifyCart", vo);
	}

	// 5. 장바구니 금액 합계
	@Override
	public int sumMoney(String userId) {
		return sqlSession.selectOne("cartMapper.sumMoney", userId);
	}

	// 6. 장바구니 동일한 상품 레코드 확인
	@Override
	public int countCart(int productId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("userId", userId);

		return sqlSession.selectOne("cartMapper.countCart", map);
	}

	// 7. 장바구니 상품 수량 추가
	@Override
	public void updateCart(CartVO vo) {
		sqlSession.update("cartMapper.updateCart", vo);
	}

}
