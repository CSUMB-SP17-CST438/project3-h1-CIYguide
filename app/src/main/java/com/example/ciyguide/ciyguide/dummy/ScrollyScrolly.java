package com.example.ciyguide.ciyguide.dummy;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by lhernandez on 5/1/2017.
 * creates a version of webview that allows the webview to scroll
 * even if it is encapsulated in a scrollview
 */

public class ScrollyScrolly extends WebView {
    public ScrollyScrolly(Context context){
        super(context);
    }
    public ScrollyScrolly(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public ScrollyScrolly(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //the important piece of code!!!
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}
