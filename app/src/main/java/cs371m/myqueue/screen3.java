package cs371m.myqueue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;




public class screen3 extends Activity {

    Integer[] imageIDs={
            R.drawable.martian_poster,
            R.drawable.netflix,
            R.drawable.hbo,
            R.drawable.hulu,
            R.drawable.bookmark
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3);
        Log.d("screen3", "onCreate");
        Log.i("screen3", "onCreate");
    }
}
