package zmy.exp.multisrc.action.collector;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.action.util.XMLParser;

public class CollectorFactory {
	public static Collector generate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, XMLParser.ns, "info");
		String type = parser.getAttributeValue(XMLParser.ns, "collector");
		if (type.equals("ContactPicker")) {
			return new ContactPicker(parser);
		} else if (type.equals("TextConversation")) {
			return new TextConversation(parser);
		} else {
			//TODO: add other collectors
			;
		}
		return null;
	}
}
