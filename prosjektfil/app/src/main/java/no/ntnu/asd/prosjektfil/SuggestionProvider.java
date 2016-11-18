package no.ntnu.asd.prosjektfil;

import android.content.SearchRecentSuggestionsProvider;

/**
 * MÅ GJØRES OM!!
 * Created by ingalill on 13/11/2016.
 */

public class SuggestionProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "no.ntnu.asd.prosjektfil";
    // Configuers database to record recent queries
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {

        setupSuggestions(AUTHORITY,MODE);
    }
}