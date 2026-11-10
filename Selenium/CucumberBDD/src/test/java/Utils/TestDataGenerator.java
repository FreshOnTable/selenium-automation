package Utils;

	import java.security.SecureRandom;

	public class TestDataGenerator {

	    private static final SecureRandom random = new SecureRandom();

	    // Generate 6 digit PIN Code
	    public static String generatePinCode() {

	        long timestamp = System.currentTimeMillis();

	        int randomPart = random.nextInt(100);

	        String pinCode = String.valueOf(timestamp % 1000000);

	        // Make sure it is 6 digits
	        pinCode = String.format("%06d", Integer.parseInt(pinCode));

	        // Replace last 2 digits with random value
	        pinCode = pinCode.substring(0, 4)
	                + String.format("%02d", randomPart);

	        return pinCode;
	    }

	    // Generate 15 digit TRN Number
	    public static String generateTRN() {

	        long timestamp = System.currentTimeMillis();

	        String timestampPart = String.valueOf(timestamp);

	        // Take last 12 digits of timestamp
	        timestampPart =
	                timestampPart.substring(
	                    Math.max(0, timestampPart.length() - 12)
	                );

	        // Add 3 random digits
	        int randomPart = random.nextInt(1000);

	        return timestampPart
	                + String.format("%03d", randomPart);
	    }
	    
	    // Business First Name
	    public static String generateBusinessFirstName() {

	        int number = random.nextInt(100000);

	        return "TestBusiness" + String.format("%05d", number);
	    }

	    // Business Last Name
	    public static String generateBusinessLastName() {

	        int number = random.nextInt(100000);

	        return "Test" + String.format("%05d", number);
	    }
	    
	    public static String generateDubaiAddressLine1() {

	        int buildingNumber = random.nextInt(900) + 100;
	        int streetNumber = random.nextInt(90) + 10;

	        return "Test Building " + buildingNumber
	                + ", Dubai Test Street " + streetNumber;
	    }

	    public static String generateDubaiAddressLine2() {

	        int officeNumber = random.nextInt(900) + 100;

	        return "Test Office " + officeNumber + ", Dubai, UAE";
	    }
	    
	    public static String generateMobileNumber() {

	        int number = random.nextInt(10000000);

	        return "05" + String.format("%07d", number);
	    }

	    public static String generateEmail() {

	        long timestamp = System.currentTimeMillis();

	        int randomNumber = random.nextInt(10000);

	        return "testuser"
	                + timestamp
	                + randomNumber
	                + "@gamail.com";
	    }
	    

public static String generateBusinessFirstName1(String userType) {
    int number = random.nextInt(100000);

    return userType + String.format("%05d", number);
}

public static String generateBusinessLastName1(String userType) {
    int number = random.nextInt(100000);

    return "Test" + userType + String.format("%05d", number);
}

	}

