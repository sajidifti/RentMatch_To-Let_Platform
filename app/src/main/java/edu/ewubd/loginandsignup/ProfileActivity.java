package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email, formUsername, btn_edit, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        formUsername = findViewById(R.id.profileUsername);

        btn_edit = findViewById(R.id.editButton);
        btn_logout = findViewById(R.id.btn_logout);

//        getting current user
        SharedPreferences preferences = PreferenceHelper.getSharedPreferences(this);
        String username = preferences.getString("userUsername", "");


//       Initialize the database reference.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

//      Create a query for the user with the entered username.
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

//      Add a listener to the query.
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    // The user with the given username exists
                    // You can get the user data by iterating over the children of the snapshot
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Get the user object
                        HelperClass user = childSnapshot.getValue(HelperClass.class);
                        // Use the user object as needed
                        name.setText(user.name);

                        email.setText(user.email);
                        formUsername.setText(user.username);

                        System.out.println("Current user info: " + name + " " + email + " " + username);


                    }
                } else {
                    // The user with the given username does not exist
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error.
                System.out.println("Error: " + error.getMessage());
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
//    Test

    public void passUserData() {

//        getting current user
        SharedPreferences preferences = PreferenceHelper.getSharedPreferences(this);
        String username = preferences.getString("userUsername", "");

//       Initialize the database reference.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//      Create a query for the user with the entered username.
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // The user with the given username exists
                    // You can get the user data by iterating over the children of the snapshot
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Get the user object
                        HelperClass user = childSnapshot.getValue(HelperClass.class);
                        // Use the user object as needed

                        String nameFromDB = user.name;
                        String emailFromDB = user.email;
                        String usernameFromDB = user.username;
                        String userId = childSnapshot.getKey();

                        System.out.println("In pass user data");

                        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        startActivity(intent);

                    }
                } else {
                    // The user with the given username does not exist
                    System.out.println("Does Not Exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error.
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


//    String nameFromDB =
//            String emailFromDB =
//        String usernameFromDB =
//
//        System.out.println("In pass user data");
//
//        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
//        intent.putExtra("name", nameFromDB);
//        intent.putExtra("email", emailFromDB);
//        intent.putExtra("username", usernameFromDB);
//        startActivity(intent);