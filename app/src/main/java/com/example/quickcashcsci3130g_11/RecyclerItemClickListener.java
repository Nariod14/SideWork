package com.example.quickcashcsci3130g_11;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
/**
 * An item click listener for RecyclerView items. Provides callbacks for single tap and long press events.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        /**
         * Callback method for when an item is clicked.
         *
         * @param view     The clicked view.
         * @param position The position of the clicked item in the RecyclerView.
         */
        void onItemClick(View view, int position);
    }

    private GestureDetector mGestureDetector;

    /**
     * Creates a new RecyclerItemClickListener.
     *
     * @param context  The context.
     * @param recyclerView The RecyclerView to listen for item clicks.
     * @param listener The item click listener.
     */
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    /**
     * Intercepts touch events for the RecyclerView to handle item clicks and long presses.
     *
     * @param view The RecyclerView to intercept touch events for.
     * @param e The MotionEvent describing the touch event.
     * @return True if the touch event was intercepted, otherwise false.
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    /**
     * Handles touch events after they have been intercepted.
     *
     * @param view The RecyclerView that received the touch event.
     * @param motionEvent The MotionEvent describing the touch event.
     */
    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    /**
     * Callback for when it's requested to disallow intercepting touch events.
     *
     * @param disallowIntercept True if touch event interception should be disallowed, otherwise false.
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
