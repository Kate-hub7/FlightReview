package ru.sukhikh.flightreview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import ru.sukhikh.flightreview.Fragment.SubmitFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubmitFragment()).commit();
    }


}
