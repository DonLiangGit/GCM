package gcm.play.android.samples.com.gcmquickstart;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wajih on 6/14/2015.
 */
public class SignUp extends Activity {
    EditText name, email;
    Button button;
    String sname, semail;
    String token = null;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupform);
        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        button = (Button) findViewById(R.id.bRegister);
        Bundle extras = getIntent().getExtras();
        token = (String) extras.get("token");







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                sname = name.getText().toString();
                semail = email.getText().toString();
                Thread mythread = new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        HttpClient httpclient = new DefaultHttpClient();
                        // specify the URL you want to post to
                        HttpPost httppost = new HttpPost("http://droidmonitor.comxa.com/index.php");
                        try {
                            //changes
                            // create a list to store HTTP variables and their values
                            List nameValuePairs = new ArrayList();
                            // add an HTTP variable and value pair
                            nameValuePairs.add(new BasicNameValuePair("name",sname));
                            nameValuePairs.add(new BasicNameValuePair("email",semail));
                            nameValuePairs.add(new BasicNameValuePair("token",token));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            // send the variable and value, in other words post, to the URL
                            HttpResponse response = httpclient.execute(httppost);
                            Log.d("Status","Sent!..");
                        } catch (ClientProtocolException e) {
                            // process execption
                            e.printStackTrace();
                        } catch (IOException e) {
                            // process execption
                            e.printStackTrace();
                        }
                        MyGcmListenerService mylistener = new MyGcmListenerService();

                    }
                });
                mythread.start();
//
//                StuffClass myobject = new StuffClass(sname,semail,token);

            }
        });


    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }








}
