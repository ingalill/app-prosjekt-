package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
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
    public static final String URL =  "http://10.0.0.31:8080/RESTapiv3/webresources/userprofile";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getRequestQueue();

        searchResults = (ListView)findViewById(R.id.searchResult);
        adapter = new UserAdapter(this,users);
        searchResults.setAdapter(adapter);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doSearch(query);
        }
    }


      // search for a message
    private void doSearch(String query) {

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
                                //System.out.println("Funker det: " + user.getFirstname());


                              /*  if(query == user.getFirstname()) {
                                    doPresentResult(query);

                                } */
                            }
                            adapter.notifyDataSetChanged();
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

      /*  query = query.toLowerCase();

        List<List<Message>> result = new ArrayList<>();
        for(List<Message> item : DomainSingleton.getSingleton(this).getData()) {
            if(item.toString().toLowerCase().contains(query)) {
                result.add(item);
            }
        }
        doPresentResult(result); */
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


}


