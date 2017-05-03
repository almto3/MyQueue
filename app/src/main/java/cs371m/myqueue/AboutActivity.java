package cs371m.myqueue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class AboutActivity extends AppCompatActivity {

    private final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        ImageView image = (ImageView) findViewById(R.id.about_image);
        Picasso.with(this)
                .load(R.drawable.about)
                .error(R.drawable.error)
                .resize(850, 700)
                .into(image);

        Toolbar aboutToolbar = (Toolbar)findViewById(R.id.about_toolbar);
        aboutToolbar.setTitle(R.string.about);
        setSupportActionBar(aboutToolbar);

        findViewById(R.id.git_pic).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://github.com/almto3/MyQueue")));
            }
        });
    }
}
