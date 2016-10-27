package no.ntnu.asd.prosjektfil;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by martin on 27.10.2016.
 */

public class UserAdapter extends ArrayAdapter{

    public UserAdapter(Context context, List<User> users){
        super(context, 0, users);
    }

    // public View getView(int position)

}
