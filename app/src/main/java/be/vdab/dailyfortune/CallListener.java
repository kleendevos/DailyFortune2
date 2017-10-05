package be.vdab.dailyfortune;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vdabcursist on 04/10/2017.
 */

public class CallListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       try {
           String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
           if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
               Toast.makeText(context,"Phone is ringing", Toast.LENGTH_LONG).show();
           }
       } catch (Exception e) {
           Log.e("CallListener","Error Handling Phonecall",e);
       }

    }
}
