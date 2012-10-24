package zmy.exp.multisrc.action.store;
import java.io.*;
import java.net.*;


public class Source {
	static String GET(String link) throws IOException {
		URL url = new URL(link);
		URLConnection urlc = url.openConnection();
		urlc.setDoInput(true);
		urlc.setAllowUserInteraction(false);
		BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
		String l, str = new String("");
		while((l = br.readLine()) != null) {
			str += l+"\n";
		}
		br.close();
		return str;
	}

	//public static void main(String[] args) throws IOException {
		//System.out.println(GET("http://localhost:8080/multi/services/action/list"));
	//}
}
