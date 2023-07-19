package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    EditText location, type, phone, rent, size, des;

    TextView btn_back, btn_save, btn_delete, btn_boost;
    ImageView detailImage1, detailImage2, detailImage3, detailImage4;

    DatabaseReference reference;

    List<String> imageUrls = new ArrayList<>();
    String imageUrl1 = "", imageUrl2 = "", imageUrl3 = "", imageUrl4 = "";

    String id;
    int boost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        btn_save = findViewById(R.id.btn_save);
        btn_delete = findViewById(R.id.btn_delete);
        btn_boost = findViewById(R.id.btn_boost);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            location.setText(bundle.getString("location"));
            type.setText(bundle.getString("type"));
            phone.setText(bundle.getString("phone"));
            rent.setText(bundle.getString("rent"));
            size.setText(bundle.getString("size"));
            des.setText(bundle.getString("description"));

            Glide.with(this).load(bundle.getString("Image1")).into(detailImage1);
            Glide.with(this).load(bundle.getString("Image2")).into(detailImage2);
            Glide.with(this).load(bundle.getString("Image3")).into(detailImage3);
            Glide.with(this).load(bundle.getString("Image4")).into(detailImage4);


            imageUrl1 = bundle.getString("Image1");
            imageUrl2 = bundle.getString("Image2");
            imageUrl3 = bundle.getString("Image3");
            imageUrl4 = bundle.getString("Image4");

            imageUrls.add(imageUrl1);
            imageUrls.add(imageUrl2);
            imageUrls.add(imageUrl3);
            imageUrls.add(imageUrl4);

            boost = bundle.getInt("boost", 0);

            System.out.println("Boost: " + boost);

            String l = bundle.getString("location");
            String p = bundle.getString("phone");
            id = l + p;
            reference = FirebaseDatabase.getInstance().getReference("Upload Data").child(id);

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        Button save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });

//        Boost Button
        btn_boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, BoostActivity.class);
                i.putExtra("id", id);
                i.putExtra("boost", boost);
                startActivity(i);
            }
        });

//        Delete button

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//      Test part
                FirebaseStorage storage = FirebaseStorage.getInstance();
                for (String imageUrl : imageUrls) {
                    StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(DetailActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
//      End
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}