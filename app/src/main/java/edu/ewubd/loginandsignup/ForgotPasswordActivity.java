package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText username, userEmail, newPassword, confirmPassword;

    Button btn_update;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        username = findViewById(R.id.editUsername);
        userEmail = findViewById(R.id.user_email);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);


        btn_update = findViewById(R.id.btn_update);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentUserName = username.getText().toString().trim();
                String currentE = userEmail.getText().toString().trim();
                String newP = newPassword.getText().toString().trim();
                String confirmP = confirmPassword.getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                // Query the database for the user with the entered username
                Query checkUserDatabase = reference.orderByChild("username").equalTo(currentUserName);

                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                // Get the user ID from the snapshot
                                String userId = userSnapshot.getKey();

                                String emilFromDB = userSnapshot.child("email").getValue(String.class);

                                assert emilFromDB != null;
                                if (emilFromDB.equals(currentE)) {
                                    if (newP.equals(confirmP)) {
                                        newPassword.setError(null);
                                        confirmPassword.setError(null);
                                        String hashPass = PasswordUtils.hashPassword(newP);
                                        // Update the user's password
                                        reference.child(userId).child("password").setValue(hashPass);

                                        Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ForgotPasswordActivity.this, SignupActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        newPassword.setError("Invalid Credentials");
                                        confirmPassword.setError("Invalid Credentials");
                                        newPassword.requestFocus();
                                        confirmPassword.requestFocus();
                                    }
                                    userEmail.setError(null);
                                } else {
                                    userEmail.setError("Invalid Credentials");
                                    userEmail.requestFocus();
                                }
                            }
                        } else {
                            username.setError("User does not exist");
                            username.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ForgotPasswordActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}