package com.example.josip.appfinal2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Confirmation extends AppCompatActivity {

    private static final String TAG = "Confirmation";

    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btnVerify=(Button)findViewById(R.id.btnVerify);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeProfile();
                Intent i = new Intent(Confirmation.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    public void makeProfile(){
        String []first={"Basic Concepts", "Conditional And Loops", "Arrays", "Classes And Objects", "More on Classes", "Exceptions, Lists, Threads and Files"};
        String []second={"First", "Second", "Third", "Fourth", "Fifth", "Sixth","Seventh", "Eighth", "Ninth", "Tenth", "Eleventh", "Twelfth", "Thirteenth"};

        for(int i=0;i<8;i++){myRef.child("users").child(userID).child("First Activity").child(first[0]).child(second[i]).setValue(false);}
        for(int i=0;i<8;i++){myRef.child("users").child(userID).child("First Activity").child(first[1]).child(second[i]).setValue(false);}
        for(int i=0;i<4;i++){myRef.child("users").child(userID).child("First Activity").child(first[2]).child(second[i]).setValue(false);}
        for(int i=0;i<13;i++){myRef.child("users").child(userID).child("First Activity").child(first[3]).child(second[i]).setValue(false);}
        for(int i=0;i<13;i++){myRef.child("users").child(userID).child("First Activity").child(first[4]).child(second[i]).setValue(false);}
        for(int i=0;i<13;i++){myRef.child("users").child(userID).child("First Activity").child(first[5]).child(second[i]).setValue(false);}
        toastMessage("New Information has been saved.");
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
