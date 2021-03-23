package com.example.aifakenews;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;
import android.util.Log;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private ImageView imageView;
    private TextView textView;
    private static final String TAG = "FacebookActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FacebookSdk.sdkInitialize(FacebookActivity.this);

        imageView = findViewById(R.id.profilePic);
        textView = findViewById(R.id.nameText);
        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        // Set with sample values for now, will change later
        loginButton.setPermissions(Arrays.asList("user_gender", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo", "Login Successful");
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo", "Login Error");
            }
        });

    }

//    public void getProfile() {
//
//        try {
//            accessToken = AccessToken.getCurrentAccessToken();
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(
//                                JSONObject object,
//                                GraphResponse response) {
//                            Log.d(TAG, "Graph Object :" + object);
//                            try {
//                                String[] splitStr = object.getString("name").split("\\s+");
//                                FBFirstname = splitStr[0];
//                                FBLastName = splitStr[1];
//                                FBEmail = object.getString("email");
//                                FBUUID = object.getString("id");
//                                Log.e(TAG, "firstnamev: " + splitStr[0]);
//                                Log.e(TAG, "last name: " + splitStr[1]);
//                                Log.e(TAG, "Email id : " + object.getString("email"));
//                                Log.e(TAG, "ID :" + object.getString("id"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,name,link,birthday,gender,email");
//            request.setParameters(parameters);
//            request.executeAsync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String id = object.getString("id");
                            textView.setText(name);
                            Picasso.get().load("https://graph.facebook.com/" + id + "/picture/type-large")
                                    .into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender, name, id, first_name, last_name");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                LoginManager.getInstance().logOut();
                textView.setText("");
                imageView.setImageResource(0);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}