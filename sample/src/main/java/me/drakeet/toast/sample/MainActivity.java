package me.drakeet.toast.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import me.drakeet.support.toast.ToastCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastCompat.makeText(this, "hello", Toast.LENGTH_SHORT)
            .setBadTokenListener(toast -> {
                Log.e("failed toast", text(toast));
            }).show();
    }


    private @NonNull String text(@NonNull Toast toast) {
        TextView textView = toast.getView().findViewById(com.android.internal.R.id.message);
        if (textView != null) {
            return textView.getText().toString();
        } else {
            return toast.toString();
        }
    }
}
