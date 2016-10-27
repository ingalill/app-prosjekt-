package no.ntnu.asd.prosjektfil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by martin on 27.10.2016.
 */

public class UserAdapter extends ArrayAdapter{

    public UserAdapter(Context context, List<User> users){
        super(context, 0, users);
    }

     public View getView(int position, View convertView, ViewGroup parent){
         User user = (User)getItem(position);

         if (convertView == null){
             convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_user, parent, false);
         }

         TextView userInfo = (TextView) convertView.findViewById(R.id.userInfo);
         return convertView;
     }

}
