package com.ci.team16.outfitz;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class other_profile extends AppCompatActivity {
    public static ImageView prof;
    public static EditText edit;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        Bundle bundle = getIntent().getExtras();

        String user = bundle.getString("user");
        String email = bundle.getString("email");
        Log.d("other", user);
        DatabaseReference database3 = FirebaseDatabase.getInstance().getReference("Accounts");
        DatabaseReference profile3 = database3.child(user).child("email");
        profile3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    name = snapshot.getValue().toString();
                    Log.d("name", name);
                    TextView namex = findViewById(R.id.textView);
                    namex.setText(name);

                }
                catch(Exception e) {
                    Log.d("nah", "nah");

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        prof = findViewById(R.id.imageView2);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(user + "acc");
        DatabaseReference profile = database.child("prof").child("filex");
        profile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    String pic = snapshot.getValue().toString();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    Log.d("name2", name);
                    StorageReference storageRef = storage.getReference(name+ "/" + "prof" + "/");
                    StorageReference fileRef = storageRef.child(pic);
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            String src = uri.toString();
                            Log.d("user", src);
                            Picasso.get().load(src).into(prof);





                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
                catch(Exception e) {
                    Log.d("nah", "nah");

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference(user + "acc");
        DatabaseReference profile2 = database2.child("bio").child("bio");
        edit = (EditText) findViewById(R.id.editText2);
        profile2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    String pic = snapshot.getValue().toString();
                    edit.setText(pic);

                }
                catch(Exception e) {
                    Log.d("nah", "nah");

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
