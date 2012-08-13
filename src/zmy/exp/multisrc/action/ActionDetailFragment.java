package zmy.exp.multisrc.action;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Fragment that displays detail of an action.
 */
public class ActionDetailFragment extends Fragment {
	// The webview where we display the article (our only view)
	WebView mWebView;

	// The action we are to display
	Action mAction = null;

	// Parameterless constructor is needed by framework
	public ActionDetailFragment() {
		super();
	}

	/**
	 * Sets up the UI. It consists if a single WebView.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mWebView = new WebView(getActivity());
		loadWebView();
		return mWebView;
	}

	/**
	 * Displays a particular action.
	 *
	 * @param action the action to display
	 */
	public void displayAction(Action action) {
		mAction = action;
		loadWebView();
	}

	/**
	 * Loads action data into the webview.
	 *
	 * This method is called internally to update the webview's contents to the appropriate
	 * action's text.
	 */
	void loadWebView() {
		if (mWebView != null) {
			mWebView.loadData(mAction == null ? "" : mAction.getBody(), "text/html",
					"utf-8");
		}
	}

}
