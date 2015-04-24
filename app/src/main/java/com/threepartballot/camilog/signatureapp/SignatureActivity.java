package com.threepartballot.camilog.signatureapp;

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
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignatureActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        // Initialize SCAN application to retrieve encryptedBallot from the other device
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        intent.putExtra("SCAN_CAMERA_ID", 0);

        // Start SCAN activity
        startActivityForResult(intent, 0);
    }

    // Handle the result of the SCAN activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Id of the SCAN activity initialized before
        if (requestCode == 0) {

            // Handle successful scan
            if (resultCode == RESULT_OK) {
                // Retrieve encryptedBallot from the SCAN, transform it to a byte[] and store it
                String encryptedBallotString = intent.getStringExtra("SCAN_RESULT");
                byte[] encryptedBallot = new BigInteger(encryptedBallotString).toByteArray();

                // byte[] for the signature that will be shown to the other device
                byte[] sigBytes;

                // Retrieve privateKey from a local file, using an AssetManager and store it in a PrivateKey variable
                AssetManager assetManager = getApplicationContext().getAssets();
                PrivateKey privateKey = null;
                try {
                    ObjectInputStream oin_key = new ObjectInputStream(new BufferedInputStream(assetManager.open("privateKey.key")));
                    privateKey = (PrivateKey) oin_key.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Create intent to initialize next activity (ShowSignature)
                Intent intent2 = new Intent(this, ShowSignatureActivity.class);

                // Sign the encryptedBallot retrieved previously
                try {
                    // Set-up the instance of the scheme to sign, in this case, SHA1 with RSA
                    Signature signature = Signature.getInstance("SHA512withRSA");

                    // Pass the privateKey to sign, plus a random number
                    signature.initSign(privateKey, new SecureRandom());

                    // Pass the message to sign, in this case, encryptedBallot
                    signature.update(encryptedBallot);

                    // Generate signature
                    sigBytes = signature.sign();

                    // Pass the value if the signature to the next activity (ShowSignature)
                    intent2.putExtra(ShowSignatureActivity.EXTRA_SIGNATURE, sigBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Start ShowSignature
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
