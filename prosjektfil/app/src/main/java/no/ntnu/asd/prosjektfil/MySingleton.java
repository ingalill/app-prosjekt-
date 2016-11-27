package no.ntnu.asd.prosjektfil;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ingalill on 25/11/2016.
 */

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private MySingleton(Context context){

        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * If requestQueue is null, then create a new requestQueue
     * @return requestQueue
     */
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Gets the instance of MySingleton
     * @param context
     * @return mInstance of MySingleton.
     */
    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    /**
     * Add elements to the requestQueue
     * @param request
     * @param <T>
     */
    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
