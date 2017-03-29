package cs371m.myqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;




public class screen3 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3);
        setListener();
        Log.d("screen3", "onCreate");
        Log.i("screen3", "onCreate");
    }

    private void setListener(){
        Log.d("screen3", "setListener");
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.screen3);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("screen3", "setListener");
                startActivity(new Intent(screen3.this, AboutActivity.class));
                //return true;
            }

        });

    }
}
