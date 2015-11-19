package com.aupic.aupic.Event;

import com.squareup.otto.Bus;

/**
 * Created by saheb on 20/10/15.
 */
public class AppBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
