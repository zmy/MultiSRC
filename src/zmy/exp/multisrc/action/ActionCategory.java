package zmy.exp.multisrc.action;

/**
 * An action category (collection of actions).
 */
public class ActionCategory {
	// how many action?
	final int ARTICLES_PER_CATEGORY = 20;

	// array of our actions
	Action[] mActions;

	/**
	 * Create an action category.
	 */
	public ActionCategory() {
        NonsenseGenerator ngen = new NonsenseGenerator();
        mActions = new Action[ARTICLES_PER_CATEGORY];
        int i;
        for (i = 0; i < mActions.length; i++) {
        	mActions[i] = new Action(ngen);
        }
    }

    /** Returns how many actions exist in this category. */
    public int getActionCount() {
        return mActions.length;
    }

    /** Gets a particular action by index. */
    public Action getAction(int index) {
        return mActions[index];
    }
}
