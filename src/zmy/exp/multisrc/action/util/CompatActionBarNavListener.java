package zmy.exp.multisrc.action.util;

/**
 * A listener that listens to navigation events.
 *
 * Represents a listener for navigation events delivered by {@link CompatActionBarNavHandler}.
 */
public interface CompatActionBarNavListener {
    /**
     * Signals that the given category was selected.
     * @param catIndex the selected category's index.
     */
    public void onCategorySelected(int catIndex);
}
