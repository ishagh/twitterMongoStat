package twingo.java;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class Twiter {
	private final static String CONSUMER_KEY = "2x6hy5bJHo5XcImAitDv4g";
	private final static String CONSUMER_SECRET = "aG7k8lwbVQ1YGNyVz9CUL8ByNKk95BwAawWszledY";
	private final static String ACCESS_TOKEN = "1630323176-eWhtJduFdCdfkDnOul7mTJnKkt2U4P4VEVqDqiN";
	private final static String ACCESS_SECRET = "LzaTrpKbCMOD2QJ4LQ68rZJVJlZeSpEcYR34C6QCHE";

	public static List<String> get100Tweets(String q) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(CONSUMER_KEY); 
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(ACCESS_SECRET);
		cb.setDebugEnabled(true);
		cb.setJSONStoreEnabled(true);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(q);
		query.count(100);
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> r = new ArrayList<String>();
		for (Status status : result.getTweets()) {
			r.add(DataObjectFactory.getRawJSON(status));
		}

		return r;

	}

}
