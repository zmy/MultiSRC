package zmy.exp.multisrc.Survey;

import zmy.exp.multisrc.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

public class SurveyListActivity extends FragmentActivity
        implements SurveyListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("SurveyListActivity", "Created");

        if (findViewById(R.id.survey_detail_container) != null) {
        	Log.d("SurveyListActivity", "mTwoPane");
            mTwoPane = true;
            ((SurveyListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.survey_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(SurveyDetailFragment.ARG_ITEM_ID, id);
            SurveyDetailFragment fragment = new SurveyDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.survey_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, SurveyDetailActivity.class);
            detailIntent.putExtra(SurveyDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
