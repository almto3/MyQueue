package cs371m.myqueue;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class ItemDetailsActivity extends Activity {

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
        setContentView(R.layout.item_details_layout);
        Log.d("ItemDetailsActivity", "onCreate");
        Log.i("ItemDetailsActivity", "onCreate");
    }
}
