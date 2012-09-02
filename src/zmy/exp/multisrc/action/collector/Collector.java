/**
 * 
 */
package zmy.exp.multisrc.action.collector;

import android.content.Context;
import android.view.View;

/**
 * @author zmy
 *
 */
public interface Collector {
	public String getResult();
	public View getView(Context context);
	public float getProgress();
}
