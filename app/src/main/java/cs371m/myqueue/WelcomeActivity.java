package cs371m.myqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("WelcomeActivity", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        setListener();
    }

    private void setListener(){
        Log.d("WelcomeActivity", "setListener");
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.welcome_layout);
        lLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WelcomeActivity", "setListener");
                startActivity(new Intent(WelcomeActivity.this, SelectSourcesActivity.class));
            }

        });

    }
}
