package app.com.example.android.agenttagging;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by shuvam on 07-09-2016.
 */
public class RecyclerViewDisabler implements RecyclerView.OnItemTouchListener {

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
