package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import com.android.volley.Request;
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
    private Resources res;
    private String URL;

    // the database variable names.
    public static String KEY_FIRSTNAME;
    public static String KEY_LASTNAME;
    public static String KEY_HOME;
    public static String KEY_PHONE;
    public static String KEY_INFORMATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        user = new User();

        userList = (ListView) findViewById(R.id.userList);
        userAdapter = new UserAdapter(this, users);
        userList.setAdapter(userAdapter);
        setTitle("Jobb søkere");
        res = getResources();
        URL = res.getString(R.string.url);
        KEY_FIRSTNAME = res.getString(R.string.firstname);
        KEY_LASTNAME = res.getString(R.string.lastname);
        KEY_HOME = res.getString(R.string.home);
        KEY_PHONE = res.getString(R.string.phone);
        KEY_INFORMATION = res.getString(R.string.information);

        getUser();

    } // end of on create

    /**
     * Get users from the database using a volley request.
     * Call the clickListner at the end of the method to start a new activity.
     */
    public void getUser() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                User user = new User();
                                user.setFirstname(jsonResponse.getString(KEY_FIRSTNAME));
                                user.setLastname(jsonResponse.getString(KEY_LASTNAME));
                                user.setInformation(jsonResponse.getString(KEY_INFORMATION));
                                user.setHome(jsonResponse.getString(KEY_HOME));
                                user.setPhone(jsonResponse.getString(KEY_PHONE));

                                users.add(user);
                            }
                            //Notifies that the underlying data has been changed, view with the data set should refresh itself.
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
        MySingleton.getInstance(ListUserActivity.this).addToRequestQueue(jsonArrayRequest);
        listViewClicker();
    }

    /**
     * Start the activity MyProfileActivity,
     * send with an intent with the firstname of the jobseeker.
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
     * To spescify the optins menu for an activity
     * Menu layout has a search item.
     * @param menu
     * @return
     */
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

    /**
     * React to the user tapping/selecting an options menu item.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

} // end of class



