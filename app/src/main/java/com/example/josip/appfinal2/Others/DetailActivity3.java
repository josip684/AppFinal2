package com.example.josip.appfinal2.Others;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josip.appfinal2.BasicConcepts.BasicConceptsActivity;
import com.example.josip.appfinal2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class DetailActivity3 extends AppCompatActivity {

    private static final String TAG = "DetailActivity3";
    private String userID;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private TextView tv_Title;
    private TextView tv_Subtitle;
    private TextView tvCast;
    private TextView tvStory;

    private TextView tvAD;
    private EditText etAD;
    private Button btnCheckAD;
    private Button btnNextAD;

    private String test;
    private String test2;

    private static RadioGroup rg;

    private static RadioButton rb_selected;
    private static RadioButton rb_choiceA;
    private static RadioButton rb_choiceB;
    private static RadioButton rb_choiceC;
    RadioButton rb;
    private  String checkedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);

        final MediaPlayer mp=MediaPlayer.create(this,R.raw.youwouldntbelieve);
        final MediaPlayer mp2=MediaPlayer.create(this,R.raw.served);

        int Start = ((Globals) getApplicationContext()).Start;
        int Test = ((Globals) getApplicationContext()).End;

        rg=(RadioGroup)findViewById(R.id.rg_choices);
        rb_choiceA=(RadioButton)findViewById(R.id.rb_choiceA);
        rb_choiceB=(RadioButton)findViewById(R.id.rb_choiceB);
        rb_choiceC=(RadioButton)findViewById(R.id.rb_choiceC);

        //TODO  GLOBALNE ZA PRVU RAZINU
        if(Globals.getInstance().Test==1)
        {
            test="Basic Concepts";
        }
        else if(Globals.getInstance().Test==2)
        {
            test="Conditional And Loops";
        }
        else if(Globals.getInstance().Test==3){
            test="Arrays";
        }
        else if(Globals.getInstance().Test==4){
            test="Classes And Objects";
        }
        else if(Globals.getInstance().Test==5){
            test="More on Classes";
        }
        else if(Globals.getInstance().Test==6){
            test="Exceptions, Lists, Threads and Files";
        }

        //TODO GLOBALNE ZA DRUGU RAZINU
        if (Globals.getInstance().Test2==0)
        {
            test2="First";
        }
        else if(Globals.getInstance().Test2==1)
        {
            test2="Second";
        }
        else if(Globals.getInstance().Test2==2)
        {
            test2="Third";
        }
        else if(Globals.getInstance().Test2==3)
        {
            test2="Fourth";
        }
        else if(Globals.getInstance().Test2==4)
        {
            test2="Fifth";
        }
        else if(Globals.getInstance().Test2==5)
        {
            test2="Sixth";
        }
        else if(Globals.getInstance().Test2==6)
        {
            test2="Seventh";
        }
        else if(Globals.getInstance().Test2==7)
        {
            test2="Eighth";
        }
        else if(Globals.getInstance().Test2==8)
        {
            test2="Ninth";
        }
        else if(Globals.getInstance().Test2==9)
        {
            test2="Tenth";
        }
        else if(Globals.getInstance().Test2==10)
        {
            test2="Eleventh";
        }
        else if(Globals.getInstance().Test2==11)
        {
            test2="Twelfth";
        }
        else if(Globals.getInstance().Test2==12)
        {
            test2="Thirteenth";
        }

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
                  //  toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                   // toastMessage("Successfully signed out.");
                }
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Showing and Enabling clicks on the Home/Up button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // setting up text views and stuff
        setUpUIViews();

        // recovering data from MainActivity, sent via intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("theoryModel"); // getting the model from MainActivity send via extras
            final TheoryModel theoryModel = new Gson().fromJson(json, TheoryModel.class);

            tv_Title.setText(theoryModel.get_title());
            tv_Subtitle.setText(theoryModel.get_subtitle());

            StringBuffer stringBuffer = new StringBuffer();
            for(TheoryModel.Cast cast : theoryModel.getCastList()){
                stringBuffer.append(cast.getName() + ", ");
            }

            tvCast.setText(stringBuffer);
            tvStory.setText(theoryModel.getStory());
            tvAD.setText(theoryModel.getQuestion());

            rb_choiceA.setText((theoryModel.getChoiceA()));
            rb_choiceB.setText((theoryModel.getChoiceB()));
            rb_choiceC.setText((theoryModel.getChoiceC()));

            btnCheckAD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                    if(rb_choiceA.isChecked()&& rb_choiceA.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#00FF00"));
                        mp.start();
                        myRef.child("users").child(userID).child("First Activity").child(test).child(test2).setValue(true);

                    }
                    else if(rb_choiceA.isChecked()&& !rb_choiceA.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#ff0000"));
                        mp2.start();
                        etAD.setText("");
                    }
                    /////////////////////////////
                    else if(rb_choiceB.isChecked()&& rb_choiceB.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#00FF00"));
                        mp.start();
                        myRef.child("users").child(userID).child("First Activity").child(test).child(test2).setValue(true);
                    }
                    else if(rb_choiceB.isChecked()&& !rb_choiceB.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#ff0000"));
                        mp2.start();
                        etAD.setText("");
                    }
                    //////////////////////
                    else if(rb_choiceC.isChecked()&& rb_choiceC.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#00FF00"));
                        mp.start();
                        myRef.child("users").child(userID).child("First Activity").child(test).child(test2).setValue(true);
                    }
                    else if(rb_choiceC.isChecked()&& !rb_choiceC.getText().toString().equals(theoryModel.getCorrectAnswer()))
                    {
                        btnCheckAD.setTextColor(Color.parseColor("#ff0000"));
                        mp2.start();
                        etAD.setText("");
                    }

                }catch(Exception e)
                    {
                        toastMessage("Netočan odgovor!!!");
                        mp2.start();
                    }
                }
            });

            btnNextAD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DetailActivity3.this,BasicConceptsActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
    private void setUpUIViews() {
        tv_Title = (TextView)findViewById(R.id.tv_Title);
        tv_Subtitle = (TextView)findViewById(R.id.tv_Subtitle);

        tvCast = (TextView)findViewById(R.id.tvCast);
        tvStory = (TextView)findViewById(R.id.tvStory);

        tvAD=(TextView)findViewById(R.id.tvAD);
        etAD=(EditText)findViewById(R.id.etAD);
        btnCheckAD=(Button)findViewById(R.id.btnCheckAD);
        btnNextAD=(Button)findViewById(R.id.btnNextAD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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

    public void rbclick(View v)
    {
        String checkedButton;
        int radiobuttonID=rg.getCheckedRadioButtonId();

        rb=(RadioButton)findViewById(radiobuttonID);

        if(rb_choiceA.isChecked())
        {
            checkedButton=rb_choiceA.toString();
        }
        else if(rb_choiceB.isChecked())
        {
            checkedButton=rb_choiceB.toString();
        }
        else
        {
            checkedButton=rb_choiceC.toString();
        }

        //Toast.makeText(getBaseContext(),rb.getText(),Toast.LENGTH_LONG).show();
    }

}
