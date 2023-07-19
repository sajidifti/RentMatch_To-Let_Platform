package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NormalDetailsActivity extends AppCompatActivity {
    float latPicked = 23.7596273f;
    float lngPicked = 90.4122775f;
    TextView location, type, phone, rent, size, des;
    ImageView detailImage1, detailImage2, detailImage3, detailImage4;

    TextView btn_map, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_details);

        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        phone = findViewById(R.id.phone);
        rent = findViewById(R.id.rent);
        size = findViewById(R.id.size);
        des = findViewById(R.id.description);

        detailImage1 = findViewById(R.id.detailImage1);
        detailImage2 = findViewById(R.id.detailImage2);
        detailImage3 = findViewById(R.id.detailImage3);
        detailImage4 = findViewById(R.id.detailImage4);

        btn_back = findViewById(R.id.btn_back);
        btn_map = findViewById(R.id.showMap);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            location.setText(bundle.getString("location"));
            type.setText(bundle.getString("type"));
            phone.setText(bundle.getString("phone"));
            rent.setText(bundle.getString("rent"));
            size.setText(bundle.getString("size"));
            des.setText(bundle.getString("description"));

            latPicked = bundle.getFloat("latitude" + "");
            lngPicked = bundle.getFloat("longitude" + "");

            System.out.println("DB Lat/Lng: " + latPicked + lngPicked);

            Glide.with(this).load(bundle.getString("Image1")).into(detailImage1);
            Glide.with(this).load(bundle.getString("Image2")).into(detailImage2);
            Glide.with(this).load(bundle.getString("Image3")).into(detailImage3);
            Glide.with(this).load(bundle.getString("Image4")).into(detailImage4);


        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NormalDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        Map button
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NormalDetailsActivity.this, ShowLocation.class);

                System.out.println("lat: " + latPicked);
                System.out.println("lng: " + lngPicked);

                double lat = (double) latPicked;
                double lng = (double) lngPicked;

                System.out.println("lat: " + latPicked);
                System.out.println("lng: " + lngPicked);

                i.putExtra("latPicked", lat);
                i.putExtra("lngPicked", lng);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NormalDetailsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}