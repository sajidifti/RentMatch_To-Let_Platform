package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoostActivity extends AppCompatActivity {

    EditText phone, transaction;
    Button boost;

    DatabaseReference reference;
    int newBoost = 0;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        newBoost = i.getIntExtra("boost", 0);


        phone = findViewById(R.id.phone);
        transaction = findViewById(R.id.transaction);

        boost = findViewById(R.id.btn_boost);

        reference = FirebaseDatabase.getInstance().getReference("Upload Data").child(id);

        boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneEntered = phone.getText().toString();
                String transactionEnterd = transaction.getText().toString();


                if (!phoneEntered.isEmpty() && !transactionEnterd.isEmpty()) {

//                    Save change in dataBase

                    newBoost = newBoost + 100;
                    reference.child("boost").setValue(newBoost);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BoostActivity.this, "Post Boosted Successfully!!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(BoostActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }, 1000); // 1000 milliseconds = 1 second

                } else {
                    Toast.makeText(BoostActivity.this, "Please enter phone and transaction id", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}