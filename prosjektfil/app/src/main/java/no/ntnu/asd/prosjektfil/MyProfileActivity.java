package no.ntnu.asd.prosjektfil;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Inga
 */
public class MyProfileActivity extends AppCompatActivity {

    private Resources res;
    private String URL;
    private TextView TextFirstname;
    private TextView TextLastname;
    private TextView TextPhone;
    private TextView TextHome;
    private TextView TextInformation;

    // the database variable names.
    public static String KEY_FIRSTNAME;
    public static String KEY_LASTNAME;
    public static String KEY_HOME;
    public static String KEY_PHONE;
    public static String KEY_INFORMATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        TextFirstname = (TextView) findViewById(R.id.textViewFornavn3);
        TextLastname = (TextView) findViewById(R.id.textViewEtternavn3);
        TextHome = (TextView) findViewById(R.id.textViewAdress3);
        TextInformation = (TextView) findViewById(R.id.cv);
        TextPhone = (TextView) findViewById(R.id.textViewMobNr3);

        res = getResources();
        URL = res.getString(R.string.url);
        KEY_FIRSTNAME = res.getString(R.string.firstname);
        KEY_LASTNAME = res.getString(R.string.lastname);
        KEY_HOME = res.getString(R.string.home);
        KEY_PHONE = res.getString(R.string.phone);
        KEY_INFORMATION = res.getString(R.string.information);

        Intent intent = getIntent();
        final String intentFirstname = intent.getStringExtra("firstname");
        setTitle(intent.getStringExtra(intentFirstname));

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // try to get the information about the jobseeker and show it in the text fields.
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                String firstname = jsonResponse.getString(KEY_FIRSTNAME);
                                if (firstname.compareTo(intentFirstname) == 0) {
                                    String lastname = jsonResponse.getString(KEY_LASTNAME);
                                    String phone = jsonResponse.getString(KEY_PHONE);
                                    String information = jsonResponse.getString(KEY_INFORMATION);
                                    String home = jsonResponse.getString(KEY_HOME);

                                    TextFirstname.setText(firstname);
                                    TextLastname.setText(lastname);
                                    TextHome.setText(home);
                                    TextPhone.setText(phone);
                                    TextInformation.setText(information);
                                   // System.out.println("Hallo?" + firstname);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        MySingleton.getInstance(MyProfileActivity.this).addToRequestQueue(jsonArrayRequest);
    }
} // end of class
