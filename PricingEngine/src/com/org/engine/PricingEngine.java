package com.org.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.org.engine.impl.DemandSupplyDecorator;
import com.org.engine.impl.Product;
import com.org.engine.impl.WarrantyDecorator;
import com.org.util.Choice;
import com.org.util.Demand;
import com.org.util.PricingStrategy;


/**
 * @author manishnasarpuri
 *
 */
public class PricingEngine {

	public static void main(String[] args) {
		PricingEngine engine = new PricingEngine();
		engine.getUserInput();
	}
	
	/**
	 * The method will ask for the user input.
	 * */
	public void getUserInput() {
		int productCount=0;
		String input = new String();
		PricingStrategy pricingStrategy = null;
		int surveyCount=0;
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Number Of Products");
			input = bufferRead.readLine();
			Map<Object, Object[]> map = new HashMap<Object, Object[]>();
			System.out.println("Please Enter Product Name with its Supply and Demand parameters e.g. flashdrive H H or flashdrive L H where H stands for High and L stands for Low");
			System.out.println("OR");
			System.out.println("Please Enter Product Name with its Supply and Demand parameters and Warranty " +
					" e.g. flashdrive H H Y or flashdrive L H N where H stands for High, L stands for Low " + 
					"Y stands for Yes and N stands for N");
			productCount = Integer.parseInt(input);
			for (int i = 0; i < productCount; i++) {
				String productInput = checkForProductInput(bufferRead);
				fillObject(productInput,map);
			}
			System.out.println("Number of Surveys");
			input = bufferRead.readLine();
			surveyCount = Integer.parseInt(input);
			System.out.println("Please Enter Competitor Details for the product e.g. mp3player X 60.0 where mp3player is product name and X is company name and 60.0 is the Competitor Price");
			for (int i = 0; i < surveyCount; i++) {
				String surveyPricingInput = checkSurveyPricing(bufferRead);
				fillPricingObject(surveyPricingInput,map);
			}
			System.out.println("Enter Pricing Strategy Least/Highest");
			input = bufferRead.readLine();
			pricingStrategy = checkPricingStrategy(input);
			Set<Object> keySet = map.keySet();
			for (Object object : keySet) {
				Object[] objects = map.get(object);
				if (objects[3] != null) {
					IProduct product = null;
					if (objects[2] != null) {
						product = new WarrantyDecorator(new DemandSupplyDecorator(new 
								Product(object.toString(), (List<Double>)objects[3], pricingStrategy),
								(Demand)objects[0], (Demand)objects[1]),(Choice)objects[2]);
					}else{
						product = new DemandSupplyDecorator(new 
								Product(object.toString(), (List<Double>)objects[3], pricingStrategy),
								(Demand)objects[0], (Demand)objects[1]);
					}
					System.out.println("Recommended price for product "+object+" is "+String.format("%.1f",product.calculate()));
				}else{
					System.out.println("SurveyPrice for product "+object+" is not entered..");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Please check the input and retry again");
			System.exit(0);
		}
	}
	
	/**
	 * Method will check for the Competitor Survey Pricing with the format flashdrive X 12.0
	 * @param surveyPricingInput
	 * @param map
	 * */
	private void fillPricingObject(String surveyPricingInput,
			Map<Object, Object[]> map) throws Exception {
		try {
			String[] split = surveyPricingInput.split(" ");
			if (split.length == 3) {
				if (map.containsKey(split[0])) {
					Object[] objects = map.get(split[0]);
					if (objects[3] != null) {
						List<Double> list = (List<Double>) objects[3];
						list.add(Double.parseDouble(split[2]));
					} else {
						List<Double> list = new ArrayList<Double>();
						list.add(Double.parseDouble(split[2]));
						objects[3] = list;
					}
				}
			} else {
				System.out.println("Please Enter the proper value of the Competitor Details ");
				System.exit(0);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

	}
	
	/**
	 * Method will check for the Pricing Strategy depending of user input , if not matched will consider the Least pricing strategy by default  
	 * @param input
	 * @return PricingStrategy
	 * */
	private PricingStrategy checkPricingStrategy(String input) throws Exception {
		try {
			if (input != null && input.equalsIgnoreCase(PricingStrategy.HIGHEST.toString())) {
				return PricingStrategy.HIGHEST;
			}else if (input != null && input.equalsIgnoreCase(PricingStrategy.LEAST.toString())) {
				return PricingStrategy.LEAST;
			}else{
				System.out.println("Least Pricing Strategy is choosen by default");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return PricingStrategy.LEAST;
	}

	private void fillObject(String productInput, Map<Object, Object[]> map) {
		String[] split = productInput.split(" ");
		Object[] objects = new Object[5];
		objects[0] = getDemand(split[1]);
		objects[1] = getDemand(split[2]);
		if (split.length == 4) {
			objects[2] = getDemand(split[3]);
		}
		map.put(split[0],objects);
	}
	
	
	/**
	 * Method will check for the Demand i.r High or Low  
	 * @param demand
	 * @return Demand
	 * */
	public Demand getDemand(String demand){
		if (demand.equalsIgnoreCase(Demand.H.toString())) {
			return Demand.H;
		}else if (demand.equalsIgnoreCase(Demand.L.toString())) {
			return Demand.L;
		}
		return Demand.L;
	}
	
	/**
	 * @param bufferRead
	 * @return String
	 * */
	private String checkForProductInput(BufferedReader bufferRead)
			throws Exception {
		String input = new String();
		input = bufferRead.readLine();
		return checkIfProductInputProper(bufferRead,input);
	}
	
	/**
	 * @param bufferRead
	 * @param input
	 * @return String
	 * */
	
	private String checkIfProductInputProper(BufferedReader bufferRead, String input) throws Exception {
		try {
			String[] split = input.split(" ");
			if (split.length == 3) {
				if (!(split[1]!= null && split[1].equalsIgnoreCase(Demand.H.toString()) || split[1].equalsIgnoreCase(Demand.L.toString())) 
						&& !(split[2]!= null && split[2].equalsIgnoreCase(Demand.H.toString()) || split[2].equalsIgnoreCase(Demand.L.toString()))) {
					System.out.println("Please Enter the proper value of the product "+split[0]);
					checkIfProductInputProper(bufferRead,input);
				}else{
					return input;
				}
			}else if (split.length == 4){
				if (!(split[1]!= null && split[1].equalsIgnoreCase(Demand.H.toString()) || split[1].equalsIgnoreCase(Demand.L.toString()))
						&& !(split[2]!= null && split[2].equalsIgnoreCase(Demand.H.toString()) || split[2].equalsIgnoreCase(Demand.L.toString()))
						&& !(split[3]!= null && split[3].equalsIgnoreCase(Choice.Y.toString()) || split[3].equalsIgnoreCase(Choice.N.toString()))) {
					System.out.println("Please Enter the proper value of the product "+split[0]);
					checkIfProductInputProper(bufferRead,input);
				}
			}else{
				System.out.println("Please enter the product details properly and Try Again");
				System.exit(0);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return input;
	}
	
	/**
	 * @param bufferRead
	 * @return String
	 * */
	private String checkSurveyPricing(BufferedReader bufferRead) throws Exception {
		String input = new String();
		input = bufferRead.readLine();
		return checkIfCompetitorInputProper(bufferRead,input);
	}
	
	
	/**
	 * @param bufferRead
	 * @param input
	 * @return String
	 * */
	private String checkIfCompetitorInputProper(BufferedReader bufferRead,
			String input) throws Exception{
		try {
			String[] split = input.split(" ");
			if (split.length == 3) {
				if (split[2] != null) {
					Double.parseDouble(split[2]);
				}else{
					System.out.println("Please Enter the proper value of the Product Competitor Details and Try Again");
					input = checkForProductInput(bufferRead);
				}
			}else{
				System.out.println("Please Enter the proper value of the Product Competitor Details and Try Again");
				System.exit(0);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return input;
	}
}
