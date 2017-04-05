package cs371m.myqueue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class BrowseActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = sharedPrefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = sharedPrefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), true);
            edit.commit();
            startActivity(new Intent(BrowseActivity.this, WelcomeActivity.class));
        }

        setContentView(R.layout.browse_layout);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                TypedArray rotten = getResources().obtainTypedArray(R.array.rotten_tomatoes_score);
                TypedArray movie_plot = getResources().obtainTypedArray(R.array.movie_plots);

                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/imageBitmap" + ".png");
                    Log.e("file: ", file.toString());
                    FileOutputStream stream = new FileOutputStream(file);
                    item.getImage().compress(Bitmap.CompressFormat.JPEG, 50, stream);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //Create intent
                Intent intent = new Intent(BrowseActivity.this, MediaDetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", "imageBitmap");
                intent.putExtra("rotten",rotten.getString(position));
                intent.putExtra("movie_plot",movie_plot.getString(position));

                startActivity(intent);
            }
        });

        Toolbar browseToolbar = (Toolbar)findViewById(R.id.browse_toolbar);
        setSupportActionBar(browseToolbar);
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
                return true;
            case R.id.menu_bookmarks:
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

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            String movie_name = imgs.getString(i).replace("res/drawable/", "");
            movie_name = movie_name.replace(".jpg","");
            movie_name = movie_name.replace("_"," ");
            movie_name = movie_name.toUpperCase();
            imageItems.add(new ImageItem(bitmap, movie_name));
        }
        return imageItems;
    }
}
