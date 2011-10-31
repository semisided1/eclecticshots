package ca.eclecticshots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class Net {

	
public static void printimages(String surl) {
	String html = get(surl);
	int fromindex = 0;
	int endindex = 0;
	while ( true ) {
		fromindex = html.indexOf("<img src=\"", fromindex);
		if ( fromindex == -1) break;
		endindex =  html.indexOf("\"", fromindex+10);
		System.out.print(html.substring(fromindex+10, endindex) + "\n");
		fromindex = endindex;
	}
}
	
	
public static List<String> getImages(String surl) {
	String html = get(surl);
	List < String >  imgs = new ArrayList < String >  (  ) ; 
	int fromindex = 0;
	int endindex = 0;
	while ( true ) {
		fromindex = html.indexOf("<img src=\"", fromindex);
		if ( fromindex == -1) break;
		endindex =  html.indexOf("\"", fromindex+10);
		imgs.add(html.substring(fromindex+10, endindex));
		fromindex = endindex;
	}
	return imgs;
}
	
	
public static String get(String surl) {
	URL url = null;
    BufferedReader reader = null;
    String line = null;
    StringBuilder stringBuilder = null;
    HttpURLConnection connection = null;   
    try {
     url = new URL(surl);
    connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setReadTimeout(15*1000);
    connection.connect();
    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    stringBuilder = new StringBuilder();
 
      while ( true ) {
    	  line = reader.readLine();
    	  if ( line == null) break;
        stringBuilder.append(line + "\n");
      }
      reader.close();
    } catch ( Exception e ) { e.printStackTrace() ; }
    
    return stringBuilder.toString();
}

public static void main(String[] args) {
		String surl = "http://picasaweb.google.com/dirtslayer";
		String html = Net.get(surl);
		System.out.print(html);
	//	Net.printimages(surl);
		List<String> imgs =  getImages(surl);
			for (String img : imgs) System.out.println(img);
		return;
}

}
