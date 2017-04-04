package cs371m.myqueue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MediaDetailsActivity extends AppCompatActivity {

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
        setContentView(R.layout.media_details_layout);
        Log.d("MediaDetailsActivity", "onCreate");
        Log.i("MediaDetailsActivity", "onCreate");

        //added
        String title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        //ImageView imageView = (ImageView) findViewById(R.id.image);
        //imageView.setImageBitmap(bitmap);
        //until here, breaks because of this

        Toolbar itemDetailsToolbar = (Toolbar)findViewById(R.id.item_details_toolbar);
        setSupportActionBar(itemDetailsToolbar);
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
