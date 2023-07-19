package edu.ewubd.loginandsignup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UploadActivity extends AppCompatActivity {

    boolean clickedOnMap = false;
    ImageView uploadImage1, uploadImage2, uploadImage3, uploadImage4;
    Button saveButton, mapButton;
    AutoCompleteTextView location, type;
    EditText phone, rent, size, des;
    String imageURL1, imageURL2, imageURL3, imageURL4, username;

    Uri uri1, uri2, uri3, uri4;
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        SharedPreferences preferences = PreferenceHelper.getSharedPreferences(this);

        username = preferences.getString("userUsername", "");

        System.out.println("Username: " + username);


        location = findViewById(R.id.location);
        type = findViewById(R.id.type);
        phone = findViewById(R.id.phone);
        rent = findViewById(R.id.rent);
        size = findViewById(R.id.size);
        des = findViewById(R.id.description);

        saveButton = findViewById(R.id.saveButton);
        mapButton = findViewById(R.id.mapShow);

//        Image

        uploadImage1 = findViewById(R.id.uploadImage1);
        uploadImage2 = findViewById(R.id.uploadImage2);
        uploadImage3 = findViewById(R.id.uploadImage3);
        uploadImage4 = findViewById(R.id.uploadImage4);

// Testing location value
        String[] suggestions = {"Aftabnagar", "Banasree", "Khilgaon", "Badda", "Rampura"};
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        location.setAdapter(adapterLocation);
        location.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
        location.setThreshold(1);


// Testing Type value
        String[] suggestionsForType = {"Family", "Bachelor", "Seat"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, suggestionsForType);
        type.setAdapter(adapterType);
        type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
        type.setThreshold(1);


//        Code to change later to improve performance

//            This code snippet sets up an "activity result launcher" using the AndroidX library's ActivityResultContracts.
//            StartActivityForResult class. It allows the user to select an image from their device's gallery, and then
//            displays the selected image in an ImageView with the ID "uploadImage1".

//        for image 1
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri1 = data.getData();

                            uploadImage1.setImageURI(uri1);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

//            For image 2
        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri2 = data.getData();

                            uploadImage2.setImageURI(uri2);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher2.launch(photoPicker);
            }
        });

//        For image 3
        ActivityResultLauncher<Intent> activityResultLauncher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri3 = data.getData();

                            uploadImage3.setImageURI(uri3);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher3.launch(photoPicker);
            }
        });

//        For image 4
        ActivityResultLauncher<Intent> activityResultLauncher4 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri4 = data.getData();

                            uploadImage4.setImageURI(uri4);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher4.launch(photoPicker);
            }
        });


//        Finish

//        map button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedOnMap = true;
                Intent i = new Intent(UploadActivity.this, PickLocation.class);
                startActivity(i);
            }
        });


//        Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                call function to save data.
                saveData();

            }
        });

    }

    public void saveData() {

//      Creating reference for all the four images
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri1.getLastPathSegment());
        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri2.getLastPathSegment());
        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri3.getLastPathSegment());
        StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri4.getLastPathSegment());

//      Showing loading progress bar
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);

        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL1 = urlImage.toString();

                storageReference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri urlImage = uriTask.getResult();
                        imageURL2 = urlImage.toString();

                        storageReference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri urlImage = uriTask.getResult();
                                imageURL3 = urlImage.toString();

                                storageReference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isComplete()) ;
                                        Uri urlImage = uriTask.getResult();
                                        imageURL4 = urlImage.toString();

                                        uploadData();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });


    }

    public void uploadData() {
        int boost = 0;
        float latitude = 0f;
        float longitude = 0f;

        if (clickedOnMap) {
            SharedPreferences preferences = PreferenceHelper.getSharedPreferences(getApplicationContext());
            latitude = preferences.getFloat("PickedLocationLatitude", 0f);
            longitude = preferences.getFloat("PickedLocationLongitude", 0f);
        }

        String locationData = location.getText().toString();
        String typeData = type.getText().toString();
        String phoneData = phone.getText().toString();
        String rentData = rent.getText().toString();
        String sizeData = size.getText().toString();
        String descriptionData = des.getText().toString();

        String id = locationData + phoneData;


        if (!isValidMobileNo(phoneData)) {
            Toast.makeText(getApplicationContext(), "Phone Is Not Valid", Toast.LENGTH_SHORT).show();
            return;
        }


        DataClass dataClass = new DataClass(locationData, typeData, phoneData, rentData, sizeData, descriptionData, imageURL1, imageURL2, imageURL3, imageURL4, username, latitude, longitude, boost);

        FirebaseDatabase.getInstance().getReference("Upload Data").child(id).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                check code
                if (task.isSuccessful()) {
                    Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static boolean isValidMobileNo(String str) {
        return str.matches("^(?:\\+88|88)?(01[3-9]\\d{8})$");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}