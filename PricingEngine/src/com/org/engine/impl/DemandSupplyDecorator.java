package com.org.engine.impl;

import com.org.engine.IProduct;
import com.org.util.Demand;

public class DemandSupplyDecorator extends ProductDecorator{
	
	Demand supply;
	Demand demand;
	
	public DemandSupplyDecorator(IProduct product) {
		super(product);
	}
	
	public DemandSupplyDecorator(IProduct product,Demand supply,Demand demand){
		this(product);
		this.demand = demand;
		this.supply = supply;
	}
	
	@Override
	public double calculate() {
		return calculatePrice(product.calculate());
	}
	

	/**
	 * If Supply is High and Demand is High, Product is sold at same price as chosen price.
	 * If Supply is High and Demand is High, Product is sold at same price as chosen price.
	 * If Supply is Low and Demand is High, Product is sold at 5 % more than chosen price.
	 * If Supply is High and Demand is Low, Product is sold at 5 % less than chosen price.
	 * 
	 * @param product	
	 * @param price
	 * */
	private double calculatePrice(double price) {
		double recommedPrice = 0.0;
		if (supply.equals(Demand.H) && demand.equals(Demand.H)) {
			recommedPrice =  price;
		}else if (supply.equals(Demand.L) && demand.equals(Demand.L)) {
			recommedPrice = price*1.1;
		}else if (supply.equals(Demand.L) && demand.equals(Demand.H)) {
			recommedPrice = price*1.05;
		}else if (supply.equals(Demand.H) && demand.equals(Demand.L)) {
			recommedPrice = price*0.95;
		}
		return recommedPrice;
	}
}
