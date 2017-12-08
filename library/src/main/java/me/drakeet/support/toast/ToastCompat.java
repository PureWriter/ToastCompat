package me.drakeet.support.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;
import java.lang.reflect.Field;

/**
 * @author drakeet
 */
public final class ToastCompat extends Toast {

    private final @NonNull Toast toast;


    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     * or {@link Activity} object.
     * @param base The base toast
     */
    private ToastCompat(Context context, @NonNull Toast base) {
        super(context);
        this.toast = base;
    }


    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     * or {@link android.app.Activity} object.
     * @param text The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     * {@link #LENGTH_LONG}
     */
    public static ToastCompat makeText(Context context, CharSequence text, int duration) {
        // We cannot pass the SafeToastContext to Toast.makeText() because
        // the View will unwrap the base context and we are in vain.
        @SuppressLint("ShowToast")
        Toast toast = Toast.makeText(context, text, duration);
        setContext(toast.getView(), new SafeToastContext(context, toast));
        return new ToastCompat(context, toast);
    }


    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     * or {@link android.app.Activity} object.
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     * {@link #LENGTH_LONG}
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    public static Toast makeText(Context context, @StringRes int resId, int duration)
        throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }


    public @NonNull ToastCompat setBadTokenListener(@NonNull BadTokenListener listener) {
        ((SafeToastContext) getView().getContext()).setBadTokenListener(listener);
        return this;
    }


    @Override
    public void show() {
        toast.show();
    }


    @Override
    public void setDuration(int duration) {
        toast.setDuration(duration);
    }


    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }


    @Override
    public void setMargin(float horizontalMargin, float verticalMargin) {
        toast.setMargin(horizontalMargin, verticalMargin);
    }


    @Override
    public void setText(int resId) {
        toast.setText(resId);
    }


    @Override
    public void setText(CharSequence s) {
        toast.setText(s);
    }


    @Override
    public void setView(View view) {
        toast.setView(view);
        setContext(view, new SafeToastContext(view.getContext(), this));
    }


    @Override
    public float getHorizontalMargin() {
        return toast.getHorizontalMargin();
    }


    @Override
    public float getVerticalMargin() {
        return toast.getVerticalMargin();
    }


    @Override
    public int getDuration() {
        return toast.getDuration();
    }


    @Override
    public int getGravity() {
        return toast.getGravity();
    }


    @Override
    public int getXOffset() {
        return toast.getXOffset();
    }


    @Override
    public int getYOffset() {
        return toast.getYOffset();
    }


    @Override
    public View getView() {
        return toast.getView();
    }


    public @NonNull Toast getBaseToast() {
        return toast;
    }


    private static void setContext(@NonNull View view, @NonNull Context context) {
        try {
            Field field = View.class.getDeclaredField("mContext");
            field.setAccessible(true);
            field.set(view, context);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
