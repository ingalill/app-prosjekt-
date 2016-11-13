package no.ntnu.asd.prosjektfil;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ingalill on 11/11/2016.
 */

public class SearchActivity extends AppCompatActivity {

    private ListView searchResults;
    private UserAdapter adapter;
    private List<User> users = new ArrayList<>();

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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


}


