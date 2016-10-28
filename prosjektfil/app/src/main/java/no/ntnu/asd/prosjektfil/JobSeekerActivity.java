package no.ntnu.asd.prosjektfil;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    public static final String url ="http://158.38.193.13:8080/RESTapiv2/webresources/restapi.userprofile";
    RequestQueue requestQueue;
    private ArrayList<User> users;
    private UserAdapter adapter;
    private Button createUserButton;
    //private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);
        //user = new User();

        profilePicture = (ImageView) findViewById(R.id.imageView);
        loadImageButton = (Button) findViewById(R.id.buttonProfilepicture);
        createUserButton = (Button) findViewById(R.id.button3);

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
                                String information = jsonObject.getString("information");
                                User newUser = new User(firstname, lastname, home, information);
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
        requestQueue.add(jsonArrayRequest);
        // ^^^^^^


        // det over skal fungere


        createUserButton.setOnClickListener( new View.OnClickListener() {
        @Override
                public void onClick(View v){
           User us =  new User();
                    System.out.println("Legg til ny bruker her! ");
                    User user = new User(us.getID(),us.getFirstname(), us.getLastname(), us.getHome(),us.getInformation());

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", user.getID());
                        jsonObject.put("firstname", user.getFirstname());
                        jsonObject.put("lastname", user.getLastname() );
                        jsonObject.put("home", user.getHome());
                        jsonObject.put("information", user.getInformation());
                        Log.d("test", "put json");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("resp", response.toString());
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("test", "Error test");
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                    adapter.add(user);
                }
            }


        );

    } // end of onCreate

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
