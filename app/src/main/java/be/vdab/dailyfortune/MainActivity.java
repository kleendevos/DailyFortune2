package be.vdab.dailyfortune;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPreferences preferences = new MyPreferences(MainActivity.this);
        if(!preferences.isFirstTime()){
            Intent i = new Intent(getApplicationContext(),FortuneActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
        }

        final Intent serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
        Button btn_startService = (Button) findViewById(R.id.button2);
        btn_startService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startService(serviceIntent);
            }
        });

        Button btn_stopService = (Button) findViewById(R.id.button3);
        btn_stopService.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stopService(serviceIntent);
            }
        });

    }

    public void SaveUserName (View view) {
        EditText userName = (EditText)findViewById(R.id.EditText1);
        MyPreferences preferences = new MyPreferences(MainActivity.this);
        preferences.setUserName(userName.getText().toString().trim());
        Intent i = new Intent(getApplicationContext(),FortuneActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }


}
