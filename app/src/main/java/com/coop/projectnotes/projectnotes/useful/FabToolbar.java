package com.coop.projectnotes.projectnotes.useful;

import android.support.design.widget.FloatingActionButton;
import android.view.View;


public class FabToolbar
{
    public static FabMenuBuilder with(FloatingActionButton fab) {
        return new FabMenuBuilder(fab);
    }

    public static class FabMenuBuilder
    {
        private int duration = 150;
        private FloatingActionButton fab;
        private FabToolbarAnimator animator;
        private OnFabMenuListener listener;

        FabMenuBuilder(FloatingActionButton fab)
        {
            if (fab == null) {
                throw new NullPointerException("FloatingActionButton is null.");
            }
            this.fab = fab;
            this.animator = new FabToolbarAnimator(fab.getContext());
        }

        public FabMenuBuilder duration(int millisecond) {
            this.duration = millisecond / 2;
            return this;
        }

        public FabMenuBuilder setListener(OnFabMenuListener listener) {
            this.listener = listener;
            return this;
        }

        public void transformTo(View transformView) {
            if (transformView == null) {
                throw new NullPointerException("View is null.");
            }
            if (fab.getVisibility() == View.VISIBLE) {
                animator.transformTo(fab, transformView, duration, listener);
            }
        }

        public void transformFrom(View transformView) {
            if (transformView == null) {
                throw new NullPointerException("View is null.");
            }
            if (fab.getVisibility() != View.VISIBLE) {
                animator.transformOut(fab, transformView, duration, listener);
            }
        }
    }

    public interface OnFabMenuListener {
        public void onStartTransform();
        public void onEndTransform();
    }
}