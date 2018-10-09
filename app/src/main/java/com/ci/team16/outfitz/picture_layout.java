package com.ci.team16.outfitz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class picture_layout extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_layout);
        Bundle bundle = getIntent().getExtras();

        String user = bundle.getString("username");
        String pic = bundle.getString("picture");
        String email = bundle.getString("email");

        ImageView imageView = (ImageView)findViewById(R.id.imageInd);


        Picasso.get().load(pic).into(imageView);
        Button save = findViewById(R.id.save);
        Button like = findViewById(R.id.like);




        int start = pic.indexOf("F");
        int end  = pic.indexOf("?");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Feed");
        DatabaseReference newd = database.child(pic.substring(start+1,end)).child("likes");
        Log.d("likes", pic.substring(start+1,end));
        newd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                TextView likes = (TextView) findViewById(R.id.counter);
                Log.d("likes", snapshot.getValue().toString());
                likes.setText(snapshot.getValue().toString());  //prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Button open = findViewById(R.id.open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();

                String user = bundle.getString("username");
                String pic = bundle.getString("picture");

                int start = pic.indexOf("F");
                int end  = pic.indexOf("?");
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Feed");
                DatabaseReference info = database.child(pic.substring(start+1,end)).child("uploaded");
                //Log.d("likes", pic.substring(start+1,end));
                info.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Bundle bundle = getIntent().getExtras();

                        String email = bundle.getString("email");

                        String by;
                        by = snapshot.getValue().toString();
                        Intent i = new Intent(picture_layout.this, other_profile.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("user", by);
                        bundle2.putString("email", email);
                        i.putExtras(bundle2);
                        startActivity(i);



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



            }
        });




        DatabaseReference database_feed = FirebaseDatabase.getInstance().getReference("Feed");
        DatabaseReference toggle = database_feed.child(pic.substring(start+1,end));

        toggle.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    String saved = snapshot.getValue().toString();
                    if (saved.equals("True")) {
                        Button save = findViewById(R.id.save);

                        //Toast.makeText(picture_layout.this, "Unsaved", Toast.LENGTH_LONG).show();
                        //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        save.setBackgroundColor(Color.WHITE);
                        save.setTextColor(Color.parseColor("#d54444"));

                    }
                    else {
                        Button save = findViewById(R.id.save);

                        //Toast.makeText(picture_layout.this, "Unsaved", Toast.LENGTH_LONG).show();
                        //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        save.setBackgroundColor(Color.parseColor("#d54444"));
                        save.setTextColor(Color.WHITE);
                    }

                }
                catch(Exception e) {
                    Bundle bundle = getIntent().getExtras();
                    String user = bundle.getString("username");
                    String pic = bundle.getString("picture");
                    int start = pic.indexOf("F");
                    int end  = pic.indexOf("?");
                    DatabaseReference database_feed = FirebaseDatabase.getInstance().getReference("Feed");
                    DatabaseReference toggle = database_feed.child(pic.substring(start+1,end));
                    toggle.child(user).setValue("False");

                }






            }
            @Override
            public void onCancelled(DatabaseError databaseError) {




            }
        });

        DatabaseReference database_liked = FirebaseDatabase.getInstance().getReference("Feed");
        DatabaseReference toggle_like = database_liked.child(pic.substring(start+1,end));

        toggle_like.child(user + "likes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    String liked = snapshot.getValue().toString();
                    if (liked.equals("True")) {
                        Button like = findViewById(R.id.like);

                        //Toast.makeText(picture_layout.this, "Unsaved", Toast.LENGTH_LONG).show();
                        //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        like.setBackgroundColor(Color.WHITE);
                        like.setTextColor(Color.parseColor("#d54444"));

                    }
                    else {
                        Button like = findViewById(R.id.like);

                        //Toast.makeText(picture_layout.this, "Unsaved", Toast.LENGTH_LONG).show();
                        //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        like.setBackgroundColor(Color.parseColor("#d54444"));
                        like.setTextColor(Color.WHITE);
                    }

                }
                catch(Exception e) {
                    Bundle bundle = getIntent().getExtras();
                    String user = bundle.getString("username");
                    String pic = bundle.getString("picture");
                    int start = pic.indexOf("F");
                    int end  = pic.indexOf("?");
                    DatabaseReference database_liked = FirebaseDatabase.getInstance().getReference("Feed");
                    DatabaseReference toggle_like = database_liked.child(pic.substring(start+1,end));
                    toggle_like.child(user + "likes").setValue("False");

                }






            }
            @Override
            public void onCancelled(DatabaseError databaseError) {




            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                //save.setTextColor(Color.WHITE);

                Bundle bundle = getIntent().getExtras();
                String user = bundle.getString("username");
                String pic = bundle.getString("picture");
                int start = pic.indexOf("F");
                int end  = pic.indexOf("?");
                DatabaseReference database_feed = FirebaseDatabase.getInstance().getReference("Feed");
                DatabaseReference toggle = database_feed.child(pic.substring(start+1,end));

                toggle.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        String saved = snapshot.getValue().toString();
                        Log.d(saved,"piclayout");
                        if (saved.equals("True")) {
                            Button save = findViewById(R.id.save);

                            Toast.makeText(picture_layout.this, "Unsaved", Toast.LENGTH_LONG).show();
                            //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                            save.setBackgroundColor(Color.parseColor("#d54444"));
                            save.setTextColor(Color.WHITE);
                            Bundle bundle = getIntent().getExtras();
                            String user = bundle.getString("username");
                            String pic = bundle.getString("picture");
                            int start = pic.indexOf("F");
                            int end  = pic.indexOf("?");
                            DatabaseReference database_feed = FirebaseDatabase.getInstance().getReference("Feed");
                            DatabaseReference toggle = database_feed.child(pic.substring(start+1,end));
                            toggle.child(user).setValue("False");
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference(user);
                            database.child(pic.substring(start,end)).removeValue();


                        }
                        else {
                            Button save = findViewById(R.id.save);

                            Toast.makeText(picture_layout.this, "Saved", Toast.LENGTH_LONG).show();
                            //save.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                            save.setBackgroundColor(Color.WHITE);
                            save.setTextColor(Color.parseColor("#d54444"));
                            Bundle bundle = getIntent().getExtras();
                            String user = bundle.getString("username");
                            String pic = bundle.getString("picture");
                            int start = pic.indexOf("F");
                            int end  = pic.indexOf("?");
                            DatabaseReference database_feed = FirebaseDatabase.getInstance().getReference("Feed");
                            DatabaseReference toggle = database_feed.child(pic.substring(start+1,end));
                            toggle.child(user).setValue("True");
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference(user);
                            database.child(pic.substring(start,end)).child("file").setValue(pic);

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Log.d("collectionpic", pic);
                //int start = pic.indexOf("F");
                //int end  = pic.indexOf("?");


            }
        });
        //Button like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                //like.setTextColor(Color.WHITE);


                Bundle bundle = getIntent().getExtras();
                String user = bundle.getString("username");
                String pic = bundle.getString("picture");
                int start = pic.indexOf("F");
                int end  = pic.indexOf("?");
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Feed");
                DatabaseReference toggle = database.child(pic.substring(start+1,end));
                //Log.d("piclayout", user + "likes");
                toggle.child(user + "likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        String liked = snapshot.getValue().toString();
                        Log.d(liked,"piclayout");
                        if (liked.equals("True")) {
                            Button like = findViewById(R.id.like);

                            Toast.makeText(picture_layout.this, "Unliked", Toast.LENGTH_LONG).show();
                            //save.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                            like.setBackgroundColor(Color.parseColor("#d54444"));
                            like.setTextColor(Color.WHITE);
                            TextView likes = (TextView) findViewById(R.id.counter);
                            int num_likes = Integer.parseInt(likes.getText().toString());
                            num_likes--;
                            likes.setText(Integer.toString(num_likes));
                            Bundle bundle = getIntent().getExtras();
                            String user = bundle.getString("username");
                            String pic = bundle.getString("picture");
                            int start = pic.indexOf("F");
                            int end  = pic.indexOf("?");
                            DatabaseReference database_liked = FirebaseDatabase.getInstance().getReference("Feed");
                            DatabaseReference toggle_like = database_liked.child(pic.substring(start+1,end));
                            toggle_like.child(user + "likes").setValue("False");
                            DatabaseReference newd = database_liked.child(pic.substring(start+1,end)).child("likes");

                            newd.setValue(num_likes);




                        }
                        else {
                            Button like = findViewById(R.id.like);

                            Toast.makeText(picture_layout.this, "Liked", Toast.LENGTH_LONG).show();
                            //save.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                            like.setBackgroundColor(Color.WHITE);
                            like.setTextColor(Color.parseColor("#d54444"));
                            TextView likes = (TextView) findViewById(R.id.counter);
                            int num_likes = Integer.parseInt(likes.getText().toString());
                            num_likes++;
                            likes.setText(Integer.toString(num_likes));
                            Bundle bundle = getIntent().getExtras();
                            String user = bundle.getString("username");
                            String pic = bundle.getString("picture");
                            int start = pic.indexOf("F");
                            int end  = pic.indexOf("?");
                            DatabaseReference database_liked = FirebaseDatabase.getInstance().getReference("Feed");
                            DatabaseReference toggle_like = database_liked.child(pic.substring(start+1,end));
                            toggle_like.child(user + "likes").setValue("True");
                            DatabaseReference newd = database_liked.child(pic.substring(start+1,end)).child("likes");

                            newd.setValue(num_likes);



                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });




            }
        });
        //imageView.setImageBitmap(this.bitmapList.get(position));

    }
}
