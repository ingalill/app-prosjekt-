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
    private UserAdapter adapter;
    private List<User> users = new ArrayList<>();
    public static final String URL = "http://158.38.193.13:8080/RESTapiv3/webresources/userprofile/search/";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent()); // test
        getRequestQueue();

        searchResults = (ListView) findViewById(R.id.searchResult);
        adapter = new UserAdapter(this, users);
        searchResults.setAdapter(adapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doSearch(query);
        }

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //får tak i navnet til kontakten man har valgt
                String firstname = "";//= adapter.getItem(position);

                Intent i = new Intent(getApplicationContext(), SearchResult.class);
                i.putExtra("firstname", firstname);
                //i.putExtra(CONVERSATION_ID, conversationId);
                startActivity(i);
            }
        });
    } // end of on create

    public void onNewIntent(Intent intent) { //test
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) { // test
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    // search for a message
    private void doSearch(String query) {
        // User user = new User();
        query = query.toLowerCase();
        List<User> result = new ArrayList<>();
        for (User s : users) {
            if (s.getFirstname().toLowerCase().compareTo(query) == 0) {
                result.add(s);
                System.out.println("hva inneholder " + s);
                adapter.notifyDataSetChanged();
            }

        }
        doPresentResult(result);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL + query,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Got: " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }}
        );
        requestQueue.add(jsonArrayRequest);
    }

    // present the results. Her skal List vekk. DomainSingleton skal vekk og byttes med db.
    private void doPresentResult(List<User> results) {
        adapter.addAll(results);
        adapter.notifyDataSetChanged();
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

}


