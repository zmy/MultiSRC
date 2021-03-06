package zmy.exp.multisrc.user;

import zmy.exp.multisrc.R;
import zmy.exp.multisrc.action.ActionViewerActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	public final static String EXTRA_MESSAGE = "zmy.exp.multisrc.MESSAGE";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        //TODO: switch to auto-signin or login/register
    }
    /** Called after register/login successfully */
    public void getInvolved(View view) {
    	//TODO: get start
    	Intent intent = new Intent(this, ActionViewerActivity.class);
    	startActivity(intent);
    }
    /** Called when the user selects the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}