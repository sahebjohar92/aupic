package com.aupic.aupic.Activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.R;

import butterknife.ButterKnife;

/**
 * Created by saheb on 20/10/15.
 */
public abstract class AupFragmentActivity extends ActionBarActivity {

    protected ActionBar actionBar;
    protected String titleText = "";

    protected abstract int getContentViewId();

    protected abstract int getTitleText();

    public void setTitleText(String text) {
        titleText = text;

        actionBar.setTitle(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.inject(this);

        AppBus.getInstance().register(this);
        actionBar = getSupportActionBar();
        titleText = getResources().getString(getTitleText());
        actionBar.setTitle(titleText);

        if (null != getActionbarTitle(this)) {
            getActionbarTitle(this).setEllipsize(TextUtils.TruncateAt.MARQUEE);
            getActionbarTitle(this).setSelected(true);
        }
    }

    public TextView getActionbarTitle(Activity activity) {
        return (TextView) activity.findViewById(R.id.action_bar_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



