package zmy.exp.multisrc.action.store;

import android.content.res.Resources;

/**
 * Source/repository of actions.
 */
public class ActionSource {

	// the instance
	static ActionSource instance = null;

	// List of category titles
	public static final String CATEGORIES[] = { "Recommend", "Popular", "WiFi", "SNS", "Friends", "Others" };

	// category objects, representing each category
	ActionCategory[] mCategory;

	/** Returns the singleton instance of this class. */
	public static ActionSource getInstance(Resources res) {
		if (instance == null) {
			instance = new ActionSource(res);
		}
		return instance;
	}

	//	public ActionSource() {
	//		int i;
	//		mCategory = new ActionCategory[CATEGORIES.length];
	//		for (i = 0; i < CATEGORIES.length; i++) {
	//			mCategory[i] = new ActionCategory();
	//		}
	//	}

	public ActionSource(Resources res) {
		int i;
		mCategory = new ActionCategory[CATEGORIES.length];
		for (i = 0; i < CATEGORIES.length; i++) {
			mCategory[i] = new ActionCategory(res);
		}
	}

	/** Returns the list of action categories. */
	public String[] getCategories() {
		return CATEGORIES;
	}

	/** Returns a category by index. */
	public ActionCategory getCategory(int categoryIndex) {
		return mCategory[categoryIndex];
	}
}
