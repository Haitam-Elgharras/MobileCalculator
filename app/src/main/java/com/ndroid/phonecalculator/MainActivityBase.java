package com.ndroid.phonecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public abstract class MainActivityBase extends AppCompatActivity {
    EditText inputEditText;
    TextView buttonResult;
    String lastResult = "";

    protected Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, button00;
    protected Button buttonMultiply, buttonDivide, buttonAdd, buttonSubtract, buttonDot, buttonC, buttonEquals, buttonDEL, buttonPower, buttonMod, buttonAns;



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // For landscape orientation, start MainLandscapeActivity
            Intent intent = new Intent(this, MainActivityLandscape.class);
            startActivityWithCurrentState(intent);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // For portrait orientation, start MainPortraitActivity
            Intent intent = new Intent(this, MainActivityPortrait.class);
            startActivityWithCurrentState(intent);
        }
    }

    protected void startActivityWithCurrentState(Intent intent) {
        // Get the current text and result
        String currentText = inputEditText.getText().toString();
        String currentResult = buttonResult.getText().toString();

        // Put the current text and result into the intent
        intent.putExtra("currentText", currentText);
        intent.putExtra("currentResult", currentResult);

        // Start the new activity
        startActivity(intent);
    }


    protected void initializeButtons() {
        // Initialize the buttons here
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button00 = findViewById(R.id.button00);

        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonDot = findViewById(R.id.buttonDot);
        buttonC = findViewById(R.id.buttonC);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonDEL = findViewById(R.id.buttonDEL);
        buttonPower = findViewById(R.id.buttonPower);
        buttonMod = findViewById(R.id.buttonMod);
        buttonAns = findViewById(R.id.buttonAns);

        // Set the onClickListener for each button
        button0.setOnClickListener(v -> appendNumber("0"));
        button1.setOnClickListener(v -> appendNumber("1"));
        button2.setOnClickListener(v -> appendNumber("2"));
        button3.setOnClickListener(v -> appendNumber("3"));
        button4.setOnClickListener(v -> appendNumber("4"));
        button5.setOnClickListener(v -> appendNumber("5"));
        button6.setOnClickListener(v -> appendNumber("6"));
        button7.setOnClickListener(v -> appendNumber("7"));
        button8.setOnClickListener(v -> appendNumber("8"));
        button9.setOnClickListener(v -> appendNumber("9"));
        button00.setOnClickListener(v -> {
            String existingText = inputEditText.getText().toString();
            if(existingText.isEmpty()) return;
            char lastChar = existingText.charAt(existingText.length() - 1);
            if (Character.isDigit(lastChar)) {
                String newText = existingText + "00";
                inputEditText.setText(newText);
            }
        });

        buttonMultiply.setOnClickListener(v -> appendOperator('*'));
        buttonDivide.setOnClickListener(v -> appendOperator('/'));
        buttonAdd.setOnClickListener(v -> appendOperator('+'));
        buttonSubtract.setOnClickListener(v -> appendOperator('-'));
        buttonMod.setOnClickListener(v -> appendOperator('%'));
        buttonAns.setOnClickListener(v -> {
            inputEditText.setText(lastResult);
        });

        buttonDot.setOnClickListener(v -> {
            String existingText = inputEditText.getText().toString();
            if(existingText.isEmpty()) return;
            // check if last character is a number
            char lastChar = existingText.charAt(existingText.length() - 1);
            if (Character.isDigit(lastChar)) {
                String newText = existingText + ".";
                inputEditText.setText(newText);
            }

        });
        buttonC.setOnClickListener(v -> {
            inputEditText.setText("");
            buttonResult.setText("");
        });

        buttonPower.setOnClickListener(v -> {
            String existingText = inputEditText.getText().toString();
            if(existingText.isEmpty()) return;
            String newText = existingText + "^";
            inputEditText.setText(newText);
        });

        buttonDEL.setOnClickListener(v -> {
            String existingText = inputEditText.getText().toString();
            if(existingText.isEmpty()) return;
            String newText = existingText.substring(0, existingText.length() - 1);
            inputEditText.setText(newText);
        });

        buttonEquals.setOnClickListener(v -> {
            String expression = inputEditText.getText().toString();
            try {
                double result = Evaluator.evaluateWithFunctions(expression);
                // to fix the result to 4 decimal places using toFixed
                result = Double.parseDouble(String.format("%.4f", result));
                this.buttonResult.setText(String.valueOf(result));
                lastResult = String.valueOf(result);
            } catch (IllegalArgumentException e) {
                this.buttonResult.setText("Error");
            }
        });
    }

    protected void appendNumber(String number) {
        // Get the current text from the inputEditText
        String existingText = inputEditText.getText().toString();

        // Format the new text with the existing text and the number
        String newText = existingText + number;

        // Set the new text to the inputEditText
        inputEditText.setText(newText);
    }

    protected void appendOperator(char operator) {
        String existingText = inputEditText.getText().toString();
        if (existingText.isEmpty()) {
            return;
        }
        char lastChar = existingText.charAt(existingText.length() - 1);
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '%') {
            String newText = existingText.substring(0, existingText.length() - 1) + operator;
            inputEditText.setText(newText);
        } else {
            String newText = existingText + operator;
            inputEditText.setText(newText);
        }
    }
}