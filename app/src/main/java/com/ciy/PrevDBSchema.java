package com.ciy;

import android.provider.BaseColumns;

/**
 * Created by Joe Otter on 5/29/2017.
 */

public class PrevDBSchema {

    public static final class PrevSave implements BaseColumns{
        public static final String PREV_NAME = "prev";
        public static final String SAVE_NAME = "save";

        public static final class Cols{
            public static final String R_NAME = "recipe_name";
            public static final String R_URL = "recipe_url";
            public static final String R_IMG = "recipe_image";
            public static final String R_INGREDIENTS = "ingredients";
        }
    }
}
