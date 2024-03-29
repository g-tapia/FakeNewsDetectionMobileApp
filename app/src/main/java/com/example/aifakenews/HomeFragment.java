package com.example.aifakenews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import me.angrybyte.goose.Article;
import me.angrybyte.goose.Configuration;
import me.angrybyte.goose.ContentExtractor;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    TextInputEditText editText;
    TextInputLayout history1;
    TextInputEditText history;
    Button button;
    SharedPreferences sharedPref;
    Button clearhistory;
    Button selecthistory;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        editText = view.findViewById(R.id.link);
        history = view.findViewById(R.id.history);
        history1 = view.findViewById(R.id.history1);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
        clearhistory = view.findViewById(R.id.clearhistory);
        clearhistory.setOnClickListener(this);
        selecthistory = view.findViewById(R.id.selecthistory);
        selecthistory.setOnClickListener(this);

        selecthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(history.getWindowToken(), 0);

                if (history.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "History is empty", Toast.LENGTH_SHORT).show();
                }else{
                    String historytext = history.getText().toString();
                    final CharSequence[] hArray = historytext.split("\\r?\\n");
                    Log.d(TAG, "onClick: the length of the list is: " + hArray.length);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Make a selection:");
                    builder.setItems(hArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            editText.setText(hArray[which]);
                        }
                    });

                    builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ;
                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                }
            }
        });

        history1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(), "LOL", Toast.LENGTH_SHORT).show();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(history1.getWindowToken(), 0);

            }
        });

        clearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                history.getText().clear();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        String link = sharedPref.getString("LINK", "");
        String historytext = sharedPref.getString("HISTORY", "");
        editText.setText(link);
        history.setText(historytext);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LINK", editText.getText().toString());
        editor.putString("HISTORY", history.getText().toString());
        editor.apply();
        super.onPause();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.drawer_menu);
        if(item!=null)
            item.setVisible(false);
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: The link is: " + editText.getText().toString());
        history.setText(editText.getText().toString() + "\n" + history.getText().toString() + "\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); //////////////
                Uri dataUri = Uri.parse(editText.getText().toString());
                String urlToUse = dataUri.toString();

                Configuration config = new Configuration(getActivity().getCacheDir().getAbsolutePath());
                ContentExtractor extractor = new ContentExtractor(config);

                Article article = null;
                try {
                    article = extractor.extractContent(urlToUse, true);
                    Log.d(TAG, "run: " + article);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + article);
                }

                String details = "";
                try {
                    details = article.getCleanedArticleText();
                    Log.d(TAG, "run: DETAILS = " + details);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "run: " + details);
                }

                if (!details.isEmpty()) {
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        String URL = "http://192.168.56.1:5000/data";
//                        String URL = "http://10.0.0.56:5000/data";

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

                                        // Simple dialog - no buttons.
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                        builder.setMessage("The news source you entered is REAL");
                                        builder.setTitle("Real News!");

                                        double probability = Double.parseDouble(parsed[2].substring(1, parsed[2].length() - 1));
                                        if (probability < .5){
                                            builder.setMessage("The news source you entered is FAKE");
                                            builder.setTitle("Fake News!");
                                        }


                                        //AlertDialog dialog = builder.create();
                                        builder.show();
                                    } else if (parsed[0].equalsIgnoreCase("Fake")) {
                                        // Simple dialog - no buttons.
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                        builder.setMessage("The news source you entered is FAKE");
                                        builder.setTitle("Fake News!");

                                        double probability = Double.parseDouble(parsed[2].substring(1, parsed[2].length() - 1));
                                        if (probability > .5){
                                            builder.setMessage("The news source you entered is REAL");
                                            builder.setTitle("Real News!");
                                        }


                                        //AlertDialog dialog = builder.create();
                                        builder.show();
                                    } else {
                                        // Simple dialog - no buttons.
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                        builder.setMessage("There was an error parsing the server response.");
                                        builder.setTitle("Error!");

                                        //AlertDialog dialog = builder.create();
                                        builder.show();
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
                }
            }
        }).start();

    }



}
