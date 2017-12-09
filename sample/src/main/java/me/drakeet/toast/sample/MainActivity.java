package me.drakeet.toast.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.drakeet.support.toast.ToastCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastCompat.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }
}
