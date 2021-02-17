package com.hoseong.spring.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.shop.ProductService;

@Controller
@RequestMapping("shop/product/*")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	// 1. 상품 전체 목록
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("/shop/productList");
		mav.addObject("list", productService.listProduct());
		
		return mav;
	}
	
	// 2. 상품 상세보기
	@RequestMapping("/detail/{productId}")
	public ModelAndView detail(@PathVariable("productId") int productId, ModelAndView mav) {
		mav.setViewName("/shop/productDetail");
		mav.addObject("vo", productService.detailProsuct(productId));
		
		return mav;
	}

}
