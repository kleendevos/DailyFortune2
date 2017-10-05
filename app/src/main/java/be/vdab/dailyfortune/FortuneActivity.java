package be.vdab.dailyfortune;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.R.attr.data;

public class FortuneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune);
        MyPreferences preferences = new MyPreferences(FortuneActivity.this);

        if (preferences.isFirstTime()){
            Toast.makeText(FortuneActivity.this, "Hi " + preferences.getUserName(), Toast.LENGTH_LONG).show();
            preferences.setOld(true);
        }else {
            Toast.makeText(FortuneActivity.this, "Welcome Back " + preferences.getUserName(), Toast.LENGTH_LONG).show();
        }

        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet())
            getFortuneOnline();
        else
            readFortuneFromFile();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void getFortuneOnline(){
        final TextView fortuneTxt = (TextView)findViewById(R.id.fortune);
        final TextView authorTxt = (TextView)findViewById(R.id.author);
        fortuneTxt.setText("Loading ...");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://quotes.rest/qod.json", (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                String fortune;
                String author = null;
                try {
                    JSONObject contents = response.getJSONObject("contents");
                    JSONArray quotes = contents.getJSONArray("quotes");
                    fortune = quotes.getJSONObject(0).getString("quote");
                    author = quotes.getJSONObject(0).getString("author");

                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
                    fortune = "Error";
                }fortuneTxt.setText(fortune);
                authorTxt.setText(author);

                writeToFile (fortune);
                writeToFile(author);
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d ("Response", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }


    private void writeToFile(String fortune) {
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Fortune.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }  catch (IOException e) {
            Log.e("Message; ", "File write Failed" + e.toString());
        }
    }


    private void readFortuneFromFile(){
        String fortune = " ";
        try {
            InputStream inputstream = openFileInput("Fortune.json");
            if(inputstream != null){
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                String revieveString = "";
                StringBuilder stringbuilder = new StringBuilder();
                Log.v("Message: ", "reading ... ");
                while((revieveString = bufferedreader.readLine()) !=null){
                    stringbuilder.append(revieveString);
                }
                inputstream.close();
                fortune = stringbuilder.toString();
            }
    } catch (FileNotFoundException e) {
            Log.e("Message: ", "File not Found: " + e.toString());

        } catch (IOException e) {
            Log.e("Message: ", "Can not read file" + e.toString());
        }
        TextView fortuneText = (TextView) findViewById(R.id.fortune);
        fortuneText.setText(fortune);
    }
}
