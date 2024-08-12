package com.example.leavemanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StartScreen extends AppCompatActivity {
    Animation anim;
    ImageView imgLogo;
    CardView card;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        getSupportActionBar().hide();

        imgLogo=findViewById(R.id.imgLogo);
        card=findViewById(R.id.card2);
        btn=findViewById(R.id.button);
        //animtion();
        fadein();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        /*new CountDownTimer(7000,1000)  
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }.start();*/

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animtion();
            }
        });
    }


    private void animtion()
    {
        anim= AnimationUtils.loadAnimation(this,R.anim.rotatelogo);
        imgLogo.startAnimation(anim);
    }
    private void fadein()
    {
        btn.setVisibility(View.VISIBLE);
        btn.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.moveright));
        btn.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
    }
}