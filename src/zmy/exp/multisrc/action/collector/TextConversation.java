package zmy.exp.multisrc.action.collector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.action.util.XMLParser;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextConversation implements Collector {

	public class Info {
		public Map<String, String> options;
		public Map<String, Object> resources;
		public String title;

		public Info(XmlPullParser parser) {
			options = new HashMap<String, String>();
		}

		void readInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
			parser.require(XmlPullParser.START_TAG, XMLParser.ns, "info");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("title")) {
					title = XMLParser.readTitle(parser);
				} else {
					XMLParser.skip(parser);
				}
			}
		}
	}
	
	LinearLayout mLayout;
	TextView mText;
	EditText mEdit;

	@Override
	public String getResult() {
		return mEdit.getText().toString();
	}

	@Override
	public View getView(Context context) {
		mLayout = new LinearLayout(context);
		mText = new TextView(context);
		mText.setText("question");
		mLayout.addView(mText);
		mEdit = new EditText(context);
		mEdit.setHint("hint");
		mLayout.addView(mEdit);
		return mLayout;
	}

	@Override
	public float getProgress() {
		return 1;
	}

	public TextConversation(XmlPullParser parser) {
		;
	}

}
