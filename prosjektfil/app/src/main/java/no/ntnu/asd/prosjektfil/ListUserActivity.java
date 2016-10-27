package no.ntnu.asd.prosjektfil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    ListView userList;          // Definerer listview
    ArrayList<User> users;      // Definerer arraylist
    UserAdapter userAdapter;    // Definerer adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        // Instansiering
        userList = (ListView) findViewById(R.id.userList);  // userList mot ListView'en userList
        users = new ArrayList<>();                          // arraylist som holder users til bruk i listen
        userAdapter = new UserAdapter(this,users);          // adapter til ListView'en userList
        userList.setAdapter(userAdapter);
    }


}
