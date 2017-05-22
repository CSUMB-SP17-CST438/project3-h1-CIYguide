package com.ciy;

import android.provider.BaseColumns;

/**
 * Created by Joe Otter on 5/20/2017.
 */

/*
    This table does not need a key.
    It will consist of two columns.
    One column will display the name of what the preference is and the
    other column will display whether it is checked or not.
 */

public class PreferencesDBSchema {

    public static final class Preferences implements BaseColumns{
        public static final String NAME = "preferences";

        public static final class Cols{
            public static final String PREFNAME = "prefname";
            public static final String CHECKED = "checked";
        }
    }
}
