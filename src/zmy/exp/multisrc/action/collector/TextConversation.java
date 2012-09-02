package zmy.exp.multisrc.action.collector;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextConversation implements Collector {

	LinearLayout mLayout;
	TextView mText;
	EditText mEdit;
	
	@Override
	public String getResult() {
		return mEdit.getText().toString();
	}

	@Override
	public View getView() {
		return mLayout;
	}

	@Override
	public float getProgress() {
		return 1;
	}
	
	public TextConversation(String question, String hint, Context context) {
		mLayout = new LinearLayout(context);
		mText = new TextView(context);
		mText.setText(question);
		mLayout.addView(mText);
		mEdit = new EditText(context);
		mEdit.setHint(hint);
		mLayout.addView(mEdit);
	}

}
