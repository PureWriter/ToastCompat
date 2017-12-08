package me.drakeet.support.toast;

import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * @author drakeet
 */
public interface BadTokenListener {

    void onBadTokenOccurred(@NonNull Toast toast);
}
