package com.example.tanmay.facebookappinviteimplementation;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FacebookAppInviteActivity extends AppCompatActivity {

    private static final int REQUEST_INVITE = 0;
    LoginButton loginButton;
    CallbackManager callbackManager;
    Button firebaseInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_facebook_app_invite);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        firebaseInvite=(Button)findViewById(R.id.firebase_invite);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult){
                openFacebookAppInvite();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        firebaseInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInviteClicked();
            }
        });
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Inner Voice")
                .setMessage("Download this")
                .setDeepLink(Uri.parse("https://fb.me/1349472831832219"))
                .setCustomImage(Uri.parse("https://yt3.ggpht.com/-CRHSauniQ94/AAAAAAAAAAI/AAAAAAAAAAA/6_c-kqZZzeg/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"))
                .setCallToActionText("Install!")
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(FacebookAppInviteActivity.class.getSimpleName(), "onActivityResult: sent invitation " + id);
                }
            } else {

            }
        }
    }

    public void openFacebookAppInvite(){
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://fb.me/1349472831832219";
        //generate app link api through https://developers.facebook.com/quickstarts/1437171463022071/?platform=app-links-host
        previewImageUrl = "https://yt3.ggpht.com/-CRHSauniQ94/AAAAAAAAAAI/AAAAAAAAAAA/6_c-kqZZzeg/s900-c-k-no-mo-rj-c0xffffff/photo.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog appInviteDialog = new AppInviteDialog(this);
            AppInviteDialog.show(this, content);
        }
    }
}
