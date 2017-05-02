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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectSourcesActivity extends AppCompatActivity {

    private static SharedPreferences sharedPrefs;
    private SelectSourcesActivity activity;

    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.select_source_layout);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        boolean previouslyStarted = sharedPrefs.getBoolean(getString
                (R.string.pref_previously_selected_sources), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = sharedPrefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_selected_sources), true);
            edit.commit();
            TextView select_sources_prompt = (TextView) findViewById(R.id.select_sources_prompt);
            select_sources_prompt.setText(getString(R.string.select_source_prompt));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        final Button continue_button = (Button) findViewById(R.id.sources_continue);
        continue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!sharedPrefs.getBoolean(getString(R.string.netflix_selected), false) &&
                        !sharedPrefs.getBoolean(getString(R.string.hulu_selected), false) &&
                        !sharedPrefs.getBoolean(getString(R.string.hbo_selected), false) &&
                        !sharedPrefs.getBoolean(getString(R.string.amazon_selected), false)) {
                    Toast.makeText(getBaseContext(),
                            R.string.force_select_service, Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(activity, BrowseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        });

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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_browse:
                intent = new Intent(this, BrowseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_bookmarks:
                Toast.makeText(getBaseContext(), R.string.bookmarks_not_implemented,
                        Toast.LENGTH_LONG).show();
                intent = new Intent(this, BrowseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class SourcesFragment extends ListFragment {

        int mCurCheckPosition = 0;

        private DatabaseReference mDatabase;
        private String userId;

        public static int numSelected;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userId = user.getUid();

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
            getListView().setItemChecked(1, sharedPrefs.getBoolean(getString(R.string.hulu_selected), false));
            getListView().setItemChecked(2, sharedPrefs.getBoolean(getString(R.string.hbo_selected), false));
            getListView().setItemChecked(3, sharedPrefs.getBoolean(getString(R.string.amazon_selected), false));

        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            numSelected = getListView().getCheckedItemCount();
            addSource(position);
        }

        void addSource(final int index) {
            mCurCheckPosition = index;

            SharedPreferences.Editor edit = sharedPrefs.edit();

            switch (mCurCheckPosition) {
                case 0:
                    boolean netflix_selected = sharedPrefs.getBoolean(getString(R.string.netflix_selected), false);
                    if(!netflix_selected) {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.netflix_source)).setValue(true);
                        edit.putBoolean(getString(R.string.netflix_selected), true);
                    } else {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.netflix_source)).setValue(false);
                        edit.putBoolean(getString(R.string.netflix_selected), false);
                    }
                    edit.apply();
                    break;
                case 1:
                    boolean hbo_selected = sharedPrefs.getBoolean(getString(R.string.hulu_selected), false);
                    if(!hbo_selected) {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.hulu_source)).setValue(true);
                        edit.putBoolean(getString(R.string.hulu_selected), true);
                    } else {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.hulu_source)).setValue(false);
                        edit.putBoolean(getString(R.string.hulu_selected), false);
                    }
                    edit.apply();
                    break;
                case 2:
                    boolean hulu_selected = sharedPrefs.getBoolean(getString(R.string.hbo_selected), false);
                    if(!hulu_selected) {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.hbo_source)).setValue(true);
                        edit.putBoolean(getString(R.string.hbo_selected), true);
                    } else {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.hbo_source)).setValue(false);
                        edit.putBoolean(getString(R.string.hbo_selected), false);
                    }
                    edit.apply();
                    break;
                case 3:
                    boolean amazon_selected = sharedPrefs.getBoolean(getString(R.string.amazon_selected), false);
                    if(!amazon_selected) {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.amazon_source)).setValue(true);
                        edit.putBoolean(getString(R.string.amazon_selected), true);
                    } else {
                        mDatabase.child("users").child(userId).child("services").child
                                (getString(R.string.amazon_source)).setValue(false);
                        edit.putBoolean(getString(R.string.amazon_selected), false);
                    }
                    edit.apply();
                    break;
            }
        }
    }
}