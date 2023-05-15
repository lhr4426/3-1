package com.example.mc_week10_homework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    RatingBar rating_cat_1, rating_cat_2, rating_cat_3;
    int cat1, cat2, cat3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        rating_cat_1 = (RatingBar) findViewById(R.id.rating_cat_1);
        rating_cat_2 = (RatingBar) findViewById(R.id.rating_cat_2);
        rating_cat_3 = (RatingBar) findViewById(R.id.rating_cat_3);

        Intent intent = getIntent();

        if (intent != null) {
            cat1 = intent.getIntExtra("cat1", 0);
            cat2 = intent.getIntExtra("cat2", 0);
            cat3 = intent.getIntExtra("cat3", 0);

            rating_cat_1.setRating(checkRating(cat1));
            rating_cat_2.setRating(checkRating(cat2));
            rating_cat_3.setRating(checkRating(cat3));
        }
    }

    public int checkRating(int rating) {
        if(rating > 5)
            return 5;
        else
            return rating;
    }
    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("cat1", (int) rating_cat_1.getRating());
        intent.putExtra("cat2", (int) rating_cat_2.getRating());
        intent.putExtra("cat3", (int) rating_cat_3.getRating());
        setResult(RESULT_OK, intent);
        finish();
    }
}
