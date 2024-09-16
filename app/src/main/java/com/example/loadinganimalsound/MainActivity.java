package com.example.loadinganimalsound;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int[] animalImages = {
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3
    };

    private int[] colors;
    private ImageView animalImageView;
    private TextView textView;
    private RelativeLayout layout;
    private ProgressBar customProgressBar;
    private TextView loadingText;
    private View loadingOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors = new int[]{
                ContextCompat.getColor(this, R.color.purple_200),
                ContextCompat.getColor(this, R.color.purple_500),
                ContextCompat.getColor(this, R.color.purple_700),
                ContextCompat.getColor(this, R.color.teal_200),
                ContextCompat.getColor(this, R.color.teal_700)
        };

        animalImageView = findViewById(R.id.animalImageView);
        textView = findViewById(R.id.textView);
        layout = findViewById(R.id.layout);
        customProgressBar = findViewById(R.id.customProgressBar);
        loadingText = findViewById(R.id.loadingText);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        showLoading();

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(30); // Thời gian tải
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int finalI = i;
                runOnUiThread(() -> customProgressBar.setProgress(finalI));
            }
            runOnUiThread(() -> {
                hideLoading();
                showRandomAnimal();
            });
        }).start();
    }

    private void showLoading() {
        loadingOverlay.setVisibility(View.VISIBLE);
        customProgressBar.setVisibility(View.VISIBLE);
        loadingText.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        animalImageView.setAlpha(0.3f);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            float rotation = (float) animation.getAnimatedValue();
            loadingText.setRotation(rotation);
        });
        animator.start();
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(View.GONE);
        customProgressBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        animalImageView.setAlpha(1.0f);
    }

    private void showRandomAnimal() {
        Random random = new Random();
        int randomAnimal = random.nextInt(animalImages.length);
        int randomColor = random.nextInt(colors.length);

        animalImageView.setImageResource(animalImages[randomAnimal]);
        layout.setBackgroundColor(colors[randomColor]);
    }
}