package com.threepartballot.camilog.signatureapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignatureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        intent.putExtra("SCAN_CAMERA_ID", 0);

        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String encryptedBallotString = intent.getStringExtra("SCAN_RESULT");
                byte[] encryptedBallot = new BigInteger(encryptedBallotString).toByteArray();

                String privateKeyString = "";

                File privateKeyDir = getApplicationContext().getDir("privateKey", Context.MODE_PRIVATE);
                File privateKeyFile = new File(privateKeyDir, "privateKey.key");

                if (!privateKeyFile.exists()) {
                    // Hacer algo cuando no existe la firma
                }

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(privateKeyFile));
                    privateKeyString = reader.readLine();
                } catch (IOException e) {}

                Intent intent2 = new Intent(this, ShowSignatureActivity.class);

                try {
                    byte[] privateKeyBytes = new BigInteger(privateKeyString).toByteArray();
                    PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
                    PrivateKey privateKey = privateKeyFactory.generatePrivate(privateSpec);

                    Signature signature = Signature.getInstance("SHA256withRSA");
                    signature.initSign(privateKey, new SecureRandom());

                    signature.update(encryptedBallot);
                    byte[] sigBytes = signature.sign();

                    intent2.putExtra(ShowSignatureActivity.EXTRA_SIGNATURE, sigBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                startActivity(intent2);

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signature, menu);
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
