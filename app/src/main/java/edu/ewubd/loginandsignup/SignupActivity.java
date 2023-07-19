package edu.ewubd.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupUserName, signupPassword, confirm_Password;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//      EditText
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUserName = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        confirm_Password = findViewById(R.id.confirm_password);

//      TextView
        loginRedirectText = findViewById(R.id.loginRedirectText);

//      Button
        signupButton = findViewById(R.id.signup_button);


//        signup button onclick handler function
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              Initialize Database
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

//              Getting users inputs

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUserName.getText().toString();
                String password = signupPassword.getText().toString();
                String confirmPassword = confirm_Password.getText().toString();


                //                Checking

                if (name.isEmpty()) {
                    signupName.setError("Name can not be empty");
                    signupName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    signupEmail.setError("Email can not be empty");
                    signupEmail.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    signupEmail.setError("Invalid email address");
                    signupEmail.requestFocus();
                    return;
                }

                if (username.isEmpty()) {
                    signupUserName.setError("Username can not be empty");
                    signupUserName.requestFocus();
                    return;
                }


                if (password.isEmpty()) {
                    signupPassword.setError("Password can not be empty");
                    signupPassword.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()) {
                    confirm_Password.setError("Confirm password can not be empty");
                    confirm_Password.requestFocus();
                    return;
                }

                if (!confirmPassword.equals(password)) {
                    confirm_Password.setError("password and confirm password doesn't match");
                    confirm_Password.requestFocus();
                    return;
                }
// Testing part
//      Initialize Database
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

//      Query the database for the user with the entered username
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            signupUserName.setError("There is another user with that user name!");
                            signupUserName.requestFocus();
                        } else {
                            // proceed with the sign-up process
//                          Create a unique key for the user
                            String userId = reference.push().getKey();

//                          Generating hash password
                            String hashedPassword = PasswordUtils.hashPassword(password);

                            HelperClass helperClass = new HelperClass(name, email, username, hashedPassword);
                            reference.child(userId).setValue(helperClass);

                            Toast.makeText(SignupActivity.this, "You have signup successfully", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // handle database error latter
                        // display an error message to the user
                        Toast.makeText(SignupActivity.this, "Could not check username. Please try again later.", Toast.LENGTH_SHORT).show();

                    }
                });


//                Testing part end


            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}