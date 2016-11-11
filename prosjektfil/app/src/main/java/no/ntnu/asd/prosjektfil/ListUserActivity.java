package no.ntnu.asd.prosjektfil;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
 * Created by ingalill
 */
public class ListUserActivity extends AppCompatActivity {

     // inn her skal det var get user
    private ListView userList;          // Definerer listview
    private UserAdapter userAdapter;    // Definerer adapter
    private List<User> users = new ArrayList<User>();
    private User user;
                                       //158.38.193.12 // 10.0.0.31
    public static final String URL =  "http://158.38.193.14:8080/RESTapiv3/webresources/userprofile";
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        user = new User();

        userList = (ListView)findViewById(R.id.userList);
        userAdapter = new UserAdapter(this,users);
        userList.setAdapter(userAdapter);
        setTitle("Jobb søkere");

        getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                User user = new User();
                                user.setFirstname(jsonResponse.getString("firstname"));

                               // if(jsonResponse.has("lastname"))
                                user.setLastname(jsonResponse.getString("lastname"));
                                user.setInformation(jsonResponse.getString("information"));
                                user.setHome(jsonResponse.getString("home"));
                                user.setPhone(jsonResponse.getString("phone"));

                                users.add(user);

                                System.out.println("Funker det: " + user.getFirstname());
                            }
                            userAdapter.notifyDataSetChanged();
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
        requestQueue.add(jsonArrayRequest);
    } // end of on create


    /**
     * Not in use at the moment.
     */
    public void listViewClicker(){
    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);

            String firstname = userAdapter.getItem(position).getFirstname();
            intent.putExtra("contactName", firstname);
            startActivity(intent);
        }
    });

}
    /*
    on click functions skal inn.
    Intent intent  = new Intent(getApplicationContext(), xx.class);
    String firstname = userAdapter.getItem(position).getName();
    intent.putExtra("firstname",firstname);
    startActivity(intent);

    inn i xx.class
    contactName = intent.getStringExtra("firstname");
     */


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

   /* private TextView TextFirstname;
    private TextView TextLastname;
    private TextView TextPhone;
    private TextView TextHome;
    private TextView TextInformation; */

 /* TextFirstname = (TextView) findViewById(R.id.firstname);
        TextL***astname = (TextView) findViewById(R.id.lastname);
        TextHome = (TextView) findViewById(R.id.home);
        TextInformation = (TextView)findViewById(R.id.information);
        TextPhone = (TextView) findViewById(R.id.phone); */
/*

try...
was inside for()...
  /*String firstname = jsonResponse.getString("firstname");
                                String lastname = jsonResponse.getString("lastname");
                                String phone = jsonResponse.getString("phone");
                                String information = jsonResponse.getString("information");
                                String home = jsonResponse.getString("home");
                                System.out.println("Inni json Array request, fungerer dette mon tro?"); */

                            /*  TextFirstname.setText(firstname);
                                TextLastname.setText(lastname);
                                TextHome.setText(home);
                                TextPhone.setText(phone);
                                TextInformation.setText(information); */

