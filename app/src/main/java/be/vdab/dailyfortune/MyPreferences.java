package be.vdab.dailyfortune;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vdabcursist on 04/10/2017.
 */

public class MyPreferences {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "DailyFortune";
    private static final String IS_FIRSTTIME = "IsFistTime";
    public static final String userName = "name";

    public MyPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public boolean isFirstTime (){
        return preferences.getBoolean(IS_FIRSTTIME, true);
    }

    public void setOld (boolean b){
        if(b){
            editor.putBoolean(IS_FIRSTTIME, false);
            editor.commit();
        }
    }

    public String getUserName() {
        return preferences.getString(userName, "");
    }


    public  void setUserName(String name) {
       editor.putString(userName, name);
        editor.commit();
    }
}
