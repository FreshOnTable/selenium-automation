package DB;

	import com.mongodb.ConnectionString;
	import com.mongodb.MongoClientSettings;
	import com.mongodb.client.MongoClient;
	import com.mongodb.client.MongoClients;
	import com.mongodb.client.MongoDatabase;

	import java.util.concurrent.TimeUnit;

	public class MongoDBConnection {

	    private static MongoClient mongoClient;
	    private static MongoDatabase database;

	    /*
	     * IMPORTANT:
	     * Do not hard-code your real password in source code.
	     *
	     * Set these as environment variables:
	     *
	     * MONGO_URI
	     * MONGO_DATABASE
	     */

	    private static final String MONGO_URI =
	            System.getenv("MONGO_URI");

	    private static final String DATABASE_NAME =
	            System.getenv("MONGO_DATABASE");

	    public static MongoDatabase getDatabase() {

	        if (mongoClient == null) {

	            if (MONGO_URI == null || MONGO_URI.isBlank()) {
	                throw new RuntimeException(
	                        "MONGO_URI environment variable is not set"
	                );
	            }

	            if (DATABASE_NAME == null || DATABASE_NAME.isBlank()) {
	                throw new RuntimeException(
	                        "MONGO_DATABASE environment variable is not set"
	                );
	            }

	            ConnectionString connectionString =
	                    new ConnectionString(MONGO_URI);

	            MongoClientSettings settings =
	                    MongoClientSettings.builder()
	                            .applyConnectionString(connectionString)
	                            .applyToSocketSettings(builder ->
	                                    builder
	                                            .connectTimeout(10, TimeUnit.SECONDS)
	                                            .readTimeout(30, TimeUnit.SECONDS)
	                            )
	                            .build();

	            mongoClient =
	                    MongoClients.create(settings);

	            database =
	                    mongoClient.getDatabase(DATABASE_NAME);

	            System.out.println(
	                    "✅ MongoDB connection established"
	            );
	        }

	        return database;
	    }

	    public static void closeConnection() {

	        if (mongoClient != null) {

	            mongoClient.close();

	            mongoClient = null;
	            database = null;

	            System.out.println(
	                    "🔌 MongoDB connection closed"
	            );
	        }
	    }
	}
	


