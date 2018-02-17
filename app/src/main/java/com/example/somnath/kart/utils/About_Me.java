package com.example.somnath.kart.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.somnath.kart.R;

/**
 * Created by SOMNATH on 14-02-2018.
 */

public class About_Me extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me);

        String string="I am a CSE geek who loves to spend time with the laptop and gadgets. If there is no gadget in my hand then I " +
                "feel that a part of me is missing. So you must have got what kind of person I am." +
                "Lets know about my skills. I am learning Web and Android. I have spent quite a time in learning and developing websites and " +
                "apps. I keep on updating myself with newer technologies. " +
                "Last but not the least I really enjoy solving problems.";
        TextView textView=(TextView) findViewById(R.id.about_Me);

    }
}
