package ws.design.com.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import customfonts.MyTextView;
import ws.design.com.R;

public class signin extends AppCompatActivity {
    TextView user,pass;
    MyTextView signin;
    Typeface fonts1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        user = (TextView)findViewById(R.id.email);
        pass = (TextView)findViewById(R.id.password);
        signin = (MyTextView)findViewById(R.id.signin1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;
                if (connected) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config1.LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    boolean connected = false;
                                    System.out.println("response is" + response);
                                    if (!response.trim().equals("unsuccessful")) {
                                        //Creating a shared preference//Starting profile activity
                                        Toast.makeText(signin.this, response.toString(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(signin.this, MainActivity.class);
                                        intent.putExtra("user",response);
                                        startActivity(intent);
                                        Toast.makeText(signin.this,response.toString(),Toast.LENGTH_SHORT).show();
                                    } else {
                                        //If the server response is not success
                                        //Displaying an error message on toast
                                        Toast.makeText(signin.this, response.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {

                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            //params.put(Config1.KEY_ROLLNO, user1.getText().toString());
                            //params.put(Config1.KEY_PASSWORD, pass1.getText().toString());
                            params.put("admin", user.getText().toString());
                            params.put("password", pass.getText().toString());

                            //returning parameter
                            return params;
                        }


                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(signin.this);
                    requestQueue.add(stringRequest);
                }
                else
                    Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_LONG).show();
            }
        });

        fonts1 =  Typeface.createFromAsset(signin.this.getAssets(),
                "fonts/Lato-Regular.ttf");




        TextView t4 =(TextView)findViewById(R.id.create);
        t4.setTypeface(fonts1);



    }
}
class Config1
{
    static String LOGIN_URL="";
}