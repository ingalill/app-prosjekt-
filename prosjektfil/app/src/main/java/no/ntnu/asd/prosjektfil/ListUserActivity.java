package no.ntnu.asd.prosjektfil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

     // inn her skal det var get user
    ListView userList;          // Definerer listview
    //ArrayList<User> users;      // Definerer arraylist
    UserAdapter userAdapter;    // Definerer adapter
    TextView textViewTest;
    Button buttonClick;
                                       //158.38.193.12 // 10.0.0.31
    public static final String url =  "http://158.38.193.12:8080/RESTapiv3";  //"http://10.0.0.31:8080/RESTapiv2/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        textViewTest = (TextView)findViewById(R.id.textViewTest);
        buttonClick = (Button)findViewById(R.id.buttonClick);
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(ListUserActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                textViewTest.setText(response);
                               // requestQueue.stop();
                                Log.d("test", "virke det???");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                textViewTest.setText("Something went wrong");
                               // requestQueue.stop();
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });


        //***********TEST****************
        /*List users = Arrays.asList(
              new User("Martin","Blom","Ålesund","Hei!"),
                new User("Ola","Normann","Ålesund","Hei!"),
                new User("Kari","Normann","Ålesund","Hei!")
        );*/
        //***********TEST****************

        // Instansiering
        //users = new ArrayList<>();                          // arraylist som holder users til bruk i listen
      //  userAdapter = new UserAdapter(getApplicationContext(),users);          // adapter til ListView'en userList
        //userList = (ListView) findViewById(R.id.userList);  // userList mot ListView'en userList
        //userList.setAdapter(userAdapter);                   // setter adapter opp mot ListView
    }

    JsonObjectRequest req = new JsonObjectRequest(url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.e("Error: ", error.getMessage());
        }
    });

}
