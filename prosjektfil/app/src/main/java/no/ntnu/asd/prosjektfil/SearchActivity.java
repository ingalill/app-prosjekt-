package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

/**
 * Created by inga lill bjølstad on 11/11/2016.
 */

public class SearchActivity extends AppCompatActivity {

    private ListView searchResults;
    private UserAdapter searchAdapter;
    private Resources res;
    private String URL;

    public static String KEY_FIRSTNAME;
    public static String KEY_LASTNAME;
    public static String KEY_HOME;
    public static String KEY_PHONE;
    public static String KEY_INFORMATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        res = getResources();
        URL = res.getString(R.string.serach_url);
        KEY_FIRSTNAME = res.getString(R.string.firstname);
        KEY_LASTNAME = res.getString(R.string.lastname);
        KEY_HOME = res.getString(R.string.home);
        KEY_PHONE = res.getString(R.string.phone);
        KEY_INFORMATION = res.getString(R.string.information);

        searchResults = (ListView) findViewById(R.id.searchResult);
        searchAdapter = new UserAdapter(this, new ArrayList<User>());
        searchResults.setAdapter(searchAdapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY); // suggestion is disabled.
            // SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            //suggestions.saveRecentQuery(query, null);
            doSearch(query);
        }

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the name of the contact we choosed.
                String firstname = searchAdapter.getItem(position).getFirstname();
                Intent i = new Intent(getApplicationContext(), SearchResult.class);
                i.putExtra("firstname", firstname);
                startActivity(i);
            }
        });
    } // end of on create

    /*
     * Search for a jobseeker.
     * Adds the jsonArrayRequest to RequestQueue.
     */
    private void doSearch(final String query) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL + query,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> result = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonResponse = response.getJSONObject(i);
                                User user = new User();
                                user.setFirstname(jsonResponse.getString(KEY_FIRSTNAME));
                                if (user.getFirstname().compareTo(query) == 0) {
                                    user.setFirstname(jsonResponse.getString(KEY_FIRSTNAME));
                                    user.setLastname(jsonResponse.getString(KEY_LASTNAME));
                                    user.setInformation(jsonResponse.getString(KEY_INFORMATION));
                                    user.setHome(jsonResponse.getString(KEY_HOME));
                                    user.setPhone(jsonResponse.getString(KEY_PHONE));
                                    result.add(user);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        doPresentResult(result);
                        System.out.println("Got: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Do not work.. and the error is: " + error);
            }
        }
        );
        MySingleton.getInstance(SearchActivity.this).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Present the result and adding them to the searchAdapter
     * @param results, list with user to add.
     */
    private void doPresentResult(List<User> results) {
        searchAdapter.addAll(results);
        searchAdapter.notifyDataSetChanged();
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


