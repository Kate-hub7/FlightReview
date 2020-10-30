package ru.sukhikh.flightreviewscreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.sukhikh.flightreviewscreen.Fragment.SubmitFragment;


public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubmitFragment()).commit();
    }


}
