package com.example.josip.appfinal2.Others;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.josip.appfinal2.BasicConcepts.BasicConceptsActivity;
import com.example.josip.appfinal2.LoginActivity;
import com.example.josip.appfinal2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class First3Activity extends AppCompatActivity {

    private static final String TAG = "First3Activity";
    private String userID;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    public Button btnBasicConcepts;
    public Button btnConditionalAndLoops;
    public Button btnArrays;
    public Button btnClassesAndObjects;
    public Button btnMoreOnClasses;
    public Button btnExceptions;
    public Button btnCertificate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first3);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
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
        //upotreba Globalne varijable, vatam u globalnu varijablu koji je botun stisnut na prethodnom activityju pa
        //pomoću njega generiram određeni broj botuna
        int Test = ((Globals) getApplicationContext()).Test;

        btnBasicConcepts=(Button)findViewById(R.id.btnBasicConcepts);

        btnConditionalAndLoops=(Button)findViewById(R.id.btnConditionalAndLoops);

        btnArrays=(Button)findViewById(R.id.btnArrays);
        btnClassesAndObjects=(Button)findViewById(R.id.btnClassesAndObjects);
        btnMoreOnClasses=(Button)findViewById(R.id.btnMoreOnClasses);
        btnExceptions=(Button)findViewById(R.id.btnExceptions);

        btnBasicConcepts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnBasicConcepts) {
                    Globals.getInstance().Test=1;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnConditionalAndLoops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnConditionalAndLoops) {
                    Globals.getInstance().Test=2;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnArrays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnArrays)
                {
                    Globals.getInstance().Test=3;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnClassesAndObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnClassesAndObjects)
                {
                    Globals.getInstance().Test=4;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnMoreOnClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnMoreOnClasses)
                {
                    Globals.getInstance().Test=5;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnExceptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnExceptions)
                {
                    Globals.getInstance().Test=6;
                    Intent intent=new Intent(First3Activity.this,BasicConceptsActivity.class);////izmitini main
                    startActivity(intent);
                }
            }
        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_signout) {
            mAuth.signOut();
            toastMessage("Signing Out...");
            Intent intent=new Intent(First3Activity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
