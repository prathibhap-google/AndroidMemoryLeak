package com.example.pprabhakaran.androidmemoryleak;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView taskStatus;
    private static View view;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskStatus = (TextView) findViewById(R.id.task_done);


        Button taskAction = (Button) findViewById(R.id.task_action);
        taskAction.setOnClickListener((View v) ->{
            //Asynctask causing memmeory leak
            new SampleTask().execute();
            //Handlers causing memeory leak.
            createHandler();
        });

        //memory leak because of static views
        callStaticView();

    }

    private void callStaticView(){
        view = findViewById(R.id.task_text);
    }


    void createHandler() {
        new Handler() {
            @Override public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        }.postDelayed(new Runnable() {
            @Override public void run() {
                while(true);
            }
        }, Long.MAX_VALUE >> 1);
    }

    private class SampleTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskStatus.setText("Completed @: " + new Date().getTime());
        }

    }
}
