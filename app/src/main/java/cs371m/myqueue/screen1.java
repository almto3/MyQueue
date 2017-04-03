package cs371m.myqueue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class screen1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.source_fragment_layout);

        final Button continue_button = (Button) findViewById(R.id.sources_continue);
        continue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(screen1.this, screen2.class));
            }
        });

    }

            /**
             * This is the "top-level" fragment, showing a list of items that the
             * user can pick.  Upon picking an item, it takes care of displaying the
             * data to the user as appropriate based on the current UI layout.
             */

    public static class TitlesFragment extends ListFragment {

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

            // Not yet implemented, currently uses only Netflix automatically

        }
    }
}

/*
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erinjensby on 3/26/17.
 */

/*
public class screen1 extends ListActivity {

    private static final String TAG = "SourceList";

    private ListView view;
    private List<String> sources;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createModel();

        view = getListView();

        setAdapter();

        createOnItemClickListener();
    }

    private void setAdapter() {
        // for layout that is simply a TextViews
        adapter
                = new ArrayAdapter<String>(
                this,
                R.layout.list_item, // android.R.layout.simple_list_item_1,
                sources);

/*
        // for layout with TextView in more complex layout
        adapter
                = new ArrayAdapter<String>(
                this, // context
                R.layout.list_item, // layout of list items / rows
                R.id.sourceTextView, // sub layout to place text
                sources); // model of text

        // don't comment out!
        setListAdapter(adapter);
    }

    private void createModel() {
        String[] rawData = getResources().getStringArray(R.array.sources);
        sources = new ArrayList(Arrays.asList(rawData));
    }

    private void createOnItemClickListener() {

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Log.d(TAG, "Selected view: " + v);

                String source = sources.get(position);

                String toastString = "position: " + position +
                        ", id: " + id + "\ndata: "
                        + source;

            }
        });
    }
}
*/