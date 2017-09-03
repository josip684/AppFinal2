package com.example.josip.appfinal2.BasicConcepts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josip.appfinal2.LoginActivity;
import com.example.josip.appfinal2.Others.DetailActivity;
import com.example.josip.appfinal2.Others.DetailActivity2;
import com.example.josip.appfinal2.Others.DetailActivity3;
import com.example.josip.appfinal2.Others.First3Activity;
import com.example.josip.appfinal2.Others.Globals;
import com.example.josip.appfinal2.Others.TheoryModel;
import com.example.josip.appfinal2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BasicConceptsActivity extends AppCompatActivity {

    private static final String TAG = "BasicConceptsActivity";

    private final String URL_TO_HIT = "https://firebasestorage.googleapis.com/v0/b/appfinal2firebase.appspot.com/o/2808_13.11.json?alt=media&token=0634361f-042e-4687-8dec-38a120a123ff";
    private ListView lvTheory;
    private Button btnQuestions;

    private int start;
    private int end;//ovo su zamjene za globalne variable start i end

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_concepts);

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
                   // toastMessage("Successfully signed in with: " + user.getEmail());
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

        start=Globals.getInstance().Start;
        end=Globals.getInstance().End;

        int Test2 = ((Globals) getApplicationContext()).Test2;
        int Start = ((Globals) getApplicationContext()).Start;
        int End = ((Globals) getApplicationContext()).End;

        if(Globals.getInstance().Test==1) {
            Globals.getInstance().Start=0;
            Globals.getInstance().End=8;
        }
        else if(Globals.getInstance().Test==2) {
            Globals.getInstance().Start=8;
            Globals.getInstance().End=16;
        }
        else if(Globals.getInstance().Test==3) {
            Globals.getInstance().Start=16;
            Globals.getInstance().End=20;
        }
        else if(Globals.getInstance().Test==4){
            Globals.getInstance().Start=20;
            Globals.getInstance().End=34;
        }
        else if(Globals.getInstance().Test==5){
            Globals.getInstance().Start=34;
            Globals.getInstance().End=46;
        }
        else if(Globals.getInstance().Test==6) {
            Globals.getInstance().Start = 46;
            Globals.getInstance().End = 59;
        }
        start=Globals.getInstance().Start;
        end=Globals.getInstance().End;

        lvTheory = (ListView)findViewById(R.id.lvTheory);
        new JSONTask().execute(URL_TO_HIT);
    }

    public class JSONTask extends AsyncTask<String,String, List<TheoryModel> > {
        //AsyncTask omogućuje izvršavanje pozadinskih operacija i objavljivanje rezultata dijelovima korisničkog sučelja
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // dialog.show();
        }
        @Override
        protected List<TheoryModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";

                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("learnJava");

                List<TheoryModel> theoryModelList = new ArrayList<>();

                Gson gson = new Gson();//Gson library

                for(int i=start; i<end; i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TheoryModel theoryModel = gson.fromJson(finalObject.toString(), TheoryModel.class); // a single line json parsing using Gson
                    theoryModelList.add(theoryModel);
                }
                return theoryModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onPostExecute(final List<TheoryModel> result) {
            super.onPostExecute(result);
            //dialog.dismiss();
            if(result != null) {
                TheoryAdapter adapter = new TheoryAdapter(getApplicationContext(), R.layout.row, result);//omogućuje pristup podatkovnim stavkama
                lvTheory.setAdapter(adapter);
                lvTheory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TheoryModel theoryModel = result.get(position); // getting the model

                        Globals.getInstance().Test2=position;//todo Globalna koja pamti drugu razinu

                        if(theoryModel.getLayoutID().equals("1")){
                            Intent intent = new Intent(BasicConceptsActivity.this, DetailActivity.class);
                            intent.putExtra("theoryModel", new Gson().toJson(theoryModel));
                            startActivity(intent);
                        }
                        else if(theoryModel.getLayoutID().equals("2")){
                            Intent intent = new Intent(BasicConceptsActivity.this, DetailActivity2.class);
                            intent.putExtra("theoryModel", new Gson().toJson(theoryModel));
                            startActivity(intent);
                        }
                        else if(theoryModel.getLayoutID().equals("3")){
                            Intent intent = new Intent(BasicConceptsActivity.this, DetailActivity3.class);
                            intent.putExtra("theoryModel", new Gson().toJson(theoryModel));
                            startActivity(intent);
                        }

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class TheoryAdapter extends ArrayAdapter {

        private List<TheoryModel> theoryModelList;
        private int resource;
        private LayoutInflater inflater;
        public TheoryAdapter(Context context, int resource, List<TheoryModel> objects) {
            super(context, resource, objects);
            theoryModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
                holder.tv_Title = (TextView)convertView.findViewById(R.id.tv_Title);
                holder.tv_Subtitle = (TextView)convertView.findViewById(R.id.tv_Subtitle);

                holder.tvCast = (TextView)convertView.findViewById(R.id.tvCast);
                holder.tvStory = (TextView)convertView.findViewById(R.id.tvStory);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final ViewHolder finalHolder = holder;

            holder.tv_Title.setText(theoryModelList.get(position).get_title());
            holder.tv_Subtitle.setText(theoryModelList.get(position).get_subtitle());

            StringBuffer stringBuffer = new StringBuffer();
            for(TheoryModel.Cast cast : theoryModelList.get(position).getCastList()){
                stringBuffer.append(cast.getName() + ", ");
            }

            holder.tvCast.setText(stringBuffer);
            holder.tvStory.setText(theoryModelList.get(position).getStory());
            return convertView;
        }

        class ViewHolder{
            private TextView tv_Title;
            private TextView tv_Subtitle;
            private TextView tvCast;
            private TextView tvStory;
        }
    }

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new JSONTask().execute(URL_TO_HIT);
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent=new Intent(BasicConceptsActivity.this, First3Activity.class);
            startActivity(intent);
        }

        if (id == R.id.action_signout) {
            mAuth.signOut();
            toastMessage("Signing Out...");
            Intent intent=new Intent(BasicConceptsActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
