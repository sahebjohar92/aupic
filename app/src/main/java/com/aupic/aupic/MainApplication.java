package com.aupic.aupic;

import android.app.Application;

import com.aviary.android.feather.sdk.IAviaryClientCredentials;

/**
 * Created by saheb on 22/11/15.
 */
public class MainApplication extends Application implements IAviaryClientCredentials {

    private static final String CREATIVE_SDK_CLIENT_ID = "16c61380d40f412aa5c3de5bfcc6bc89";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "790e3cad-0288-4913-beb0-77141176c683";

    @Override
    public String getBillingKey() {
        return ""; // Leave this blank
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }
}
