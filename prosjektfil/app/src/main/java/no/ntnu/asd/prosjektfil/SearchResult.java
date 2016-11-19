package no.ntnu.asd.prosjektfil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
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

import java.util.List;

/**
 * Created by ingalill on 17/11/2016.
 */

public class SearchResult extends AppCompatActivity{

    private RequestQueue requestQueue;
    public static final String URL = "http://158.38.193.7:8080/RESTapiv3/webresources/userprofile/";
    private TextView TextFirstname;
    private TextView TextLastname;
    private TextView TextPhone;
    private TextView TextHome;
    private TextView TextInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        final String intentFirstname = intent.getStringExtra("firstname");
        setTitle(intent.getStringExtra(intentFirstname));

        TextFirstname = (TextView) findViewById(R.id.textViewFornavn3);
        TextLastname = (TextView) findViewById(R.id.textViewEtternavn3);
        TextHome = (TextView) findViewById(R.id.textViewAdress3);
        TextInformation = (TextView) findViewById(R.id.cv);
        TextPhone = (TextView) findViewById(R.id.textViewMobNr3);

        getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                String firstname = jsonResponse.getString("firstname");
                                if (firstname.compareTo(intentFirstname) == 0) {

                                    //firstname = jsonResponse.getString("firstname");
                                    String lastname = jsonResponse.getString("lastname");
                                    String phone = jsonResponse.getString("phone");
                                    String information = jsonResponse.getString("information");
                                    String home = jsonResponse.getString("home");

                                    TextFirstname.setText(firstname);
                                    TextLastname.setText(lastname);
                                    TextHome.setText(home);
                                    TextPhone.setText(phone);
                                    TextInformation.setText(information);
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
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * If there is no requestQueue then its create a new reqeustQueue
     *
     * @return requestQueue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }


} // end of class


