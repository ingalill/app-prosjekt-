package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
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

/**
 * Created by inga lill bjølstad on 11/11/2016.
 */

public class SearchActivity extends AppCompatActivity {

    private ListView searchResults;
    private UserAdapter searchAdapter;
    private ArrayAdapter adapter;
    public static final String URL = "http://158.38.193.7:8080/RESTapiv3/webresources/userprofile/search/";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getRequestQueue();

        searchResults = (ListView) findViewById(R.id.searchResult);
        searchAdapter = new UserAdapter(this, new ArrayList<User>());
        searchResults.setAdapter(searchAdapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            //suggestions.saveRecentQuery(query, null);
            doSearch(query);
        }

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //får tak i navnet til kontakten man har valgt
                String firstname = searchAdapter.getItem(position).getFirstname();
                Intent i = new Intent(getApplicationContext(), SearchResult.class);
                i.putExtra("firstname", firstname);
                startActivity(i);
            }
        });
    } // end of on create

    /*
     * Search for a jobseeker.
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
                                user.setFirstname(jsonResponse.getString("firstname"));
                                if (user.getFirstname().compareTo(query) == 0) { // må endre user.getFirstname
                                    user.setFirstname(jsonResponse.getString("firstname"));
                                    user.setLastname(jsonResponse.getString("lastname"));
                                    user.setInformation(jsonResponse.getString("information"));
                                    user.setHome(jsonResponse.getString("home"));
                                    user.setPhone(jsonResponse.getString("phone"));
                                    result.add(user);
                                    // System.out.println("KOMMER JEG HIT???? " + user);
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

        requestQueue.add(jsonArrayRequest);
    }

    // present the results.
    private void doPresentResult(List<User> results) {
        searchAdapter.addAll(results);
        searchAdapter.notifyDataSetChanged();
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


