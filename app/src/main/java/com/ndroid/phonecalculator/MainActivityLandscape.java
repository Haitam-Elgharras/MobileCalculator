package com.ndroid.phonecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class MainActivityLandscape extends MainActivityBase {

    Button buttonRightBracket, buttonLeftBracket, buttonSin, buttonCos, buttonTan, buttonLog, buttonLn, buttonSqrt, buttonFact, buttonPi, buttonE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landscape);

        // Initialize inputEditText
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

        // Initialize buttons
        initializeButtons();
        // Initialize functions
        initializeFunctions();
    }

    private void initializeFunctions() {
        buttonRightBracket = findViewById(R.id.buttonRightBracket);
        buttonLeftBracket = findViewById(R.id.buttonLeftBracket);
        buttonSin = findViewById(R.id.buttonSin);
        buttonCos = findViewById(R.id.buttonCos);
        buttonTan = findViewById(R.id.buttonTan);
        buttonLog = findViewById(R.id.buttonLog);
        buttonLn = findViewById(R.id.buttonLn);
        buttonSqrt = findViewById(R.id.buttonSqrt);
        buttonFact = findViewById(R.id.buttonFact);
        buttonPi = findViewById(R.id.buttonPi);
        buttonE = findViewById(R.id.buttonE);

        buttonRightBracket.setOnClickListener(v -> appendOperator(')'));
        buttonLeftBracket.setOnClickListener(v -> appendLeftBracket());
        buttonFact.setOnClickListener(v -> appendOperator('!'));

        buttonSin.setOnClickListener(v -> appendFunction("sin("));
        buttonCos.setOnClickListener(v -> appendFunction("cos("));
        buttonTan.setOnClickListener(v -> appendFunction("tan("));
        buttonLog.setOnClickListener(v -> appendFunction("log("));
        buttonLn.setOnClickListener(v -> appendFunction("ln("));
        buttonSqrt.setOnClickListener(v -> appendFunction("sqrt("));

        buttonPi.setOnClickListener(v -> appendNumber("π"));
        buttonE.setOnClickListener(v -> appendNumber("e"));
    }

    private void appendLeftBracket() {
        if (inputEditText.getText().toString().isEmpty()) {
            inputEditText.setText("(");
            return;
        }

        String existingText = inputEditText.getText().toString();
        char lastChar = existingText.charAt(existingText.length() - 1);
        if (lastChar == ')' || Character.isDigit(lastChar) || lastChar == 'e' || lastChar == 'π') {
            String newText = existingText + '*' + '(';
            inputEditText.setText(newText);
        }
        else {
            String newText = existingText + '(';
            inputEditText.setText(newText);
        }
    }

    private void appendFunction(String s) {
        if (inputEditText.getText().toString().isEmpty()) {
            inputEditText.setText(s);
            return;
        }

        String existingText = inputEditText.getText().toString();
        String lastChar = existingText.substring(existingText.length() - 1);
        if (lastChar.charAt(0) == ')' || Character.isDigit(lastChar.charAt(0)) || lastChar.charAt(0) == 'e' || lastChar.charAt(0) == 'π') {
            String newText = existingText + '*' + s;
            inputEditText.setText(newText);
        }
        else {
            String newText = existingText + s;
            inputEditText.setText(newText);
        }
    }


}
