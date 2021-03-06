/**
 * 
 */
package com.org.engine.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.org.engine.IProduct;
import com.org.util.PricingStrategy;

/**
 * @author manishnasarpuri
 *
 */
public class Product implements IProduct{

	private String name;
	private List<Double> surveyPricingList = new ArrayList<Double>();
	private double price = 0;
	private PricingStrategy pricingStrategy;
	private double sumOfAmount=0;
	private int totalCount=0;


	public Product(String name,List<Double> surveyPricingList, PricingStrategy pricingStrategy){
		this.name = name;
		this.surveyPricingList = surveyPricingList;
		this.pricingStrategy = pricingStrategy;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surveyPricingList
	 */
	public List<Double> getSurveyPricingList() {
		return surveyPricingList;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the pricingStrategy
	 */
	public PricingStrategy getPricingStrategy() {
		return pricingStrategy;
	}

	@Override
	public double calculate() {
		Map<Double, Integer> calculateByStrategy = calculateByStrategy(getPricingMap());
		Set<Double> keySet = calculateByStrategy.keySet();
		double average = getAverage();
		for (Double price : keySet) {
			if (checkForAveragePrice(average, price)) {
				this.price = price;
				break;
			}
		}
		return price;
	}
	
	/**
	 * Method will prepare the Map depending on the user input of the product and its survey pricing.
	 * Map will have key as the pricing amount and value as the frequency of the pricing amount
	 * @return map
	 * */
	private Map<Double, Integer> getPricingMap() {
		Map<Double, Integer> map = new TreeMap<Double, Integer>();
		for (Double priceValue : getSurveyPricingList()) {
			int count = 0;
			sumOfAmount+=priceValue;
			totalCount+=1;
			if (map.containsKey(priceValue)) {
				count = map.get(priceValue);
				map.put(priceValue, count+1);
			}else{
				map.put(priceValue, count+1);
			}
		}
		return map;
	}
	
	/**
	 * Method takes the input as Map which key is the pricing amount and value is the frequency of the pricing amount.
	 * First we perform the sorting by value of Map in descending order because we need to get the mostly occurring frequency price.  
	 * Second Depending on the Pricing Strategy we perform the sorting by key in the map
	 * If the pricing strategy is Least, then perform the sorting by key in ascending order
	 * If the pricing strategy is Highest, then perform the sorting by key in descending order
	 * 
	 * @param unsortMap
	 * @return sortedMap
	 * */
	private Map<Double, Integer> calculateByStrategy(Map<Double, Integer> unsortMap) {
		// Convert Map to List
		List<Map.Entry<Double, Integer>> list = 
				new LinkedList<Map.Entry<Double, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Double, Integer>>() {
			public int compare(Map.Entry<Double, Integer> o1,
					Map.Entry<Double, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		Map<Double, Integer> sortedMap = null;
		// Convert sorted map back to a Map Depending on the Pricing Strategy
		if (pricingStrategy.equals(PricingStrategy.LEAST)) {
			sortedMap = new LinkedHashMap<Double, Integer>();
			for (Iterator<Map.Entry<Double, Integer>> it = list.iterator(); it.hasNext();) {
				Map.Entry<Double, Integer> entry = it.next();
				sortedMap.put(entry.getKey(), entry.getValue());
			}
		}else{
			sortedMap = new TreeMap<Double, Integer>(Collections.reverseOrder());
			for (Iterator<Map.Entry<Double, Integer>> it = list.iterator(); it.hasNext();) {
				Map.Entry<Double, Integer> entry = it.next();
				sortedMap.put(entry.getKey(), entry.getValue());
			}
		}
		return sortedMap;

	}
	
	public double getAverage(){
		if (totalCount != 0 && sumOfAmount !=0) {
			return sumOfAmount/totalCount;
		}
		return 0;
	}
	
	/**
	 * 
	 * Checking for the Average Conditions.
	 * Prices less than 50% of average price are treated as promotion and not considered.
	 * Prices more than 50% of average price are treated as data errors and not considered
	 * @param productAvgCost
	 * @param choosenPrice
	 * @return boolean
	 * */
	public boolean checkForAveragePrice(double productAvgCost, Double choosenPrice) {
		double startingPoint = productAvgCost * 0.5;
		double endingPoint = productAvgCost * 1.5;
		if (choosenPrice > startingPoint && choosenPrice < endingPoint) {
			return true;
		}
		return false;
	}
}
