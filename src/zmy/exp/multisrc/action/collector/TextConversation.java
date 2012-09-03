package zmy.exp.multisrc.action.collector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.action.ActionDetailFragment;
import zmy.exp.multisrc.action.util.XMLParser;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextConversation implements Collector {

	Map<String, String> options;
	String question, hint;

	LinearLayout mLayout;
	TextView mText;
	EditText mEdit;
	ActionDetailFragment mFather;

	@Override
	public String getResult() {
		return mEdit.getText().toString();
	}

	@Override
	public View getView(ActionDetailFragment father) {
		if (mFather == null || !mFather.equals(father)) {
			mLayout = new LinearLayout(father.getActivity());
			mLayout.setOrientation(LinearLayout.VERTICAL);
			mText = new TextView(father.getActivity());
			mText.setText(question);
			mLayout.addView(mText);
			mEdit = new EditText(father.getActivity());
			mEdit.setHint(hint);
			mLayout.addView(mEdit);
			mFather = father;
		}
		return mLayout;
	}

	@Override
	public float getProgress() {
		//TODO:
		return 1;
	}

	public TextConversation(XmlPullParser parser) {
		options = new HashMap<String, String>();
		mFather = null;
		try {
			readInfo(parser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void readInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, XMLParser.ns, "info");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				hint = XMLParser.readTitle(parser);
			} else if (name.equals("description")) {
				question = XMLParser.readDescription(parser);
			} else {
				XMLParser.skip(parser);
			}
		}
	}

	@Override
	public void onResponseResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}

}
