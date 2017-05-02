package cs371m.myqueue;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kaivan on 5/2/2017.
 */

public class MenuActivity extends Activity implements View.OnClickListener{
    Button button;


    private static MenuActivity instance;
    public static MenuActivity get() { return instance; }
    private Queue q;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Button browse = (Button) findViewById(R.id.browse_button);
        browse.setOnClickListener(this); //calling onClick method
        Button shows = (Button) findViewById(R.id.shows_button);
        shows.setOnClickListener(this);
        Button myqueue = (Button) findViewById(R.id.myqueue_button);
        myqueue.setOnClickListener(this);
        Button select_services = (Button) findViewById(R.id.select_services_button);
        select_services.setOnClickListener(this);
        Button settings = (Button) findViewById(R.id.settings_button);
        settings.setOnClickListener(this);

        instance = this;
        q = Queue.get();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.browse_button:
                Intent browse_intent = new Intent(MenuActivity.this, BrowseActivity.class);
                startActivity(browse_intent);
                break;

            case R.id.shows_button:
                Intent shows_intent = new Intent(MenuActivity.this, SeriesActivity.class);
                startActivity(shows_intent);
                break;

            case R.id.myqueue_button:
                Intent myqueue_intent = new Intent(MenuActivity.this, MyQueueActivity.class);
                startActivity(myqueue_intent);
                break;

            case R.id.select_services_button:
                Intent services_intent = new Intent(MenuActivity.this, SelectSourcesActivity.class);
                startActivity(services_intent);
                break;

            case R.id.settings_button:
                Intent settings_intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(settings_intent);
                break;

            default:
                break;
        }

    }

}