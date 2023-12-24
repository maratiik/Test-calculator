import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {

        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        in.close();

        String answ = calc(expression);
        System.out.println(answ);
    }


    public static boolean isNumeric(String str1, String str2) throws IllegalArgumentException {

        boolean s1 = false;
        boolean s2 = false;

        if (str1.contains("+") || str1.contains("-") || str1.contains("*") || str1.contains("/")) {
            throw new IllegalArgumentException("Cannot work with 3 or more numbers.");
        }

        if (str2.contains("+") || str2.contains("-") || str2.contains("*") || str2.contains("/")) {
            throw new IllegalArgumentException("Cannot work with 3 or more numbers.");
        }

        try {
            Double.parseDouble(str1);
            s1 = true;
        } catch(NumberFormatException e) {
            s1 = false;
        }

        try {
            Double.parseDouble(str2);
            s2 = true;
        } catch (NumberFormatException e) {
            s2 = false;
        }

        if (s1 != s2) {
            throw new IllegalArgumentException("Cannot use both Roman and Arabic numbers.");
        } else {
            return s1;
        }
    }


    public static void isValidRoman(String str1, String str2) throws IllegalArgumentException {

        if (str1.contains("+") || str1.contains("-") || str1.contains("*") || str1.contains("/")) {
            throw new IllegalArgumentException("Cannot work with 3 or more numbers.");
        }

        if (str2.contains("+") || str2.contains("-") || str2.contains("*") || str2.contains("/")) {
            throw new IllegalArgumentException("Cannot work with 3 or more numbers.");
        }

        return;
    }


    public static boolean isInteger(Double a, Double b) throws IllegalArgumentException {

        if ((a % 1) == 0 && (b % 1) == 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Cannot work with non-integer numbers.");
        }
    }


    public static String calc(String input) throws IllegalArgumentException {

        char operator = ' ';
        int operatorIndex = -1;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                operator = currentChar;
                operatorIndex = i;
                break;
            }
        }

        if (operatorIndex == -1) {
            throw new IllegalArgumentException("Invalid operator or no operator at all.");
        }

        String a = input.substring(0, operatorIndex);
        String b = input.substring(operatorIndex + 1);

        if (isNumeric(a, b) == true) {
            return calcNumeric(a, b, operator);
        } else {
            return calcRoman(a, b, operator);
        }
    }


    public static String calcNumeric(String a, String b, char operator) throws IllegalArgumentException {

        Double num1 = Double.parseDouble(a);
        Double num2 = Double.parseDouble(b);
        Double result = 0.0;

        isInteger(num1, num2);

        if(num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("A number in the expression is out of bounds.");
        }

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                break;
        }

        if ((result % 1) == 0) {
            return String.valueOf(result.intValue());
        } else {
            return String.valueOf(result);
        }
    }


    public static String calcRoman(String str1, String str2, char operator) throws IllegalArgumentException {

        Map<Character, Integer> m = new HashMap<>();
        Double num1 = 0.0;
        Double num2 = 0.0;
        Double result = 0.0;

        String a = str1.replaceAll("\\s+", "");
        String b = str2.replaceAll("\\s+", "");

        isValidRoman(a, b);

        m.put('I', 1);
        m.put('V', 5);
        m.put('X', 10);
        m.put('L', 50);
        m.put('C', 100);
        m.put('D', 500);
        m.put('M', 1000);

        for (int i = 0; i < a.length(); i++) {
            if (i < a.length() - 1 && m.get(a.charAt(i)) < m.get(a.charAt(i + 1))) {
                num1 -= m.get(a.charAt(i));
            } else {
                num1 += m.get(a.charAt(i));
            }
        }

        for (int i = 0; i < b.length(); i++) {
            if (i < b.length() - 1 && m.get(b.charAt(i)) < m.get(b.charAt(i + 1))) {
                num2 -= m.get(b.charAt(i));
            } else {
                num2 += m.get(b.charAt(i));
            }
        }

        if(num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("A number in the expression is out of bounds.");
        }

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                break;
        }

        String ones [] = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
        String tens [] = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        String hrns [] = {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};

        if ((result % 1) != 0) {
            throw new IllegalArgumentException("Roman doesn't have float numbers.");
        }

        if (result < 0) {
            throw new IllegalArgumentException("Roman doesn't have negative numbers.");
        }

        int resultInt = result.intValue();
        String answer = hrns[(resultInt%1000)/100] + tens[(resultInt%100)/10] + ones[resultInt%10];
        return answer;
    }
}
