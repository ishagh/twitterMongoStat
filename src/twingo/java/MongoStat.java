package twingo.java;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoStat {

	/**************************************************************************
	 * Find tweets that are not retweets. Find if they are active. Here average
	 *  of one retweet per minit make it active.
	 *************************************************************************/

	public static void isTopTweetActive() {

		DateFormat gmtFormat = new SimpleDateFormat();
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		gmtFormat.setTimeZone(gmtTime);
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss Z yyyy");
		Date now = new Date();
		try {
			now = gmtFormat.parse(gmtFormat.format(now));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongo.getDB("twitter");
		DBCollection collection = db.getCollection("tweets");

		BasicDBObject searchQuery = new BasicDBObject(
				"in_reply_to_status_id_str", null);
		DBCursor cursor = collection.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println("///////////////////////////////////////////");

			try {

				DBObject frstTweet = cursor.next();
				String tt = (String) frstTweet.get("text");
				int retweeted = (Integer) frstTweet.get("retweet_count");
				Date tAt = df.parse((String) frstTweet.get("created_at"));
				tAt = gmtFormat.parse(gmtFormat.format(tAt));
				int minits = (int) ((now.getTime() - tAt.getTime()) / (60 * 1000));
				int tweetsPerMin = retweeted / minits;
				System.out.println("#" + tt);
				System.out
						.println("#Retweeted: " + tweetsPerMin + " per minit");
				if (tweetsPerMin < 1)
					System.out.println("Not active");
				else if (tweetsPerMin < 3)
					System.out.println("Active");
				else
					System.out.println("Very active");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/********************************************************************
	 * Find top five followed tweets among top twenty most reweeted.
	 ********************************************************************/
	public static void top5FollowedAmng20MostRetweeted() {
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongo.getDB("twitter");
		DBCollection collection = db.getCollection("tweets");
		DBCursor cursor = collection.find()
				.sort(new BasicDBObject("retweet_count", -1)).limit(20);
		int i = 0;
		DBObject[] dbo = new DBObject[20];
		while (cursor.hasNext()) {
			DBObject tweet = cursor.next();
			dbo[i] = tweet;
			i++;

		}
		dbo = sort(dbo);
		for (int i1 = 0; i1 < 5; i1++) {
			System.out.println("@" + dbo[i1].get("text"));
			System.out.println("@" + dbo[i1].get("retweet_count"));
			System.out.println("@"
					+ ((DBObject) dbo[i1].get("user")).get("followers_count"));
		}
	}
	
	public static DBObject[] sort(DBObject[] scr) {
		DBObject tmpc;

		for (int i = 0; i < scr.length - 1; i++) {

			for (int j = 1; j < scr.length - i; j++) {
				if ((Integer) ((DBObject) scr[j - 1].get("user"))
						.get("followers_count") < (Integer) ((DBObject) scr[j]
						.get("user")).get("followers_count")) {
					tmpc = scr[j - 1];
					scr[j - 1] = scr[j];
					scr[j] = tmpc;
				}
			}
		}
		return scr;
	}
	/**************************************************************************
	 * Find five most befriended
	 **************************************************************************/
	public static void  topFiveMostBefriended(){
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongo.getDB("twitter");
		DBCollection collection = db.getCollection("tweets");
		DBCursor cursor = collection.find()
				.sort(new BasicDBObject("user.friends_count", -1)).limit(5);
		while (cursor.hasNext()) {
			DBObject tweet = cursor.next();
			System.out.println("@"
					+ ((DBObject) tweet.get("user")).get("friends_count"));
		}
	}

}
