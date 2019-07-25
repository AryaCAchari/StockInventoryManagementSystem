package application.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utilities {

	/**
	 * @return
	 * current date and time
	 */
	public static String currentDateAndTime() {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
//		Long timeMillies = System.currentTimeMillis();
//		"yyyy:MM:dd:hh:mm:ss a"
		String currentTime = dateFormat.format(date);
		System.out.println(currentTime);
		/*
		 * java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		 * System.out.println(sqlDate);
		 */
		return currentTime;
	}
	
	
	/**
	 * @return
	 * generate bill number with 6 digit length
	 */
	public static String generateBillNumber() {
		
		char[] value = new char[6];
		String billNumber = null;
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numeric = "0123456789";
		String billCombination = upperCase+numeric;
		
		Random random = new Random();
		
		for(int i = 0; i < 6; i++) {
			value[i] = billCombination.charAt(random.nextInt(billCombination.length()));
			billNumber = new String(value);
		}
		 
		return billNumber;
	}

	/*public static void main(String[] args) {
		System.out.println(dynamicClock());
	}*/
}
