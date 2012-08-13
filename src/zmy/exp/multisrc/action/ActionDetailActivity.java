package zmy.exp.multisrc.action;

import zmy.exp.multisrc.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Activity that displays a particular action onscreen.
 *
 * This activity is started only when the screen is not large enough for a two-pane layout, in
 * which case this separate activity is shown in order to display the action details. This activity
 * kills itself if the display is reconfigured into a shape that allows a two-pane layout, since
 * in that case the news article will be displayed by the {@link ActionViewerActivity} and this
 * Activity therefore becomes unnecessary.
 */
public class ActionDetailActivity extends FragmentActivity {

	// The news category index and the article index for the article we are to display
	int mCatIndex, mActIndex;

	/**
	 * Sets up the activity.
	 *
	 * Setting up the activity means reading the category/action index from the Intent that
	 * fired this Activity and loading it onto the UI. We also detect if there has been a
	 * screen configuration change (in particular, a rotation) that makes this activity
	 * unnecessary, in which case we do the honorable thing and get out of the way.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCatIndex = getIntent().getExtras().getInt("catIndex", 0);
		mActIndex = getIntent().getExtras().getInt("artIndex", 0);

		// If we are in two-pane layout mode, this activity is no longer necessary
		if (getResources().getBoolean(R.bool.has_two_panes)) {
			finish();
			return;
		}

		// Place an ActionDetailFragment as our content pane
		ActionDetailFragment f = new ActionDetailFragment();
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, f).commit();

		// Display the correct action detail on the fragment
		Action action = ActionSource.getInstance().getCategory(mCatIndex).getAction(mActIndex);
		f.displayAction(action);
	}

}
