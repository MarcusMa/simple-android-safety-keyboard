package com.marcus.lib.simplesafetykeyboarddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marcus.lib.simplesafetykeyboarddemo.keyboard.KeyBoardActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    private static final int REQUEST_CODE_FOR_KEYBOARD = 1;

    private EditText mInput;

    private Button mBtn;
    private TextView mEncryptTextView, mDecryptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEncryptTextView = (TextView) findViewById(R.id.encrypt_text);
        mDecryptTextView = (TextView) findViewById(R.id.decrypt_text);
        mInput = (EditText) findViewById(R.id.test_input);
        mInput.setOnClickListener(this);
        mBtn = (Button) findViewById(R.id.open_safety_keyboard);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.open_safety_keyboard){
            Log.d(TAG, "button clicked.");
            Intent intent = new Intent(MainActivity.this, KeyBoardActivity.class);
            startActivityForResult(intent,REQUEST_CODE_FOR_KEYBOARD);
        }
        else if(v.getId() == R.id.test_input){
            Log.d(TAG, "input edit view clicked.");
        }
        else{
            // nothing to do.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FOR_KEYBOARD){
            Log.d(TAG, "get the Result.");
        }
    }
}
