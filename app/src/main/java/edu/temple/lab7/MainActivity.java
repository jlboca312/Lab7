package edu.temple.lab7;

import android.app.FragmentManager;
import android.net.Uri;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //global variables
    WebFragment webFrag;
    ArrayList<WebFragment> fragments; //array to hold all the fragments
    EditText urlBox;
    Button goButton;
    int currentFragIndex; //variable to keep track of index of webFragment array
    FragmentManager fm; //frag manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize fragment array
        fragments  = new ArrayList<>();

        //initialize fragment manager
        fm = getFragmentManager();

        //create the web fragment instance
        webFrag = new WebFragment();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar); //puts the app bar at the top

        //give variables and references to the url text box and button
        urlBox = (EditText) findViewById(R.id.URLBox);
        goButton = (Button) findViewById(R.id.goButton);

       //if there is a link do the implicit intent stuff
        Uri data = getIntent().getData();

        if(data != null){
            //convert the data url into a string
            String url = data.toString();

            //load the url into the fragment
            webFrag.setURL(url);
        }

        //set the go button action
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the string url from the url text box
                final String strURL = urlBox.getText().toString();

                //check if it starts with 'http://'
                if(strURL.startsWith("https://")){
                    //pass string url to WebFragment.java
                    webFrag.setURL(strURL);
                }else{ //else doesn't start with https...
                    //pass string url to WebFragment.java
                    webFrag.setURL("https://" + strURL);
                }

            }
        });


        //add to the fragment array
        fragments.add(webFrag);

        //initialize current index of fragment array
        currentFragIndex = fragments.size() - 1;


        //load the webfragment
        fm.beginTransaction().add(R.id.webViewFrag, fragments.get(currentFragIndex)).commit();
        fm.executePendingTransactions();

    }

    //puts buttons on app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar, menu);

        return true;
    }

    //deals with clicking the buttons on the app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Fragment oldFrag;
        final WebFragment newWebFrag;
        fm = getFragmentManager();

        //clear the url text box
        urlBox.setText("");

        switch(item.getItemId()){
            case R.id.addButton:
                //if user chose the add button
                Toast.makeText(this, "Created a new tab", Toast.LENGTH_SHORT).show();


                //remove current fragment from view
                oldFrag = fm.findFragmentById(R.id.webViewFrag);
                fm.beginTransaction().remove(oldFrag).commit();

                //make new WebFragment instance
                newWebFrag = new WebFragment();

                //give variables and references to the url text box and button
                urlBox = (EditText) findViewById(R.id.URLBox);
                goButton = (Button) findViewById(R.id.goButton);



                //set the go button action
                goButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //get the string url from the url text box
                        final String strURL = urlBox.getText().toString();

                        //check if it starts with 'http://'
                        if(strURL.startsWith("https://")){
                            //pass string url to WebFragment.java
                            newWebFrag.setURL(strURL);
                        }else{ //else doesn't start with https...
                            //pass string url to WebFragment.java
                            newWebFrag.setURL("https://" + strURL);
                        }

                    }
                });

                //add a new web fragment to the fragment array
                fragments.add(newWebFrag);
                currentFragIndex++; //increment index counter


                //load the webfragment
                fm.beginTransaction().add(R.id.webViewFrag, fragments.get(currentFragIndex)).commit();
                //fm.executePendingTransactions();


                return true;

            case R.id.leftButton:
                //if user clicked the left arrow button
                Toast.makeText(this, "Switching to the left tab", Toast.LENGTH_SHORT).show();


                //check if not last index
                if(currentFragIndex > 0){
                    currentFragIndex--; //decrement index

                    //load the previous fragment
                    fm.beginTransaction().replace(R.id.webViewFrag, fragments.get(currentFragIndex)).commit();
                    fm.executePendingTransactions();

                }else{
                    Toast.makeText(this, "There are no more tabs to the left", Toast.LENGTH_SHORT).show();
                }


                return true;

            case R.id.rightButton:
                //if user clicked the right arrow button
                Toast.makeText(this, "Switching to the right tab", Toast.LENGTH_SHORT).show();


                //check if not last index
                if(currentFragIndex < fragments.size() - 1){
                    currentFragIndex++; //decrement index

                    //load the previous fragment
                    fm.beginTransaction().replace(R.id.webViewFrag, fragments.get(currentFragIndex)).commit();
                    fm.executePendingTransactions();

                }else{
                    Toast.makeText(this, "There are no more tabs to the right", Toast.LENGTH_SHORT).show();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
