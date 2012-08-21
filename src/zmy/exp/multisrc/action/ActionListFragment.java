package zmy.exp.multisrc.action;

import java.util.ArrayList;
import java.util.List;

import zmy.exp.multisrc.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Fragment that displays the action items for a particular action category.
 *
 * This Fragment displays a list with the news headlines for a particular news category.
 * When an item is selected, it notifies the configured listener that a headlines was selected.
 */
public class ActionListFragment extends ListFragment implements
		OnItemClickListener {

	// The list of items that we are displaying
	List<String> mItemsList = new ArrayList<String>();

	// The list adapter for the list we are displaying
	ArrayAdapter<String> mListAdapter;

	// The listener we are to notify when an item is selected
	OnItemSelectedListener mItemSelectedListener = null;

	/**
	 * Represents a listener that will be notified of item selections.
	 */
	public interface OnItemSelectedListener {
		/**
		 * Called when a given item is selected.
		 * @param index the index of the selected headline.
		 */
		public void onItemSelected(int index);
	}

	/**
	 * Default constructor required by framework.
	 */
	public ActionListFragment() {
		super();
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("List Fragment", "At Start");
		setListAdapter(mListAdapter);
		getListView().setOnItemClickListener(this);
		loadCategory(0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("List Fragment", "At Creat");
		mListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.action_list_item, 
				mItemsList);
	}

	/**
	 * Sets the listener that should be notified of item selection events.
	 * @param listener the listener to notify.
	 */
	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		mItemSelectedListener = listener;
	}

	/**
	 * Load and display the items for the given action category.
	 * @param categoryIndex the index of the action category to display.
	 */
	public void loadCategory(int categoryIndex) {
		Log.d("List Fragment", "At Load");
		mItemsList.clear();
		int i;
		ActionCategory cat = ActionSource.getInstance(getResources()).getCategory(categoryIndex);
		for (i = 0; i < cat.getActionCount(); i++) {
			mItemsList.add(cat.getAction(i).getHeadline());
		}
		mListAdapter.notifyDataSetChanged();
	}

	/**
	 * Handles a click on an item.
	 *
	 * This causes the configured listener to be notified that an item was selected.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (null != mItemSelectedListener) {
			mItemSelectedListener.onItemSelected(position);
		}
	}

	/** Sets choice mode for the list
	 *
	 * @param selectable whether list is to be selectable.
	 */
	public void setSelectable(boolean selectable) {
		if (selectable) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		else {
			getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
		}
	}

}
