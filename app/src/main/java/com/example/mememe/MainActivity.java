package com.example.mememe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
       Button sharebtn;
       ImageView imageview;
       ProgressBar progress;
       Button nextbtn;
       String currentImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          sharebtn = findViewById(R.id.sharebtn);
          nextbtn = findViewById(R.id.nextbtn);
          progress = findViewById(R.id.progress);
        imageview  = findViewById(R.id.imageview);
          loadmeme();


    }
     public void loadmeme(){
          progress.setVisibility(View.VISIBLE);
         String url = "https://meme-api.herokuapp.com/gimme";

         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                 (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                     @Override
                     public void onResponse(JSONObject response) {

                         try {
                             String url = response.getString("url");


                             Glide.with(MainActivity.this).load(url)
                                     .listener(new RequestListener<Drawable>() {
                                         @Override
                                         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                             progress.setVisibility(View.INVISIBLE);
                                             return false;

                                         }

                                         @Override
                                         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                             progress.setVisibility(View.INVISIBLE);
                                             return false;

                                         }


                                     })



                                     .into(imageview);


                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }
                 }, new Response.ErrorListener() {

                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(MainActivity.this, "Something Went Wrong! Check Your Internet Connection !", Toast.LENGTH_LONG).show();

                     }
                 });


         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


     }





    public void sharebtn(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text = "Will be available soooon..";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



    }

    public void nextbtn(View view) {
        loadmeme();
    }
}