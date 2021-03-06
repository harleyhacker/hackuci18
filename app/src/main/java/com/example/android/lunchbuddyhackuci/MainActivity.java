package com.example.android.lunchbuddyhackuci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ProfileTracker profileTrack;
    private Profile currentProfile;
    private String userFirstName;
    private String userLastName;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = database.child("Users");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("DSLKFJSDLKFJKSDFS");
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (Profile.getCurrentProfile() == null)
                        {
                            profileTrack = new ProfileTracker(){
                            @Override
                            public void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile){
                                userLastName = currentProfile.getLastName();
//                                User user = new User();
//                                user.setLastname(userLastName);
//                                user.setFirstName(currentProfile.getFirstName());
//                                mUserRef.child(currentProfile.getId()).setValue(user);

                                Log.v("facebook - profile", currentProfile.getFirstName());
                                Log.v("facebook - profile",currentProfile.getLastName());
                                profileTrack.stopTracking();
                                }
                            };
                        }
                        else
                        {
                            currentProfile = Profile.getCurrentProfile();
                            userFirstName = currentProfile.getFirstName();
                            userLastName = currentProfile.getLastName();
                            System.out.println(userFirstName + " " + userLastName);
                        }

                        startActivity(new Intent(MainActivity.this, MapUI.class));
                    }

                    @Override
                    public void onCancel() {
                        Log.v("facebook - onCancel", "cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.v("facebook - onError", exception.getMessage());
                    }
                });

//        Button nextActivity = (Button) findViewById(R.id.mapButtonActivityLoader);
//        nextActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MapUI.class));
//            }
//        });
    }



}
