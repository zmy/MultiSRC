package zmy.exp.multisrc.user;

import org.apache.http.protocol.HTTP;

import zmy.exp.multisrc.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class InviteActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_invite, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void sendInvitation(View view) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		// The intent does not have a URI, so declare the "text/plain" MIME type
		sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);
		//sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "[Invitation] Welcome to Join My Network at MultiSRC");
		sendIntent.putExtra(Intent.EXTRA_TEXT, "MultiSRC is ... Invite link ...");
		//TODO: email content
		//sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
		// You can also attach multiple items by passing an ArrayList of Uris
		startActivity(sendIntent);
	}

	public String getInviteLink() {
		//TODO:
		return null;
	}
}
