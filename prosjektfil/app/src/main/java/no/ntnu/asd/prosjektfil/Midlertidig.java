package no.ntnu.asd.prosjektfil;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ingalill on 27/10/2016.
 */

public class Midlertidig {

  /*  UserAdapter adapter;

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String messageText = jsonObject.getString("text"); // MÅ MATCHE DB!!
                            int conversationid = jsonObject.getInt("conversationId");
                            String contact = jsonObject.getString("senderId");
                            String timestamp = jsonObject.getString("created");
                            User newUser = new User(messageText, contact, conversationid, timestamp);
                            messages.add(newUser);
                            Log.d("test", "add message");
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse (VolleyError error){
                    Log.e("volley,",error.toString());
                }
            });
    requestQueue.add(jsonArrayRequest); */

    final EditText messageText = (EditText) findViewById(R.id.messageText);

    messageText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
            if (actionid == EditorInfo.IME_ACTION_SEND) {
                String newMessage = messageText.getText().toString();
                if (newMessage.equals("")) {
                    return false;
                } else {
                    User user = new User(newMessage, contactName, conversationId);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("text", newMessage);
                        jsonObject.put("senderId", contactName);
                        jsonObject.put("conversationId", conversationId);
                        jsonObject.put("timeStamp", message.getTimestamp());
                        Log.d("test", "put json");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("resp", response.toString());
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("test", "Error test");
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                    adapter.add(user);
                    messageText.setText("");
                    return true;
                }
            }
            return false;

        }
    });
}
