package no.ntnu.asd.prosjektfil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by martin on 27.10.2016.
 */

public class UserAdapter extends ArrayAdapter<User>{

    public UserAdapter(Context context, List<User> users){
        super(context, 0, users);
    }

    @NonNull
    @Override
     public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row, parent, false);
        }

        TextView userInfo = (TextView) convertView.findViewById(R.id.userInfo);
        userInfo.setText(user.getFirstname() + " " + user.getLastname());

        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        tvHome.setText(user.getHome());

        TextView tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
        tvInfo.setText(user.getInformation());

        ImageView userImage = (ImageView)convertView.findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.thumb);

        return convertView;
     }

}
