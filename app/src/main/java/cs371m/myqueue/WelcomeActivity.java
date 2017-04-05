package cs371m.myqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Created by scottm on 6/7/2016.
 *
 * A simple about activity.
 */


public class WelcomeActivity extends Activity {

    boolean firsttime = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("WelcomeActivity", "onCreate");

        if (firsttime) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome_layout);
            setListener();
            firsttime = false;      //need to log this in the prefs
        }
        else{
            openBrowse();
        }

    }

    private void setListener(){
        Log.d("WelcomeActivity", "setListener");
        LinearLayout llayout = (LinearLayout) findViewById(R.id.screen0);
        llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WelcomeActivity", "setListener");
                openBrowse();
            }

        });

    }

    private void openBrowse(){
        startActivity(new Intent(WelcomeActivity.this, SelectSourcesActivity.class));
    }
}
