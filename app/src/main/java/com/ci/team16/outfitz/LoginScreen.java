package com.ci.team16.outfitz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginScreen extends AppCompatActivity {
    SignInButton signInButton;
    Button signOutButton;
    TextView statusTextView;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    //for text boxes
    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        txtEmailLogin = findViewById(R.id.user);
        txtPwd = findViewById(R.id.pass);
        firebaseAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
//                }
            }
        };
    }
    // Goes to the sign up screen
    public void btnUserRegistration_Click(View v) {
        Intent i = new Intent(LoginScreen.this, SignUpActivity.class);
        startActivity(i);
    }
    // Checks credentials, and logs in
    public void btnUserLogin_Click(View v) {
        if (!txtEmailLogin.getText().toString().isEmpty()) {
            (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG,"aaaa");
                                EditText user = findViewById(R.id.user);
                                EditText pass = findViewById(R.id.pass);
                                String temp_user = user.getText().toString();
                                //String temp_pass = pass.getText().toString();
                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



                                Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginScreen.this, AccountProfile.class);
                                Bundle bundle = new Bundle();
                                String person_id = currentFirebaseUser.getUid();
                                //Add your data to bundle
                                bundle.putString("username", temp_user);
                                bundle.putString("uid", person_id);
                                Log.d(TAG, person_id);


                                //Add the bundle to the intent
                                i.putExtras(bundle);
                                i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);
                            } else {
                                Log.e("ERRor", task.getException().toString());
                                Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(LoginScreen.this, "Authentecation failed", Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
//}

//EditText user = findViewById(R.id.user);
        //EditText pass = findViewById(R.id.pass);


//        create.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View view) {
////                EditText user = findViewById(R.id.user);
////                EditText pass = findViewById(R.id.pass);
////
////                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Accounts");
////
////
////                String temp_user = user.getText().toString();
////                String temp_pass = pass.getText().toString();
////
////                DatabaseReference userAcc = database.child(temp_user);
////                userAcc.child("user").setValue(temp_user);
////                userAcc.child("pass").setValue(temp_pass);
////
////
////
////                Toast.makeText(LoginScreen.this,
////                        "Account created!", Toast.LENGTH_SHORT).show();
////
////
////            }
////        });
////
////
////        sign.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View view) {
////
////
////
////
////                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Accounts");
////
////
////                database.addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////
////
////                        checkCreds((Map<String, Object>) dataSnapshot.getValue());
////
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError error) {
////                        // Failed to read value
////
////                    }
////                });
////
////
////            }
////        });
////    }
////
////
////    private void checkCreds(Map<String, Object> users) {
////        List<String> user_list = new ArrayList<String>();
////        List<String> pass_list = new ArrayList<String>();
////
////        EditText user = findViewById(R.id.user);
////        EditText pass = findViewById(R.id.pass);
////
////
////        for (Map.Entry<String, Object> entry : users.entrySet()) {
////
////            //Get user map
////            Map singleUser = (Map) entry.getValue();
////            user_list.add((String) singleUser.get("user"));
////            pass_list.add((String) singleUser.get("pass"));
////        }
////
////
////        String temp_user = user.getText().toString();
////        String temp_pass = pass.getText().toString();
////
////
////
////        for (int i = 0; i < user_list.size(); i++) {
////            if (temp_user.equals(user_list.get(i))) {
////                if (temp_pass.equals(pass_list.get(i))) {
////                    Toast.makeText(LoginScreen.this,
////                            "Login Successful", Toast.LENGTH_SHORT).show();
////                    Intent x = new Intent(getApplicationContext(),AccountProfile.class);
////                    Bundle bundle = new Bundle();
////
////                    //Add your data to bundle
////                    bundle.putString("username", temp_user);
////                    bundle.putString("password", temp_pass);
////
////
////                    //Add the bundle to the intent
////                    x.putExtras(bundle);
////                    startActivity(x);
////
////                }
////                else {
////                    Toast.makeText(LoginScreen.this,
////                            "Wrong Password!", Toast.LENGTH_SHORT).show();
////                }
////
////            }
////
////        }
////
////
////
////
////
////    }
////    public String BitMapToString(Bitmap bitmap){
////        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
////        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
////        byte [] b=baos.toByteArray();
////        String temp= Base64.encodeToString(b, Base64.DEFAULT);
////        return temp;
////    }
////}


