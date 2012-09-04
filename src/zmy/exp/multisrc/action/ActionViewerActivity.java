package zmy.exp.multisrc.action;

import zmy.exp.multisrc.R;
import zmy.exp.multisrc.action.store.ActionCategory;
import zmy.exp.multisrc.action.store.ActionSource;
import zmy.exp.multisrc.action.util.CompatActionBarNavHandler;
import zmy.exp.multisrc.action.util.CompatActionBarNavListener;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;

/**
 * Main activity: shows list and details of actions, if layout permits.
 *
 * This is the main activity of the application. It can have several different layouts depending
 * on the SDK version, screen size and orientation. The configurations are divided in two large
 * groups: single-pane layouts and dual-pane layouts.
 *
 * In single-pane mode, this activity shows a list of actions using a {@link ActionListFragment}.
 * When the user clicks on an item, a separate activity (a {@link ActionDetailActivity}) is launched
 * to show the detail of the item.
 *
 * In dual-pane mode, this activity shows a {@link ActionListFragment} on the left side and an
 * {@ActionDetailFragment} on the right side. When the user selects an item on the left, the
 * corresponding action detail is shown on the right.
 *
 * If an Action Bar is available (large enough screen and SDK version 11 or up), navigation
 * controls are shown in the Action Bar (whether to show tabs or a list depends on the layout).
 * If an Action Bar is not available, a regular image and button are shown in the top area of
 * the screen, emulating an Action Bar.
 */
public class ActionViewerActivity extends FragmentActivity
implements ActionListFragment.OnItemSelectedListener, CompatActionBarNavListener, OnClickListener{

	// Whether or not we are in dual-pane mode
	boolean mIsDualPane = false;

	// The fragment where the items are displayed
	ActionListFragment mListFragment;

	// The fragment where the detail is displayed (null if absent)
	ActionDetailFragment mDetailFragment;

	// The news category and action index currently being displayed
	int mCatIndex = 0;
	int mArtIndex = 0;
	ActionCategory mCurrentCat;

	@Override
	public void onStart() {
		super.onStart();
		setActionCategory(0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		//ActionDetailFragment.mAction = new Action(getResources());

		// find our fragments
		mListFragment = (ActionListFragment) getSupportFragmentManager().findFragmentById(
				R.id.actionlist);
		mDetailFragment = (ActionDetailFragment) getSupportFragmentManager().findFragmentById(
				R.id.actiondetail);

		// Determine whether we are in single-pane or dual-pane mode by testing the visibility
		// of the detail view.
		View actionView = findViewById(R.id.actiondetail);
		mIsDualPane = actionView != null && actionView.getVisibility() == View.VISIBLE;

		// Register ourselves as the listener for the headlines fragment events.
		mListFragment.setOnItemSelectedListener(this);

		// Set up the Action Bar (or not, if one is not available)
		int catIndex = savedInstanceState == null ? 0 : savedInstanceState.getInt("catIndex", 0);
		setUpActionBar(mIsDualPane, catIndex);

		// Set up list fragment
		mListFragment.setSelectable(mIsDualPane);
		restoreSelection(savedInstanceState);

		// Set up the category button (shown if an Action Bar is not available)
		Button catButton = (Button) findViewById(R.id.categorybutton);
		if (catButton != null) {
			catButton.setOnClickListener(this);
		}
	}

	/** Called when an item is selected.
	 *
	 * This is called by the ActionListFragment (via its listener interface) to notify us that a
	 * headline was selected in the Action Bar. The way we react depends on whether we are in
	 * single or dual-pane mode. In single-pane mode, we launch a new activity to display the
	 * selected article; in dual-pane mode we simply display it on the article fragment.
	 *
	 * @param index the index of the selected headline.
	 */
	@Override
	public void onItemSelected(int index) {
		mArtIndex = index;
		if (mIsDualPane) {
			// display it on the detail fragment
			mDetailFragment.displayAction(mCurrentCat.getAction(index));
		}
		else {
			// use separate activity
			Intent i = new Intent(this, ActionDetailActivity.class);
			i.putExtra("catIndex", mCatIndex);
			i.putExtra("artIndex", index);
			startActivity(i);
		}
	}

	/** Called when a news category is selected.
	 *
	 * This is called by our CompatActionBarNavHandler in response to the user selecting a
	 * news category in the Action Bar. We react by loading and displaying the items for
	 * that category.
	 *
	 * @param catIndex the index of the selected news category.
	 */
	@Override
	public void onCategorySelected(int catIndex) {
		setActionCategory(catIndex);
	}

	/** Save instance state. Saves current category/article index. */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("catIndex", mCatIndex);
		outState.putInt("artIndex", mArtIndex);
		super.onSaveInstanceState(outState);
	}

	/** Called when news category button is clicked.
	 *
	 * This is the button that we display on UIs that don't have an action bar. This button
	 * calls up a list of news categories and switches to the given category.
	 */
	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select a Category");
		builder.setItems(ActionSource.CATEGORIES, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setActionCategory(which);
			}
		});
		AlertDialog d = builder.create();
		d.show();
	}

	/** Sets the displayed news category.
	 *
	 * This causes the list fragment to be repopulated with the appropriate headlines.
	 */
	void setActionCategory(int categoryIndex) {
		mCatIndex = categoryIndex;
		mCurrentCat = ActionSource.getInstance(getResources()).getCategory(categoryIndex);
		mListFragment.loadCategory(categoryIndex);

		// If we are displaying the article on the right, we have to update that too
		if (mIsDualPane) {
			mDetailFragment.displayAction(mCurrentCat.getAction(0));
		}

		// If we are displaying a "category" button (on the ActionBar-less UI), we have to update
		// its text to reflect the current category.
		Button catButton = (Button) findViewById(R.id.categorybutton);
		if (catButton != null) {
			catButton.setText(ActionSource.CATEGORIES[mCatIndex]);
		}
	}

	/** Restore category/action selection from saved state. */
	void restoreSelection(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			setActionCategory(savedInstanceState.getInt("catIndex", 0));
			if (mIsDualPane) {
				int actIndex = savedInstanceState.getInt("artIndex", 0);
				mListFragment.setSelection(actIndex);
				onItemSelected(actIndex);
			}
		}
	}

	/** Sets up Action Bar (if present).
	 *
	 * @param showTabs whether to show tabs (if false, will show list).
	 * @param selTab the selected tab or list item.
	 */
	@TargetApi(11)
	public void setUpActionBar(boolean showTabs, int selTab) {
		if (Build.VERSION.SDK_INT < 11) {
			// No action bar for you!
			// But do not despair. In this case the layout includes a bar across the
			// top that looks and feels like an action bar, but is made up of regular views.
			return;
		}

		//Log.d("here", "here");
		android.app.ActionBar actionBar = getActionBar();
		//Log.d("there", ""+(actionBar == null));
		actionBar.setDisplayShowTitleEnabled(false);

		// Set up a CompatActionBarNavHandler to deliver us the Action Bar nagivation events
		CompatActionBarNavHandler handler = new CompatActionBarNavHandler(this);
		if (showTabs) {
			actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
			int i;
			for (i = 0; i < ActionSource.CATEGORIES.length; i++) {
				actionBar.addTab(actionBar.newTab().setText(ActionSource.CATEGORIES[i]).setTabListener(handler));
			}
			actionBar.setSelectedNavigationItem(selTab);
		}
		else {
			actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
			SpinnerAdapter adap = new ArrayAdapter<String>(this, R.layout.actionbar_list_item,
					ActionSource.CATEGORIES);
			actionBar.setListNavigationCallbacks(adap, handler);
		}

		// Show logo instead of icon+title.
		actionBar.setDisplayUseLogoEnabled(true);
	}

}
