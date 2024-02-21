package com.ndroid.phonecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


public class MainActivityPortrait extends MainActivityBase {


    Button buttonBrackets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        inputEditText.setFocusable(false);
        buttonResult = findViewById(R.id.result);

        // Get the passed data
        Intent intent = getIntent();
        String currentText = intent.getStringExtra("currentText");
        String currentResult = intent.getStringExtra("currentResult");

        // Set the current text and result
        inputEditText.setText(currentText);
        buttonResult.setText(currentResult);

        initializeButtons();

        buttonBrackets = findViewById(R.id.buttonBrackets);
        buttonBrackets.setOnClickListener(v -> {
            String existingText = inputEditText.getText().toString();
            if (existingText.isEmpty()) {
                inputEditText.setText("(");
                return;
            }
            long openBrackets = existingText.chars().filter(ch -> ch == '(').count();
            long closeBrackets = existingText.chars().filter(ch -> ch == ')').count();
            String lastChar = existingText.substring(existingText.length() - 1);
            System.out.println(lastChar);
            if(lastChar.charAt(0) == '+' || lastChar.charAt(0) == '-' || lastChar.charAt(0) == '*' || lastChar.charAt(0) == '/' || lastChar.charAt(0) == '%' || lastChar.charAt(0) == '^' || lastChar.charAt(0) == '('){
                String newText = existingText + "(";
                inputEditText.setText(newText);
            }else if(openBrackets == closeBrackets || existingText.charAt(existingText.length() - 1) == '('){
                String newText = existingText + "(";
                inputEditText.setText(newText);
            }else if(openBrackets > closeBrackets){
                String newText = existingText + ")";
                inputEditText.setText(newText);

            }
        });

    }

}