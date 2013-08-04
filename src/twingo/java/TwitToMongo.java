package twingo.java;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class TwitToMongo {

	/**
	 * @param args
	 */
	public static void saveInDb(List<String> json) {
		Mongo mongo = null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB db = mongo.getDB("twitter");
		DBCollection collection = db.getCollection("tweets");
		Iterator<String> r = json.iterator();
		while (r.hasNext()) {
			String jso = r.next();
			DBObject dbObject = (DBObject) JSON.parse(jso);
			collection.insert(dbObject);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
