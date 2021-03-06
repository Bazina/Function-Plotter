package task1.functionplotter.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class to parse input equations and get its derivative.
 */
public class EquationParser {
    private static String Equation;
    private static String RegularBuffer, DerivativeBuffer;

    /***
     * Sets the function to be evaluated later and get its derivative.
     * @param Input The desired Equation.
     * @throws IOException In case of any errors when getting the derivative from th python program.
     */
    public static void SetEquation(String Input) throws IOException {
        if (Input.contains("=")) Input = HandleEquals(Input).replaceAll(" ", "");

        Runtime rt = Runtime.getRuntime();
        Path filePath = Paths.get("src/main/java/task1/functionplotter/Parser/Derivative.py");
        String[] commands = {"python", filePath.toAbsolutePath().toString(), Input.replaceAll("\\^", "**")};
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        // Reads the output from the command.
        String s;
        while ((s = stdInput.readLine()) != null) {
            DerivativeBuffer = s.replaceAll("\\*\\*", "^").replaceAll("e", String.valueOf(Math.exp(1)));
        }
        RegularBuffer = Input.replaceAll("e", String.valueOf(Math.exp(1)));
    }

    /***
     * Interface to evaluate the function.
     * @param Value the value to evaluate the original function with.
     * @return the evaluation value.
     */
    public static double Evaluate(BigDecimal Value) {
        return Evaluate(Value, true);
    }

    /**
     * Interface to evaluate the derivative function.
     * @param Value the value to evaluate the derivative function with.
     * @return the evaluation value.
     */
    public static double EvaluateDerivative(BigDecimal Value) {
        return Evaluate(Value, false);
    }


    /***
     * Evaluates a given function with a specific value.
     * @param Value the value to evaluate with.
     * @param Regular if false then evaluate the derivative.
     * @return the result of the evaluation.
     */
    private static double Evaluate(BigDecimal Value, boolean Regular) {
        if (Regular) Equation = RegularBuffer.replaceAll("x", "(" + Value.toPlainString() + ")");
        else Equation = DerivativeBuffer.replaceAll("x", "(" + Value.toPlainString() + ")");

        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < Equation.length()) ? Equation.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < Equation.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(Equation.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = Equation.substring(startPos, this.pos);
                    x = parseFactor();
                    x = switch (func) {
                        case "log" -> Math.log(x);
                        case "sqrt" -> Math.sqrt(x);
                        case "sin" -> Math.sin(x);
                        case "cos" -> Math.cos(x);
                        case "tan" -> Math.tan(x);
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    /***
     * Handles the '=' sign.
     * @param input the equation.
     */
    private static String HandleEquals(String input) {
        String[] Equation = input.split("=");

        return Equation[0] + "-(" + Equation[1] + ")";
    }
}
