package twitter;
import java.util.Collections;  
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.Twitter;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twittertest {
public static	String totalwords="TOp 50 words with highest frequency:\n";
public static String alltweets="\n All Tweets: \n";
	public static void main(String[] bb){
		 Map<String, Integer> wordsmap = new HashMap<String, Integer>();
		 
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("LdPKKQjouKtPJHQs3jNlfRi6R")
		  .setOAuthConsumerSecret("9MBVvEyNLLkPhzHGW5Cqpz11p6Gplniqo1lnuz2DZwTDh4meCD")
		  .setOAuthAccessToken("1432243269841260544-vaokXIhe9GzFiSTqLHeLQbvUyf7gZ7")
		  .setOAuthAccessTokenSecret("1larBbmuzvBI2Pq6I9SZTwub2QhgVqgNVcP7UVPYURmIO");
		 Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		 
		 System.out.println("Please Enter HashTag with '#' as a prefix");
		 Scanner sc= new Scanner(System.in);
		 String hashtag= sc.next();
		 Query query = new Query(hashtag);
		 query.count(200);
		 System.out.println("Please Enter File location with normal '/' path annotation or just copy path \n");
		 String loc=sc.next();
		 loc= loc.replace('\\', '/');
		 int numberOfTweets = 2000;
		 long lastID = Long.MAX_VALUE;
		 ArrayList<Status> tweets = new ArrayList<Status>();
		      try {
		        QueryResult result = twitter.search(query);
		        tweets.addAll(result.getTweets());
		        System.out.println("Gathered " + tweets.size() + " tweets"+"\n");
		        for (Status t: tweets) 
		          if(t.getId() < lastID) 
		              lastID = t.getId();

		      }

		      catch (TwitterException te) {
		        System.out.println("Couldn't connect: " + te);
		      }; 
		      query.setMaxId(lastID-1);
		    for (int i = 0; i < tweets.size(); i++) {
		      Status t = (Status) tweets.get(i);
              String user = t.getUser().getScreenName();
		      String msg = t.getText();
		      alltweets=alltweets+msg+"\n";
		      String[] words= msg.split(" ");
		      for(String w:  words) {
		    	 if(wordsmap.containsKey(w)) {
		    		 wordsmap.put(w, wordsmap.get(w)+1);
		    	 }else {
		    		 wordsmap.put(w, 1);
		    	 }
		      }
		      
		      
		       System.out. println(i + " USER: " + user + " wrote: " + msg + "\n");
		      }
		    MyComparator comp=new MyComparator(wordsmap);

		      Map<String,Integer> newMap = new TreeMap(comp);
		      newMap.putAll(wordsmap);
		      int i=1;
		      
		    for (String key:  newMap.keySet()){
		    	if(i>50) break;
	            System.out.println(i+". "+key +" = "+ newMap.get(key));
	            totalwords=totalwords+i+". "+key +" = "+ newMap.get(key)+"\n";
	            i++;
	        }
		  writefile(loc);
	}
	public static void writefile(String loc){
		try{
	        Writer output = null;
	        File file = new File(loc);
	        file.createNewFile();
	        output = new BufferedWriter(new FileWriter(file));

	       output.write(totalwords+alltweets+"\n\n Submitted By Nishkarsh Maitry");

	        output.close();
	        System.out.println("File has been written");

	    }catch(Exception e){
	        System.out.println("Could not create file");
	    }
	}

}
