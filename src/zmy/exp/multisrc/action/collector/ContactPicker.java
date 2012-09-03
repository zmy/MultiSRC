package zmy.exp.multisrc.action.collector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import zmy.exp.multisrc.action.ActionDetailFragment;
import zmy.exp.multisrc.action.util.XMLParser;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactPicker implements Collector, OnClickListener {

	static final int PICK_CONTACT_REQUEST = 1;  // The request code

	Map<String, String> options;
	String description, hint;

	LinearLayout mLayout;
	TextView mText;
	Button mButton;
	ActionDetailFragment mFather;

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getView(ActionDetailFragment father) {
		if (mFather == null || !mFather.equals(father)) {
			mLayout = new LinearLayout(father.getActivity());
			mLayout.setOrientation(LinearLayout.VERTICAL);
			mText = new TextView(father.getActivity());
			mText.setText(description);
			mLayout.addView(mText);
			mButton = new Button(father.getActivity());
			mButton.setHint(hint);
			mButton.setOnClickListener(this);
			mLayout.addView(mButton);
			mFather = father;
		}
		return mLayout;
	}

	@Override
	public float getProgress() {
		// TODO Auto-generated method stub
		return 1;
	}

	public ContactPicker(XmlPullParser parser) {
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
				description = XMLParser.readDescription(parser);
			} else {
				XMLParser.skip(parser);
			}
		}
	}

	@Override
	public void onClick(View v) {
		//Pick Contact
		Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
		pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
		mFather.startActivityFromCollector(this, pickContactIntent, PICK_CONTACT_REQUEST);
	}

	@Override
	public void onResponseResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// Check which request we're responding to
		if (requestCode == PICK_CONTACT_REQUEST) {
			// Make sure the request was successful
			if (resultCode == Activity.RESULT_OK) {
				// Get the URI that points to the selected contact
				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only one row in the result
				String[] projection = {Phone.NUMBER};

				// Perform the query on the contact to get the NUMBER column
				// We don't need a selection or sort order (there's only one result for the given URI)
				// CAUTION: The query() method should be called from a separate thread to avoid blocking
				// your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
				// Consider using CursorLoader to perform the query.
				Cursor cursor = mFather.getActivity().getContentResolver()
						.query(contactUri, projection, null, null, null);
				cursor.moveToFirst();

				// Retrieve the phone number from the NUMBER column
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);

				// Do something with the phone number...
				mButton.setText(number+"(Change)");
			}
		}
	}

}
