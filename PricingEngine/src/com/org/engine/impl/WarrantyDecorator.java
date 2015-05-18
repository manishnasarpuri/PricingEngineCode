package com.org.engine.impl;

import com.org.engine.IProduct;
import com.org.util.Choice;

/**
 * @author manishnasarpuri
 *
 */
public class WarrantyDecorator extends ProductDecorator {
	
	Choice choice;

	public WarrantyDecorator(IProduct product) {
		super(product);
	}
	
	public WarrantyDecorator(IProduct product,Choice choice){
		this(product);
		this.choice = choice;
	}
	
	@Override
	public double calculate() {
		return calculatePrice(product.calculate());
	}
	
	/**
	 * If Warranty support needed for product, Product is sold  5 % more than chosen price.
	 * If Warranty support not needed for product, Product is sold 5 % less than chosen price.
	 * 
	 * @param chosen price
	 * @return recommendedPrice
	 * */
	private double calculatePrice(double price) {
		double recommendedPrice = 0.0;
		if (choice.equals(Choice.Y)) {
			recommendedPrice = price * 1.05;
		}else if (choice.equals(Choice.N)){
			recommendedPrice = price * 0.95;
		}
		return recommendedPrice;
	}
}
