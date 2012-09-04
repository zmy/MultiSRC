package zmy.exp.multisrc.action;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.R;
import zmy.exp.multisrc.action.collector.Collector;
import zmy.exp.multisrc.action.collector.CollectorFactory;
import zmy.exp.multisrc.action.util.XMLParser;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

public class Action {
	// How many sentences in each paragraph?
	//final int SENTENCES_PER_PARAGRAPH = 20;

	// How many paragraphs in each article?
	//final int PARAGRAPHS_PER_ARTICLE = 5;

	// Headline and body
	//String mHeadline, mBody;

	public class Entry {

		String title;
		Set<String> tags;
		//TODO: deal with other intros of an action

		void readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
			parser.require(XmlPullParser.START_TAG, XMLParser.ns, "entry");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("title")) {
					title = XMLParser.readTitle(parser);
				} else {
					//TODO: read tags etc.
					XMLParser.skip(parser);
				}
			}
		}

		Entry(XmlPullParser parser) throws XmlPullParserException, IOException {
			title = "N/A";
			tags = new HashSet<String>();
			readEntry(parser);
		}

		public String getTitle() {
			return title;
		}
	}

	Entry mEntry;

	List<Collector> mCollectors;

	/**
	 * Create a news article with randomly generated text.
	 * @param ngen the nonsense generator to use.
	 */
	//	public Action(NonsenseGenerator ngen) {
	//		mHeadline = ngen.makeHeadline();
	//
	//		StringBuilder sb = new StringBuilder();
	//		sb.append("<html><body>");
	//		sb.append("<h1>" + mHeadline + "</h1>");
	//		int i;
	//		for (i = 0; i < PARAGRAPHS_PER_ARTICLE; i++) {
	//			sb.append("<p>").append(ngen.makeText(SENTENCES_PER_PARAGRAPH)).append("</p>");
	//		}
	//
	//		sb.append("</body></html>");
	//		mBody = sb.toString();
	//	}

	public Action(Resources res) {
		InputStream in = res.openRawResource(R.raw.action_0000001);
		try {
			parse(in);
			Log.d("Action", "Info Size: "+mCollectors.size());
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

	void parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			readAction(parser);
		} finally {
			in.close();
		}
	}

	void readAction(XmlPullParser parser) throws XmlPullParserException, IOException {
		mCollectors = new ArrayList<Collector>();

		parser.require(XmlPullParser.START_TAG, XMLParser.ns, "action");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("info")) {
				Log.d("Action Parse", "info");
				mCollectors.add(CollectorFactory.generate(parser));
			} else if (name.equals("entry")) {
				//parser.require(XmlPullParser.START_TAG, ns, "title");
				//headline = readText(parser);
				//parser.require(XmlPullParser.END_TAG, ns, "title");
				Log.d("Action Parse", "entry");
				mEntry = new Entry(parser);
			} else {
				XMLParser.skip(parser);
			}
		}
	}

	/** Returns the headline. */
	public String getHeadline() {
		return mEntry.getTitle();
	}

	/** Returns the article body (HTML)*/
	//public String getBody() {
	//	return mBody;
	//}

	public List<Collector> getCollectorList() {
		return mCollectors;
	}
}
