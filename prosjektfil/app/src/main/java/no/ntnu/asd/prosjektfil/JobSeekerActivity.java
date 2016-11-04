package no.ntnu.asd.prosjektfil;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class JobSeekerActivity extends AppCompatActivity {

     // code 400 --> dårlig respons for http:// lalal
    // code 500 --> server error
    ImageView profilePicture;
    Button loadImageButton;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    RequestQueue requestQueue;
    // husk å bytte ip adresse til din egen. //10.0.0.31 //158.38.193.12
    public static final String URL = "http://158.38.193.12:8080/RESTapiv3/webresources/userprofile/";  //"http://10.0.0.31:8080/RESTapiv2/webresources/userprofile";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_HOME = "home";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_INFORMATION = "information";

    private ArrayList<User> users = new ArrayList<User>();
    private Button createUserButton;
    private EditText EditTextFirstname;
    private EditText EditTextLastname;
    private EditText EditTextPhone;
    private EditText EditTextHome;
    private EditText EditTextInformation;
    int fID = 0;
    private UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);
        adapter = new UserAdapter(this, users);

        profilePicture = (ImageView) findViewById(R.id.imageView);
        loadImageButton = (Button) findViewById(R.id.buttonProfilepicture);
        createUserButton = (Button) findViewById(R.id.button3);
        //input fields
        EditTextFirstname = (EditText) findViewById(R.id.firstname);
        EditTextLastname = (EditText) findViewById(R.id.lastname);
        EditTextHome = (EditText) findViewById(R.id.address);
        EditTextPhone = (EditText) findViewById(R.id.phone);
        EditTextInformation = (EditText) findViewById(R.id.information);
        getRequestQueue();

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == createUserButton) {
                    registerJobSeeker();
                }
            }
        }
        );

    } // end of onCreate

    /**
     * Generates a unused id.
     * @return
     */
    public int findUnusedId() {
        while( findViewById(++fID) != null );
        return fID;
    }

    private void registerJobSeeker() {
        final String firstname = EditTextFirstname.getText().toString().trim();
        final String lastname = EditTextLastname.getText().toString().trim();
        final String home = EditTextHome.getText().toString().trim();
        final String phone = EditTextPhone.getText().toString().trim();
        final String information = EditTextInformation.getText().toString().trim();

        User user = new User();
        JSONObject jsonObject = new JSONObject();
        try {
            View.generateViewId();

            jsonObject.put("id", findUnusedId());
            jsonObject.put(KEY_FIRSTNAME, firstname);
            jsonObject.put(KEY_LASTNAME, lastname);
            jsonObject.put(KEY_HOME, home);
            jsonObject.put(KEY_PHONE, phone);
            jsonObject.put(KEY_INFORMATION, information);
            Log.d("test", "put json HALLLOOOOO");
            Log.d("test","FUNKER ID??????? " + findUnusedId());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("resp", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "Error test i blir gal", error);
                Toast.makeText(JobSeekerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }); /*{ //Sjekk hjemme om det fungerer. 
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };*/

        requestQueue.add(jsonObjectRequest);
        adapter.add(user);
        Intent intent = new Intent(getApplicationContext(), ListUserActivity.class);
        startActivity(intent);
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

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }
}
