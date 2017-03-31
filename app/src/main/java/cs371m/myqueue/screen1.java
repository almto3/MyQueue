package cs371m.myqueue;

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
