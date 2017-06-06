package com.ciy;

import android.provider.BaseColumns;

/**
 * Created by Joe Otter on 6/4/2017.
 */

public class FridgeDBSchema {
    public static final class Fridge implements BaseColumns{
        public static final String NAME = "fridge";
        public static final class Cols{
            public static final String ING_AMT = "amount";
            public static final String ING_NAME = "ingredient";
        }
    }
}
