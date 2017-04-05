package cs371m.myqueue;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SelectSourcesActivity extends AppCompatActivity {

    private static SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_source_layout);

        final Button continue_button = (Button) findViewById(R.id.sources_continue);
        continue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectSourcesActivity.this, BrowseActivity.class));
            }
        });

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Toolbar selectSourcesToolbar = (Toolbar)findViewById(R.id.select_sources_toolbar);
        setSupportActionBar(selectSourcesToolbar);

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


    /**
             * This is the "top-level" fragment, showing a list of items that the
             * user can pick.  Upon picking an item, it takes care of displaying the
             * data to the user as appropriate based on the current UI layout.
             */

    public static class SourcesFragment extends ListFragment {

        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            String[] rawData = getResources().getStringArray(R.array.sources);
            List<String> sources = new ArrayList(Arrays.asList(rawData));

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    sources));

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition
                        = savedInstanceState.getInt("curChoice", 0);
            }

            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            getListView().setItemChecked(0, sharedPrefs.getBoolean(getString(R.string.netflix_selected), false));
            getListView().setItemChecked(1, sharedPrefs.getBoolean(getString(R.string.hbo_selected), false));
            getListView().setItemChecked(2, sharedPrefs.getBoolean(getString(R.string.hulu_selected), false));
            getListView().setItemChecked(3, sharedPrefs.getBoolean(getString(R.string.amazon_selected), false));

        }


        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            addSource(position);
        }

        void addSource(int index) {
            mCurCheckPosition = index;

            SharedPreferences.Editor edit = sharedPrefs.edit();

            switch (mCurCheckPosition) {
                case 0:
                    boolean netflix_selected = sharedPrefs.getBoolean(getString(R.string.netflix_selected), false);
                    if(!netflix_selected) {
                        edit.putBoolean(getString(R.string.netflix_selected), true);
                    } else {
                        edit.putBoolean(getString(R.string.netflix_selected), false);
                    }
                    edit.apply();
                    break;
                case 1:
                    boolean hbo_selected = sharedPrefs.getBoolean(getString(R.string.hbo_selected), false);
                    if(!hbo_selected) {
                        edit.putBoolean(getString(R.string.hbo_selected), true);
                    } else {
                        edit.putBoolean(getString(R.string.hbo_selected), false);
                    }
                    edit.apply();
                    break;
                case 2:
                    boolean hulu_selected = sharedPrefs.getBoolean(getString(R.string.hulu_selected), false);
                    if(!hulu_selected) {
                        edit.putBoolean(getString(R.string.hulu_selected), true);
                    } else {
                        edit.putBoolean(getString(R.string.hulu_selected), false);
                    }
                    edit.apply();
                    break;
                case 3:
                    boolean amazon_selected = sharedPrefs.getBoolean(getString(R.string.amazon_selected), false);
                    if(!amazon_selected) {
                        edit.putBoolean(getString(R.string.amazon_selected), true);
                    } else {
                        edit.putBoolean(getString(R.string.amazon_selected), false);
                    }
                    edit.apply();
                    break;
            }

        }
    }
}