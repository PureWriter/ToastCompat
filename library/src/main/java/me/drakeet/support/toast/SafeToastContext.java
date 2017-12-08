package me.drakeet.support.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @author drakeet
 */
public class SafeToastContext extends ContextWrapper {

    private @Nullable BadTokenListener badTokenListener;


    public SafeToastContext(@NonNull Context base) {
        super(base);
    }


    @Override
    public Context getApplicationContext() {
        return new ApplicationContext(getBaseContext().getApplicationContext());
    }


    public void setBadTokenListener(@NonNull BadTokenListener badTokenListener) {
        this.badTokenListener = badTokenListener;
    }


    private class ApplicationContext extends ContextWrapper {

        public ApplicationContext(@NonNull Context base) {
            super(base);
        }


        @Override
        public Object getSystemService(@NonNull String name) {
            if (Context.WINDOW_SERVICE.equals(name)) {
                // noinspection ConstantConditions
                return new WindowManagerWrapper((WindowManager) getBaseContext().getSystemService(name));
            }
            return super.getSystemService(name);
        }
    }


    private class WindowManagerWrapper implements WindowManager {

        private static final String TAG = "WindowManagerWrapper";
        private final @NonNull WindowManager base;


        private WindowManagerWrapper(@NonNull WindowManager base) {
            this.base = base;
        }


        @Override
        public Display getDefaultDisplay() {
            return base.getDefaultDisplay();
        }


        @Override
        public void removeViewImmediate(View view) {
            base.removeViewImmediate(view);
        }


        @Override
        public void addView(View view, ViewGroup.LayoutParams params) {
            try {
                Log.d(TAG, "WindowManager's addView(view, params) has been hooked.");
                base.addView(view, params);
            } catch (BadTokenException e) {
                Log.i(TAG, e.getMessage());
                if (badTokenListener != null) {
                    badTokenListener.onBadTokenOccurred();
                }
            }
        }


        @Override
        public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
            base.updateViewLayout(view, params);
        }


        @Override
        public void removeView(View view) {
            base.removeView(view);
        }
    }
}
