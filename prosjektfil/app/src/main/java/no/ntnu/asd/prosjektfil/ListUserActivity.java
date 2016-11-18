package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

    private ListView userList;          // Definerer listview
    private UserAdapter userAdapter;    // Definerer adapter
    private List<User> users = new ArrayList<>();
    private User user;
    //158.38.193.12 // 10.0.0.31
    public static final String URL = "http://158.38.193.13:8080/RESTapiv3/webresources/userprofile";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        user = new User();

        userList = (ListView) findViewById(R.id.userList);
        userAdapter = new UserAdapter(this, users);
        userList.setAdapter(userAdapter);
        setTitle("Jobb søkere");

        getRequestQueue();
        getUser();

    } // end of on create

    public void getUser() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                User user = new User();
                                user.setFirstname(jsonResponse.getString("firstname"));
                                user.setLastname(jsonResponse.getString("lastname"));
                                user.setInformation(jsonResponse.getString("information"));
                                user.setHome(jsonResponse.getString("home"));
                                user.setPhone(jsonResponse.getString("phone"));

                                users.add(user);
                                // System.out.println("Funker det: " + user.getFirstname());
                            }
                            userAdapter.notifyDataSetChanged();
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
        listViewClicker();
    }

    /**
     * Not in use at the moment.
     */
    public void listViewClicker() {
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);

                String firstname = userAdapter.getItem(position).getFirstname();
                intent.putExtra("firstname", firstname);
                startActivity(intent);
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchManager sMngr = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = sMngr.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

} // end of class



