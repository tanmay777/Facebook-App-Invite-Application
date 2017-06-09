package com.example.tanmay.facebookappinviteimplementation;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FacebookAppInviteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_facebook_app_invite);
        openFacebookAppInvite();
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
