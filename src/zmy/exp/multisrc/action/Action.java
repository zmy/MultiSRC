package zmy.exp.multisrc.action;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.R;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

public class Action {
	// How many sentences in each paragraph?
	final int SENTENCES_PER_PARAGRAPH = 20;

	// How many paragraphs in each article?
	final int PARAGRAPHS_PER_ARTICLE = 5;

	// Headline and body
	String mHeadline, mBody;

	public class Info {
		public Map<String, String> options;
		public String title;
		public String collectorType;

		public Info() {
			options = new HashMap<String, String>();
		}
	}

	class XMLParser {
		private final String ns = null;

		// We don't use namespaces
		
		public String headline = "test";

		public List<Info> parse(InputStream in) throws XmlPullParserException, IOException {
			try {
				XmlPullParser parser = Xml.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				parser.setInput(in, null);
				parser.nextTag();
				return readAction(parser);
			} finally {
				in.close();
			}
		}

		private List<Info> readAction(XmlPullParser parser) throws XmlPullParserException, IOException {
			List<Info> entries = new ArrayList<Info>();

			parser.require(XmlPullParser.START_TAG, ns, "action");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				// Starts by looking for the entry tag
				if (name.equals("info")) {
					Log.d("Action Parse", "info");
					entries.add(readInfo(parser));
				} else if (name.equals("entry")) {
					//parser.require(XmlPullParser.START_TAG, ns, "title");
					//headline = readText(parser);
					//parser.require(XmlPullParser.END_TAG, ns, "title");
					Log.d("Action Parse", "entry");
					readEntry(parser);
				} else {
					skip(parser);
				}
			}
			return entries;
		}

		private void readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
			parser.require(XmlPullParser.START_TAG, ns, "entry");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("title")) {
					headline = readTitle(parser);
				} else {
					skip(parser);
				}
			}
		}
		
		// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
		// off
		// to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
		private Info readInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
			parser.require(XmlPullParser.START_TAG, ns, "info");
			Info info = new Info();
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("title")) {
					info.title = readTitle(parser);
				} else {
					skip(parser);
				}
			}
			return info;
		}

		// Processes title tags in the feed.
		private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
			parser.require(XmlPullParser.START_TAG, ns, "title");
			String title = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "title");
			return title;
		}

		// Processes link tags in the feed.
		private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
			String link = "";
			parser.require(XmlPullParser.START_TAG, ns, "link");
			String tag = parser.getName();
			String relType = parser.getAttributeValue(null, "rel");
			if (tag.equals("link")) {
				if (relType.equals("alternate")) {
					link = parser.getAttributeValue(null, "href");
					parser.nextTag();
				}
			}
			parser.require(XmlPullParser.END_TAG, ns, "link");
			return link;
		}

		// Processes summary tags in the feed.
		private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
			parser.require(XmlPullParser.START_TAG, ns, "summary");
			String summary = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "summary");
			return summary;
		}

		// For the tags title and summary, extracts their text values.
		private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
			String result = "";
			if (parser.next() == XmlPullParser.TEXT) {
				result = parser.getText();
				parser.nextTag();
			}
			return result;
		}

		// Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
		// if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
		// finds the matching END_TAG (as indicated by the value of "depth" being 0).
		private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				throw new IllegalStateException();
			}
			int depth = 1;
			while (depth != 0) {
				switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
				}
			}
		}
	}

	List<Info> mInfoList;

	/**
	 * Create a news article with randomly generated text.
	 * @param ngen the nonsense generator to use.
	 */
	public Action(NonsenseGenerator ngen) {
		mHeadline = ngen.makeHeadline();

		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		sb.append("<h1>" + mHeadline + "</h1>");
		int i;
		for (i = 0; i < PARAGRAPHS_PER_ARTICLE; i++) {
			sb.append("<p>").append(ngen.makeText(SENTENCES_PER_PARAGRAPH)).append("</p>");
		}

		sb.append("</body></html>");
		mBody = sb.toString();
	}
	
	public Action(Resources res) {
		InputStream in = res.openRawResource(R.raw.action_0000001);
		XMLParser parser = new XMLParser();
		try {
			mInfoList = parser.parse(in);
			Log.d("Action", "Info Size: "+mInfoList.size());
			mHeadline = parser.headline;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(R.raw.action_0000001)));
//		try {
//			Log.d("Action", reader.readLine());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/** Returns the headline. */
	public String getHeadline() {
		return mHeadline;
	}

	/** Returns the article body (HTML)*/
	//public String getBody() {
	//	return mBody;
	//}

	public List<Info> getInfoList() {
		return mInfoList;
	}
}
