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

public class EditProfileActivity extends AppCompatActivity {
    EditText editName, editEmail, editUsername;
    Button saveButton;
    String nameUser, emailUser, usernameUser, userId;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        saveButton = findViewById(R.id.saveButton);
        showData();

        reference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged() || isUserName()) {
                    if (isUserName()) {
                        Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileActivity.this, SignupActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 1000); // 1000 milliseconds = 1 second
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())) {
//            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            reference.child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isUserName() {
        if (!usernameUser.equals(editUsername.getText().toString())) {
            reference.child("username").setValue(editUsername.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())) {
//            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            reference.child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}