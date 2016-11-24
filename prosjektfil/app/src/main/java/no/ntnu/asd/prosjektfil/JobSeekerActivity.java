package no.ntnu.asd.prosjektfil;

import android.content.Intent;
import android.content.res.Resources;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JobSeekerActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private Button loadImageButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private RequestQueue requestQueue;
    private Resources res;
    private String URL;

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
    private UserAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);
        adapter = new UserAdapter(this, users);

        res = getResources();
        URL = res.getString(R.string.url);

        profilePicture = (ImageView) findViewById(R.id.smallPreview);
        loadImageButton = (Button) findViewById(R.id.buttonProfilepicture);
        createUserButton = (Button) findViewById(R.id.buttonCreateUser);
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
                //TODO: Her må galleri og kamerafunksjonen samkjøres på et vis
                //openGallery();
                Intent intent = new Intent(JobSeekerActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == createUserButton) {
                    registerJobSeeker();
                }
            }
        });

    } // end of onCreate

    private void registerJobSeeker() {
        final String firstname = EditTextFirstname.getText().toString().trim();
        final String lastname = EditTextLastname.getText().toString().trim();
        final String home = EditTextHome.getText().toString().trim();
        final String phone = EditTextPhone.getText().toString().trim();
        final String information = EditTextInformation.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        try {
            View.generateViewId(); // tror ikke denne trengst

            jsonObject.put(KEY_FIRSTNAME, firstname);
            jsonObject.put(KEY_LASTNAME, lastname);
            jsonObject.put(KEY_HOME, home);
            jsonObject.put(KEY_PHONE, phone);
            jsonObject.put(KEY_INFORMATION, information);
            // put password
            Log.d("test", jsonObject.toString());
            user = new User(firstname,lastname,home,phone,information);

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
            });

        requestQueue.add(jsonObjectRequest);
        adapter.add(user);
        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("firstname", firstname);
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
