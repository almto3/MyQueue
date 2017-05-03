package cs371m.myqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_layout);

        Toolbar searchToolbar = (Toolbar)findViewById(R.id.search_toolbar);
        searchToolbar.setTitle(R.string.activity_search);
        setSupportActionBar(searchToolbar);

    }

}
