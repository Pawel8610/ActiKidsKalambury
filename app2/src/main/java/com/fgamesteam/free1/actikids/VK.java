package com.fgamesteam.free1.actikids;

import com.vk.sdk.VKSdk;

/**
 * Created by pawel on 2016-07-21.
 */
public class VK extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(this);
    }
}
