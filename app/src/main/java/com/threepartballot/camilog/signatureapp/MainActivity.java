package com.threepartballot.camilog.signatureapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button configurateButton = (Button) findViewById(R.id.configurate_button);
        final Button signButton = (Button) findViewById(R.id.sign_button2);

        configurateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
                startActivity(intent);
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean keyAvailable = checkIfKeyAvailable();
                signButtonAction(keyAvailable);
            }
        });

    }

    public boolean checkIfKeyAvailable(){
        File privateKeyDir = getApplicationContext().getDir("privateKey", Context.MODE_PRIVATE);
        File privateKeyFile = new File(privateKeyDir, "privateKey.key");

        if (!privateKeyFile.exists())
            return false;
        return true;
    }

    public void signButtonAction(boolean keyAvailable){
        if(keyAvailable){
            Intent intent = new Intent(MainActivity.this, SignatureActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "A Private Key has not been recorded yet!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, Gravity.CENTER_HORIZONTAL, 300);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
