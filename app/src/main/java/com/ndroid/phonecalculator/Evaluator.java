package com.ndroid.phonecalculator;

import android.util.Log;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

    public static double evaluate(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        String operators = "+-*/^%()!";

        if(isSingleValue(expression)) {
            return getSingleValue(expression);
        }

        StringTokenizer tokens = new StringTokenizer(expression, operators, true);

        while (tokens.hasMoreTokens()) {
            String nextToken = tokens.nextToken();

            if (nextToken.startsWith("(") && nextToken.endsWith(")")) {
                String innerExpression = nextToken.substring(1, nextToken.length() - 1);
                double innerValue = evaluate(innerExpression);
                values.push(innerValue);
            } else if (operators.contains(nextToken)) {
                if (nextToken.equals("-") && (values.isEmpty() || ops.peek() == '(')) {
                    nextToken += tokens.nextToken();
                    values.push(Double.parseDouble(nextToken));
                } else if (nextToken.charAt(0) == '(') {
                    ops.push(nextToken.charAt(0));
                } else if (nextToken.charAt(0) == ')') {
                    while (ops.peek() != '(') {
                        processOperator(ops, values);
                    }
                    ops.pop();
                } else if (nextToken.charAt(0) == '!') {
                    double value = values.pop();
                    values.push((double) factorial((int) value));
                } else {
                    while (!ops.empty() && ops.peek() != '(' && precedence(ops.peek()) >= precedence(nextToken.charAt(0))) {
                        processOperator(ops, values);
                    }
                    ops.push(nextToken.charAt(0));
                }
            } else if (nextToken.equals("π")) {
                values.push(Math.PI);
            } else if (nextToken.equals("e")) {
                values.push(Math.E);
            } else {
                values.push(Double.parseDouble(nextToken));
            }
        }

        while (!ops.empty()) {
            if (ops.peek() == '(') {
                ops.pop();
            } else {
                processOperator(ops, values);
            }
        }

        return values.pop();
    }
    // Check if the expression contains only one value or a single signed value
    private static boolean isSingleValue(String expression) {
        return !expression.matches(".*[+\\-*/^%()!].*") || expression.matches("[+\\-]?\\d+(\\.\\d+)?");
    }

    // Get the single value from the expression
    private static double getSingleValue(String expression) {
        Log.i("EvaluatorTest", "Single valuehahahah: " + expression);
        if (expression.equals("π")) {
            Log.i("EvaluatorTest", "π: " + Math.PI);
            return Math.PI;
        } else if (expression.equals("e")) {
            return Math.E;
        }
        // Check if the expression is a signed number
        else if (expression.matches("[+-]?\\d+(\\.\\d+)?")) {
            Log.i("EvaluatorTest", "Signed number: " + expression);
            Log.i("EvaluatorTest", "Double: " + Double.parseDouble(expression));
            return Double.parseDouble(expression);
        }
        throw new IllegalArgumentException("Invalid expression: " + expression);
    }


    // Add a method to calculate the result of a function
    private static double calculateFunction(String functionName, double value) {
        switch (functionName) {
            case "sin":
                return Math.sin(value);
            case "cos":
                Log.i("EvaluatorTest", "cos: " + Math.cos(value));
                return Math.cos(value);
            case "tan":
                return Math.tan(value);
            case "log":
                return Math.log10(value);
            case "ln":
                return Math.log(value);
            case "sqrt":
                return Math.sqrt(value);
            case "!":
                throw new IllegalArgumentException("Factorial should be handled immediately when it's encountered");
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

    // Add a method to calculate factorial
    private static int factorial(int n) {
        if (n == 0) return 1;
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double evaluateWithFunctions(String expression) {
        String[] functions = {"sin", "cos", "tan", "log", "ln", "sqrt"};

        for (String function : functions) {
            int index = expression.indexOf(function);
            while (index != -1) {
                int start = index + function.length() + 1; // Skip the function name and the '('
                int end = expression.indexOf(')', start);
                if (end == -1) {
                    throw new IllegalArgumentException("Missing closing parenthesis for function " + function);
                }

                String argumentStr = expression.substring(start, end);
                double argument = evaluate(argumentStr);
                double result = calculateFunction(function, argument);

                if(result<0)
                    expression = expression.substring(0, index) + "(" + result + ")" + expression.substring(end + 1);
                else
                    expression = expression.substring(0, index) + result + expression.substring(end + 1);

                // Look for the next occurrence of the function
                index = expression.indexOf(function);
            }
        }
        return evaluate(expression);
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '^':
            case '%':
                return 2;
            default:
                throw new IllegalArgumentException("Operator unknown: " + operator);
        }
    }

    private static void processOperator(Stack<Character> ops, Stack<Double> values) {

        if(ops.empty())
            return;

        char operator = ops.pop();
        double rightVal = values.pop();
        double leftVal = values.pop();
        double result;

        switch (operator) {
            case '+':
                result = leftVal + rightVal;
                break;
            case '-':
                result = leftVal - rightVal;
                break;
            case '*':
                result = leftVal * rightVal;
                break;
            case '/':
                if (rightVal == 0) throw new ArithmeticException("Cannot divide by zero");
                result = leftVal / rightVal;
                break;
            case '^':
                result = Math.pow(leftVal, rightVal);
                break;
            case '%':
                result = leftVal % rightVal;
                break;
            default:
                throw new IllegalArgumentException("Operator unknown: " + operator);
        }

        values.push(result);
    }

}

