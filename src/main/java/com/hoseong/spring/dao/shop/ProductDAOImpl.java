package com.hoseong.spring.dao.shop;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hoseong.spring.vo.shop.ProductVO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Inject
	SqlSession sqlSession;
	
	// 01. 상품목록
	@Override
	public List<ProductVO> listProduct() {
		return sqlSession.selectList("productMapper.listProduct");
	}

	// 02. 상품상세
	@Override
	public ProductVO detailProsuct(int productId) {
		return sqlSession.selectOne("productMapper.detailProduct", productId);
	}

	// 03. 상품수정
	@Override
	public void updateProduct(ProductVO vo) {
		sqlSession.update("productMapper.updateProduct", vo);
	}

	// 04. 상품삭제
	@Override
	public void deleteProduct(int productId) {
		sqlSession.delete("productMapper.deleteProduct", productId);
	}

}
