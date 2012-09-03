/**
 * 
 */
package zmy.exp.multisrc.action.collector;

import zmy.exp.multisrc.action.ActionDetailFragment;
import android.view.View;

/**
 * @author zmy
 *
 */
public interface Collector extends ActionDetailFragment.DetailResponseListener {
	public static final int MAX_CONTROLLER = 8;
	public String getResult();
	public View getView(ActionDetailFragment father);
	public float getProgress();
}
