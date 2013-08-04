package twingo.java;

import java.util.List;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String choice = "";
		while (!choice.equals("-1")) {
			System.out
					.println("Type 1 to search twitter and insert result in local db");
			System.out.println("Type 2 to get statistics from local db");
			System.out.println("Type -1 to exit");
			choice = in.nextLine();
			if (choice.equals("1") || choice.equals("2")|| choice.equals("-1")) {

				int ch = Integer.parseInt(choice);
				switch (ch) {
				case 1:
					System.out.println("Enter search query");
					String searchQ = in.nextLine();
					List<String> result = twingo.java.Twiter
							.get100Tweets(searchQ);
					twingo.java.TwitToMongo.saveInDb(result);
					break;
				case 2:
					System.out.println("Enter 1 to get tweets activity");
					System.out
							.println("Enter 2 to get top five followed tweets among top twenty most reweeted.");
					System.out.println("Enter 3 to get five most befriended.");
					choice = in.nextLine();
					if (choice.equals("1") || choice.equals("2")
							|| choice.equals("3")) {
						ch = Integer.parseInt(choice);
						switch (ch) {
						case 1:
							twingo.java.MongoStat.isTopTweetActive();
							break;
						case 2:
							twingo.java.MongoStat
									.top5FollowedAmng20MostRetweeted();
							break;
						case 3:
							twingo.java.MongoStat.topFiveMostBefriended();
							break;
						}
					}
					break;
				}
			} else
				System.out.println("Choice not valid");
		}
	}
}
