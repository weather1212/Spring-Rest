package com.hoseong.spring.service.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoseong.spring.dao.shop.ProductDAO;
import com.hoseong.spring.vo.shop.ProductVO;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;

	// 01. 상품목록
	@Override
	public List<ProductVO> listProduct() {
		return productDAO.listProduct();
	}

	// 02. 상품 상세
	@Override
	public ProductVO detailProsuct(int productId) {
		return productDAO.detailProsuct(productId);
	}

	// 03. 상품수정
	@Override
	public void updateProduct(ProductVO vo) {
		productDAO.updateProduct(vo);
	}

	// 04. 상품삭제
	@Override
	public void deleteProduct(int productId) {
		productDAO.deleteProduct(productId);
	}

}
