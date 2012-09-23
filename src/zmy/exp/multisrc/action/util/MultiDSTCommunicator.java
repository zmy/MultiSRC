package zmy.exp.multisrc.action.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MultiDSTCommunicator {
	//TODO: deploy to a real server
	static final String SERVER_LINK = "http://localhost:8080/multi/services/";

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

	public static String GetActionList() throws IOException {
		return GET(SERVER_LINK+"action/list");
	}

	public static String GetActionDetail(String id) throws IOException {
		return GET(SERVER_LINK+"action/"+id+"/detail");
	}

	public static String GetActionResourse(String id, String res) throws IOException {
		return GET(SERVER_LINK+"action/"+id+"/res/"+res);
	}
}
