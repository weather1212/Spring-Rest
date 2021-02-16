package com.hoseong.spring.controller.shop;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hoseong.spring.service.shop.ProductService;

@Controller
@RequestMapping("shop/product/*")
public class ProductController {
	
	@Inject
	ProductService productService;
	
	@Resource(name = "productImagePath")
	String productImagePath;
	
	// 1. 상품 전체 목록
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("/shop/productList");
		mav.addObject("list", productService.listProduct());
		mav.addObject("productImagePath", productImagePath);
		
		return mav;
	}
	
	// 2. 상품 상세보기
	@RequestMapping("/deatil/{productId}")
	public ModelAndView detail(@PathVariable("productId") int productId, ModelAndView mav) {
		mav.setViewName("/shop/productDetail");
		mav.addObject("vo", productService.detailProsuct(productId));
		
		return mav;
	}

}
