package cs371m.myqueue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.Context;

import com.squareup.picasso.Picasso;


public class AboutActivity extends AppCompatActivity {

    private final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Log.d(TAG, "1");
        ImageView image = (ImageView) findViewById(R.id.about_image);
        Log.d(TAG, "2");
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.about);
        Log.d(TAG, "3");
        image.setImageBitmap(bMap);
        Log.d(TAG, "4");
        */
        setContentView(R.layout.about_layout);

        ImageView image = (ImageView) findViewById(R.id.about_image);
        Picasso.with(this)
                .load(R.drawable.about)
                .error(R.drawable.error)
                .resize(750, 600)
                .into(image);

        Toolbar aboutToolbar = (Toolbar)findViewById(R.id.about_toolbar);
        setSupportActionBar(aboutToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_browse:
                startActivity(new Intent(this, BrowseActivity.class));
                return true;
            case R.id.menu_bookmarks:
                startActivity(new Intent(this, BrowseActivity.class));
                return true;
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
