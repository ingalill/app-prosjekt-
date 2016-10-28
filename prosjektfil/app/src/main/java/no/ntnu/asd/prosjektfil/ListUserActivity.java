package no.ntnu.asd.prosjektfil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

    ListView userList;          // Definerer listview
    ArrayList<User> users;      // Definerer arraylist
    UserAdapter userAdapter;    // Definerer adapter
    User test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        //***********TEST****************
        //List users = Arrays.asList(
        //      new User("Martin","Blom","Ålesund","Hei!")
        //);
        //***********TEST****************

        // Instansiering
        users = new ArrayList<>();                          // arraylist som holder users til bruk i listen
        userAdapter = new UserAdapter(this,users);          // adapter til ListView'en userList
        userList = (ListView) findViewById(R.id.userList);  // userList mot ListView'en userList
        userList.setAdapter(userAdapter);                   // setter adapter opp mot ListView
    }


}
