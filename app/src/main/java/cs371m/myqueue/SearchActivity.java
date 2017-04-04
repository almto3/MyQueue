package cs371m.myqueue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by erinjensby on 4/3/17.
 */

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_layout);

        Toolbar searchToolbar = (Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(searchToolbar);

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
