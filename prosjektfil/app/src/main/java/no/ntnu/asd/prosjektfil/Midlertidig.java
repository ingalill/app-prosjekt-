package no.ntnu.asd.prosjektfil;

public class Midlertidig{

        }
/*
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobSeekerActivity extends AppCompatActivity {

    ImageView profilePicture;
    Button loadImageButton;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    // husk å bytte ip adresse til din egen.
    public static final String url ="http://10.0.0.31:8080/RESTapiv2/webresources/userprofile";
    RequestQueue requestQueue;
    private ArrayList<User> users  = new ArrayList<User>();
    private Button createUserButton;
    private EditText EditTextFirstname;
    private EditText EditTextLastname;
    private EditText EditTextPhone;
    private EditText EditTextHome;
    private EditText EditTextInformation;
    private UserAdapter adapter;
    public static final String REQUEST_TAG = "Jobseeker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);

        profilePicture = (ImageView) findViewById(R.id.imageView);
        loadImageButton = (Button) findViewById(R.id.buttonProfilepicture);
        createUserButton = (Button) findViewById(R.id.button3);
        //input fields
        EditTextFirstname = (EditText) findViewById(R.id.firstname);
        EditTextLastname = (EditText)findViewById(R.id.lastname);
        EditTextHome = (EditText)findViewById(R.id.home);
        EditTextPhone = (EditText)findViewById(R.id.phone);
        EditTextInformation = (EditText)findViewById(R.id.information);

        adapter = new UserAdapter(this,users);

        getRequestQueue();

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Get contact with the db
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String firstname = jsonObject.getString("firstname"); // MÅ MATCHE DB!!
                                String lastname = jsonObject.getString("lastname");
                                String home = jsonObject.getString("home");
                                String phone = jsonObject.getString("phone");
                                String information = jsonObject.getString("information");
                                User newUser = new User(firstname, lastname, home,phone, information);
                                users.add(newUser);
                                Log.d("tester å: ", "Legg til arbeidstaker");
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse (VolleyError error){
                        Log.e("volley,",error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest); //jsonArrayRequest --> to receive JSON array from the server.


        // feil her en plass
        createUserButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    User us = new User();
                                                    //System.out.println("Legg til ny bruker her! ");
                                                    User user = new User(us.getID(), us.getFirstname(), us.getLastname(), us.getHome(), us.getPhone(), us.getInformation());

                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("id", user.getID());
                                                        jsonObject.put("firstname", user.getFirstname());
                                                        jsonObject.put("lastname", user.getLastname());
                                                        jsonObject.put("home", user.getHome());
                                                        jsonObject.put("phone", user.getPhone());
                                                        jsonObject.put("information", user.getInformation());
                                                        Log.d("test", "put json");

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } // send and recive json object from the server
                                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.d("resp", response.toString());
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.d("test", "Error test",error);
                                                        }
                                                    });
                                                    requestQueue.add(jsonObjectRequest);
                                                    adapter.add(user);
                                                    Intent intent = new Intent(getApplicationContext(), ListUserActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
        );

    } // end of onCreate

    private void registerJobSeeker(){
        final String firstname = EditTextFirstname.getText().toString().trim();
        final String lastname = EditTextLastname.getText().toString().trim();
        final String home = EditTextHome.getText().toString().trim();
        final String phone = EditTextPhone.getText().toString().trim();
        final String information = EditTextInformation.getText().toString().trim();
    }
    /**
     * If there is no requestQueue then its create a new reqeustQueue
     * @return requestQueue
     */
    /*
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG);
        }
    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }
}
*/