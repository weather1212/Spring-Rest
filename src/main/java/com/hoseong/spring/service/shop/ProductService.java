package com.hoseong.spring.service.shop;

import java.util.List;

import com.hoseong.spring.vo.shop.ProductVO;

public interface ProductService {

	// 01. 상품목록
	public List<ProductVO> listProduct();

	// 02. 상품상세
	public ProductVO detailProsuct(int productId);

	// 03. 상품수정
	public void updateProduct(ProductVO vo);

	// 04. 상품삭제
	public void deleteProduct(int productId);

}
