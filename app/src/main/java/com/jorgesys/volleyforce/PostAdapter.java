package com.jorgesys.volleyforce;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends ArrayAdapter {

    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    //You can create a feed using http://www.json-generator.com/ of my friend Vazha Omanashvili.
    //Example:
    /*{
        "articles": [{
        "id": "1080024",
                "imageUrl": "http://img.gruporeforma.com/ImagenesIpad/4/467/3466469.jpg",
                "title": "In Poland’s Crooked Forest, a Mystery With No Straight Answer",
                "category": "SCIENCE",
                "info": "In Poland’s Krzywy Las, or Crooked Forest, the pine trees look like potbellied stick figures. On some 400 trees, the trunks buckle out 90 degrees, creating bark-covered bellies that drag just above the earth, oddly, all pointing in the same direction — north.",
                "date": "MARCH 31, 2017"
    }, {
        "id": "1080024",
                "imageUrl": "https://static01.nyt.com/images/2017/04/01/world/01Brexit/01Brexit-videoLarge.jpg",
                "title": "On ‘Brexit,’ It’s Divorce First, Trade Talks Later, E.U. Tells U.K.",
                "category": "EUROPE",
                "info": "BRUSSELS — Britain must agree to pay its bills and to protect millions of Europeans living in Britain before reaching a new trading relationship with the European Union, Donald Tusk, the president of the European Council, said on Friday.",
                "date": "MARCH 31, 2017"
    }, {
        "id": "1080024",
                "imageUrl": "https://static01.nyt.com/images/2017/03/28/world/28Coin/28Coin-master768.jpg",
                "title": "Thieves Take a Chunk of Change, All 221 Pounds of It, From a Berlin Museum",
                "category": "EUROPE",
                "info": "BERLIN — You could never palm it, flip it or plunk it into a vending machine. But apparently it can be pinched: One of the world’s largest gold coins, a 221-pound Canadian monster called the Big Maple Leaf, was stolen overnight from the Bode Museum in Berlin, the police said on Monday.",
                "date": "MARCH 27, 2017"
    }]
    }*/

    private static final String URL_FEED = "http://www.json-generator.com/api/json/get/cwioLSYdWq?indent=2";
    private static final String TAG = "PostAdapter";

    List<Post> items;

    public PostAdapter(final Context context) {
        super(context,0);

        // Create a Queue of requests
        requestQueue = Volley.newRequestQueue(context);

        // get the JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_FEED,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                        String message = "";
                        if (error instanceof NetworkError) {
                            message = "NetworkError: Error Network!";
                        } else if (error instanceof ServerError) {
                            message = "ServerError: The server could not be found. Please try again after some time!!";
                        } else if (error instanceof ParseError) {
                            message = "ParseError: Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {
                            message = "NoConnectionError: Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            message = "TimeoutError: Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                        Log.d(TAG, "jsArrayRequest Error : "+ message);

                    }
                }
        );
        // Add request to the Queue.
        requestQueue.add(jsArrayRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView;
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.post,
                parent,
                false) : convertView;
        //Get current item values.
        Post item = items.get(position);

        //get views
        TextView txtTitle = (TextView) listItemView.
                findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) listItemView.
                findViewById(R.id.txtDescription);
        TextView txtCategory = (TextView) listItemView.
                findViewById(R.id.txtCategory);
        TextView txtDate = (TextView) listItemView.
                findViewById(R.id.txtDate);
        final ImageView imagenPost = (ImageView) listItemView.
                findViewById(R.id.imagenPost);

        // set values to view.
        txtTitle.setText(item.getTitle());
        txtDescription.setText(item.getDescription());
        txtCategory.setText(item.getCategory());
        txtDate.setText(item.getDate());

        if(MainActivity.getImageLoadMethod()){
            Log.i(TAG, "Loading images with Volley.");
            // Request to get the image using Volley!
            ImageRequest request = new ImageRequest(
                /*URL_BASE +*/ item.getImagen(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imagenPost.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            imagenPost.setImageResource(R.drawable.ic_error);
                            Log.d(TAG, "Error en respuesta Bitmap: "+ error.getMessage());
                            String message ="";
                            if (error instanceof NetworkError) {
                                message = "NetworkError: Error Network!";
                            } else if (error instanceof ServerError) {
                                message = "ServerError: The server could not be found. Please try again after some time!!";
                            } else if (error instanceof ParseError) {
                                message = "ParseError: Parsing error! Please try again after some time!!";
                            } else if (error instanceof NoConnectionError) {
                                message = "NoConnectionError: Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof TimeoutError) {
                                message = "TimeoutError: Connection TimeOut! Please check your internet connection.";
                            }
                            Toast.makeText(parent.getContext(),message,Toast.LENGTH_LONG).show();
                            Log.d(TAG, "ImageRequest Error : "+ message);
                        }
                    });
            // Add request to the qeue (Volley).
            requestQueue.add(request);

        }else { //Picasso
            Log.i(TAG, "Loading images with Picasso.");
         // Request to get the image using Picasso (sorry glide fans!).
           Picasso.get()
                .load(item.getImagen())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_error)
                .into(imagenPost);
        }

       return listItemView;
    }

     public List<Post> parseJson(JSONObject jsonObject){
        List<Post> posts = new ArrayList();
        JSONArray jsonArray= null;

        try {
            // Get the object from the array.
            jsonArray = jsonObject.getJSONArray("articles");
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);
                    Post post = new Post(
                            objeto.getString("title"),
                            objeto.getString("info"),
                            objeto.getString("imageUrl"),
                            objeto.getString("category"),
                            objeto.getString("date"));
                    posts.add(post);
                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return posts;
    }
}