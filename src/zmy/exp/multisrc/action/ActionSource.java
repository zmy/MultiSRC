package zmy.exp.multisrc.action;

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
	public static ActionSource getInstance() {
		if (instance == null) {
			instance = new ActionSource();
		}
		return instance;
	}

	public ActionSource() {
		int i;
		mCategory = new ActionCategory[CATEGORIES.length];
		for (i = 0; i < CATEGORIES.length; i++) {
			mCategory[i] = new ActionCategory();
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
