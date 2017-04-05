package cs371m.myqueue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MediaDetailsActivity extends AppCompatActivity {

    private final String TAG = "MediaDetailsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_details_layout);
        Log.d("MediaDetailsActivity", "onCreate");
        Log.i("MediaDetailsActivity", "onCreate");

        Toolbar itemDetailsToolbar = (Toolbar)findViewById(R.id.item_details_toolbar);
        setSupportActionBar(itemDetailsToolbar);

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setListeners() {
        Log.d(TAG, "setListeners()");

        ImageView img0 = (ImageView) findViewById(R.id.item_details_bookmarkIcon);
        img0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_bookmarkIcon");
                Toast.makeText(getBaseContext(), "Bookmark feature isn't fully supported right now", Toast.LENGTH_LONG).show();

            }
        });

        ImageView img1 = (ImageView) findViewById(R.id.item_details_service0);
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service0");
                Toast.makeText(getBaseContext(), "Hulu isn't supported right now", Toast.LENGTH_LONG).show();
            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.item_details_service1);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service1");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.netflix.com"));
                startActivity(browserIntent);

            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.item_details_service2);
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service2");
                Toast.makeText(getBaseContext(), "HBO isn't supported right now", Toast.LENGTH_LONG).show();

            }
        });

        ImageView img4 = (ImageView) findViewById(R.id.item_details_service3);
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick() - item_details_service3");
                Toast.makeText(getBaseContext(), "amazon prime isn't supported right now", Toast.LENGTH_LONG).show();

            }
        });

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
