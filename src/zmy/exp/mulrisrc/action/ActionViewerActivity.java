package zmy.exp.mulrisrc.action;

import android.support.v4.app.FragmentActivity;

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
public class ActionViewerActivity extends FragmentActivity {

}
