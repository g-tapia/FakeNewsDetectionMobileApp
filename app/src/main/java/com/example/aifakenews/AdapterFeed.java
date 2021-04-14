package com.example.aifakenews;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Looper;
import android.security.identity.InvalidRequestMessageException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {

    String urlToCheck = "";
    boolean isurl = false;

    private static final String TAG = "AdapterFeed";
    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;

    public AdapterFeed(Context context,  ArrayList<ModelFeed> modelFeedArrayList) {

        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        holder.tv_name.setText(modelFeed.getName());
        holder.tv_likes.setText(String.valueOf(modelFeed.getLikes()));
        holder.tv_comments.setText(modelFeed.getComments() + " comments");
        holder.tv_time.setText(modelFeed.getTime());


        String[] statusTokens = modelFeed.getStatus().split(" ");
        for (int i = 0; i < statusTokens.length; i++){
            if (statusTokens[i].startsWith("https://") || statusTokens[i].startsWith("http://")){
                urlToCheck = statusTokens[i];
                isurl = true;
                //Log.d(TAG, "onBindViewHolder: urltocheck = " + urlToCheck);
            }
        }

        Log.d(TAG, "onBindViewHolder: urltocheck = " + urlToCheck);

        holder.tv_status.setText(modelFeed.getStatus());
        holder.tv_status.setTextColor(Color.BLACK);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                if (isurl){
                    isurl = false;
                }else{
                    return;
                }

                Uri dataUri = Uri.parse(urlToCheck);
                String urlToUse = dataUri.toString();

                Configuration config = new Configuration(context.getCacheDir().getAbsolutePath());
                ContentExtractor extractor = new ContentExtractor(config);

                Article article = null;
                try {
                    article = extractor.extractContent(urlToUse, true);
                    Log.d(TAG, "run: " + article);
                } catch (Exception e) {
                    //Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + article);
                }

                String details = "";
                try {
                    details = article.getCleanedArticleText();
                    Log.d(TAG, "run: DETAILS = " + details);
                } catch (Exception e) {
                    //Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: no article for " + urlToCheck);
                }

                if (!details.isEmpty()) {
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        String URL = "http://192.168.56.1:5000/data";
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("news", details);
                        final String requestBody = jsonBody.toString();
                        Log.d(TAG, "run: json stuff done");

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                                Log.d(TAG, "onResponse: " + response);
                                try {
                                    String[] parsed = response.split("\\s+");
                                    if (parsed[0].equalsIgnoreCase("Real")) {

                                        double probability = Double.parseDouble(parsed[2].substring(1, parsed[2].length() - 1));
                                        if (probability < .5){
                                            holder.tv_status.setTextColor(Color.RED);

                                        }else{
                                            holder.tv_status.setTextColor(Color.GREEN);
                                        }

                                    } else if (parsed[0].equalsIgnoreCase("Fake")) {

                                        double probability = Double.parseDouble(parsed[2].substring(1, parsed[2].length() - 1));
                                        if (probability > .5){
                                            holder.tv_status.setTextColor(Color.GREEN);
                                        }else{
                                            holder.tv_status.setTextColor(Color.RED);
                                        }

                                    } else {

                                        holder.tv_status.setTextColor(Color.YELLOW);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", error.toString());
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                Log.d(TAG, "run: get body content type");
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                Log.d(TAG, "run: get body");
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                Log.d(TAG, "run: parse start");
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response);
                                    // can get more details such as response.headers
                                }
                                Log.d(TAG, "run:" + responseString);
                                //                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                return super.parseNetworkResponse(response);
                            }
                        };
                        int socketTimeout = 10000;//30 seconds - change to what you want
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(policy);
                        Log.d(TAG, "run: requestQueue adding");
                        requestQueue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    holder.tv_status.setTextColor(Color.BLACK);
                }
            }
        }).start();

    }

    @Override
    public int getItemCount() {

        if (modelFeedArrayList != null) {
            return modelFeedArrayList.size();
        }
        else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgView_proPic = (ImageView)itemView.findViewById(R.id.imgView_profile_pic);

            tv_name = (TextView) itemView.findViewById(R.id.textView_Name);
            tv_comments = (TextView) itemView.findViewById(R.id.textView_comments);
            tv_time = (TextView) itemView.findViewById(R.id.textView_timeSincePost);
            tv_likes = (TextView) itemView.findViewById(R.id.textView_likes);
            tv_status = (TextView) itemView.findViewById(R.id.textView_status);
        }

    }
}
