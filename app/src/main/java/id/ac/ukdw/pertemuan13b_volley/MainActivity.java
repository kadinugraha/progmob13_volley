package id.ac.ukdw.pertemuan13b_volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText txtChat;
    private Button btnChat;
    private TextView txtHasil;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtChat = (EditText)findViewById(R.id.txtChat);
        btnChat = (Button)findViewById(R.id.btnChat);
        txtHasil = (TextView)findViewById(R.id.txtHasil);
        queue = Volley.newRequestQueue(this);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        getRequest();
    }

    private void sendRequest(){
        StringRequest sr = new StringRequest(
                Request.Method.POST,
                "http://192.168.0.5/progmob_volley/insert.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map chat = new HashMap();
                chat.put("user","adi");
                chat.put("message", txtChat.getText().toString());
                return chat;
            }
        };
        queue.add(sr);

        getRequest();
    }

    private void getRequest(){
        StringRequest sr = new StringRequest(
                Request.Method.GET,
                "http://192.168.0.5/progmob_volley/user.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String tmpStr = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                tmpStr += jsonArray.getJSONObject(i).getString("user") +
                                        ": " + jsonArray.getJSONObject(i).getString("message") +
                                        "\n";
                            }
                            txtHasil.setText(tmpStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(sr);
    }
}
