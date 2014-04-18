package operation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class GetTweet extends Operation {

	String query = "";

	public GetTweet() {
	}

	public GetTweet(String sib, String line) {
		this.sibPath = sib;
		String tokens[] = line.split(" ");
		folderPath = tokens[tokens.length - 1];
		for (int i = 1; i < tokens.length - 2; i++) {
			query += tokens[i] + " ";
		}
		query = query.trim();
	}

	@Override
	String compress() {
		return "GetTweet " + query + " -folder " + folderPath;
	}

	@Override
	void decompress() {

		Twitter twitter = new TwitterFactory().getInstance();
		AccessToken accessToken = new AccessToken(
				"87195042-59RZUa1wtnid3ES2AwwGqV1t7oJaDWB5KXcJB8ybr",
				"UgxymTQisf43bSpwUZHSFiAtTLff1uXVZSGo9cYkH0YAz");
		twitter.setOAuthConsumer("NNVlnvzXeUtdBIGrWwbPdt4mi",
				"eE3JKsLZLhaOssrAu7NXwxxF6yjE5oWnmW5ndIc0AbPfncnKLE");
		twitter.setOAuthAccessToken(accessToken);

		try {
			// Create a folder
			File dir = new File(sibPath + "/" + folderPath);
			dir.mkdir();

			// Create a new file in our system with path =
			// sibPath/folderPath/fileName
			PrintWriter pw;
			pw = new PrintWriter(sibPath + "/" + folderPath + "/" + "tweets-"
					+ query);

			Query twitterQuery = new Query(query);
			QueryResult result;
			result = twitter.search(twitterQuery);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) {
				pw.println("@" + tweet.getUser().getScreenName() + " - "
						+ tweet.getText());
			}
			pw.close();
		} catch (TwitterException te) {
			te.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
