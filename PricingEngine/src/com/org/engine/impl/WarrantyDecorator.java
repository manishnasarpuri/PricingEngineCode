package com.org.engine.impl;

import com.org.engine.IProduct;
import com.org.util.Choice;

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
