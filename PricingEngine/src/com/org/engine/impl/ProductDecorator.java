/**
 * 
 */
package com.org.engine.impl;

import com.org.engine.IProduct;


/**
 * @author manish_nasarpuri
 *
 */
public class ProductDecorator implements IProduct {
	

	protected IProduct product;
	public ProductDecorator(IProduct product) {
		this.product = product;
	}
	
	@Override
	public double calculate() {
		return this.product.calculate();
	}

}
