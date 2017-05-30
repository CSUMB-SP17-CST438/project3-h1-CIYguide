package com.ciy;

import android.provider.BaseColumns;

/**
 * Created by Joe Otter on 5/29/2017.
 */

public class PrevDBSchema {

    public static final class Prev implements BaseColumns{
        public static final String NAME = "prev";

        public static final class Cols{
            public static final String R_NAME = "recipe_name";
            public static final String R_URL = "recipe_url";
            public static final String R_IMG = "recipe_image";
            public static final String R_INGREDIENTS = "ingredients";
        }
    }
}
