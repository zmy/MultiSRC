package zmy.exp.multisrc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zmy.exp.multisrc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Fragment that displays detail of an action.
 */
public class ActionDetailFragment extends Fragment {
	// The webview where we display the article (our only view)
	//WebView mWebView;
	ListView mListView;
	List<Map<String, Object>> mInfoList = new ArrayList<Map<String, Object>>();
	
	SimpleAdapter mListAdapter;

	// The action we are to display
	Action mAction = null;

	// Parameterless constructor is needed by framework
	public ActionDetailFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Detail Fragment", "At Create");
		mListAdapter = new SimpleAdapter(getActivity(), mInfoList, R.layout.info_list_item,
				new String[]{"text"}, new int[]{R.id.info_text});
	}
	
	/**
	 * Sets up the UI. It consists if a single WebView.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//mWebView = new WebView(getActivity());
		//loadWebView();
		//return mWebView;
		Log.d("Detail Fragment", "CreateView");
		mListView = new ListView(getActivity());
		mListView.setAdapter(mListAdapter);
		loadListView();
		Button button = new Button(getActivity());
		button.setText("Submit");
		mListView.addFooterView(button);
		return mListView;
	}
	
//	@Override
//	public void onStart() {
//		super.onStart();
//		Log.d("Detail Fragment", "At Start, Is mListAdapter null: "+(mListAdapter == null));
//		setListAdapter(mListAdapter);
//		//loadCategory(0);
//	}

	/**
	 * Displays a particular action.
	 *
	 * @param action the action to display
	 */
	public void displayAction(Action action) {
		//mAction = action;
		//loadWebView();
		Log.d("Detail Fragment", "Display "+mInfoList.size());
		mAction = action;
		loadListView();
	}

	/**
	 * Loads action data into the webview.
	 *
	 * This method is called internally to update the webview's contents to the appropriate
	 * action's text.
	 */
	//void loadWebView() {
	//	if (mWebView != null) {
	//		mWebView.loadData(mAction == null ? "" : mAction.getBody(), "text/html",
	//				"utf-8");
	//	}
	//}

	void loadListView() {
		mInfoList.clear();
		for (Action.Info i: mAction.getInfoList()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text", i.title);
			mInfoList.add(map);
		}
		Log.d("Detail Fragment", "Load "+mInfoList.size());
		//setListAdapter(mListAdapter);
		if (mListAdapter != null)
			mListAdapter.notifyDataSetChanged();
	}
}
