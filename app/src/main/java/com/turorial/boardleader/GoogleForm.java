package com.turorial.boardleader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GoogleForm extends AppCompatActivity {
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";
    //input element ids found from the live form page
    public static final String EMAIL_KEY="entry.1824927963";
    public static final String NAME_KEY="entry.1877115667";
    public static final String LAST_NAME_KEY="entry.2006916086";
    public static final String PROJECT_LINK_KEY="entry.284483984";

    private final Context context = this;
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText githubLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Button sendButton = (Button)findViewById(R.id.confirm);
        email = (EditText)findViewById(R.id.email);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        githubLink = (EditText)findViewById(R.id.gitHubLink);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make sure all the fields are filled with values
                if(TextUtils.isEmpty(email.getText().toString()) ||
                        TextUtils.isEmpty(firstName.getText().toString()) ||
                        TextUtils.isEmpty(lastName.getText().toString()) ||
                        TextUtils.isEmpty(githubLink.getText().toString()) )
                {
                    Toast.makeText(context,"All fields are mandatory.",Toast.LENGTH_LONG).show();
                    return;
                }
                //Check if a valid email is entered
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                {
                    Toast.makeText(context,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                    return;
                }

                //Create an object for PostDataTask AsyncTask
                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL,email.getText().toString(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        githubLink.getText().toString())
                ;
            }
        });
    }


   private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String firstName = contactData[2];
            String lastName = contactData[3];
            String githubLink= contactData[4];
            String postBody="";

            try {

                postBody = EMAIL_KEY+"=" + URLEncoder.encode(email,"UTF-8") +
                        "&" + NAME_KEY + "=" + URLEncoder.encode(firstName,"UTF-8") +
                        "&" + LAST_NAME_KEY + "=" + URLEncoder.encode(lastName,"UTF-8")+
                        "&" + PROJECT_LINK_KEY + "=" + URLEncoder.encode(githubLink,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }
                  /*

            try {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost(url, postBody);
		}catch (Exception exception){
			result = false;
		}*/


            try{
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(context,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
        }

    }



}
