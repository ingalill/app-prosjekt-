package no.ntnu.asd.prosjektfil;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

     // inn her skal det var get user
    ListView userList;          // Definerer listview
    UserAdapter userAdapter;    // Definerer adapter

                                       //158.38.193.12 // 10.0.0.31
    public static final String url =  "http://158.38.193.9:8080/RESTapiv3/webresources/userprofile";
    private TextView TextFirstname;
    private TextView TextLastname;
    private TextView TextPhone;
    private TextView TextHome;
    private TextView TextInformation;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        TextFirstname = (TextView) findViewById(R.id.firstname);
        TextLastname = (TextView) findViewById(R.id.lastname);
        TextHome = (TextView) findViewById(R.id.home);
        TextInformation = (TextView)findViewById(R.id.information);
        TextPhone = (TextView) findViewById(R.id.phone);

        getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(/*Request.Method.GET,*/ url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                String firstname = jsonResponse.getString("firstname");
                                String lastname = jsonResponse.getString("lastname");
                                String phone = jsonResponse.getString("phone");
                                String information = jsonResponse.getString("information");
                                String home = jsonResponse.getString("home");
                                System.out.println("Inni json Array request, fungerer dette mon tro?");

                                TextFirstname.setText(firstname);
                                TextLastname.setText(lastname);
                                TextHome.setText(home);
                                TextPhone.setText(phone);
                                TextInformation.setText(information);
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse (VolleyError error){
                    }
                }
        );
        requestQueue.add(jsonArrayRequest); /*

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d("resp", response.toString());

                        try {
                            Toast.makeText(ListUserActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            JSONObject obj = response.getJSONObject("hei"); // annen verdi istendenfor hei
                            String firstname = obj.getString("firstname");
                            String lastname = obj.getString("lastname");
                            String phone = obj.getString("phone");
                            String information = obj.getString("information");
                            String home = obj.getString("home");
                            System.out.println("HALLASOJADSLASLSASAKLJ");

                            TextFirstname.setText(firstname);
                            TextLastname.setText(lastname);
                            TextHome.setText(home);
                            TextPhone.setText(phone);
                            TextInformation.setText(information);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "Error test i blir gal", error);
                Toast.makeText(ListUserActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);*/
    } // end of on create


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
