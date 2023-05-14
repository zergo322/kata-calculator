import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        System.out.println("Введите выражение(Например: 1 + 4; VII * IX). Допустимые операнды: +, -, *, /");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) {
        Convert convert = new Convert();
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное значение, необходимо ввести 2 числа");
        }

        String num1 = parts[0];
        String operator = parts[1];
        String num2 = parts[2];

        if (!operator.contains("+") && !operator.contains("-") && !operator.contains("*") && !operator.contains("/")) {
            throw new IllegalArgumentException("Некорректный оператор.Введите допустимые операторы:'+','-','*','/'");
        }
        NumberSystem numberSystem1 = getType(num1);
        NumberSystem numberSystem2 = getType(num2);

        if (numberSystem1 != numberSystem2) {
            throw new IllegalArgumentException("Числа находятся в различных системах счисления");
        }
        int a, b;

        if (numberSystem1 == NumberSystem.ROMAN) {
            a = convert.romanToInt(parts[0]);
            b = convert.romanToInt(parts[2]);

        } else {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
        }
        if (a < 1 || a > 11 || b < 1 || b > 11) {
            throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10 включительно");
        }
        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Некорректный оператор");
        }

        if (numberSystem1 == NumberSystem.ROMAN) {
            return convert.intToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    public static NumberSystem getType(String number) {
        if (number.matches("[IVX]+")) {
            return NumberSystem.ROMAN;
        } else if (number.matches("[0-9]+")) {
            return NumberSystem.ARABIC;
        } else {
            throw new IllegalArgumentException("Неверно введены числа");
        }
    }
}

enum NumberSystem {
    ROMAN, ARABIC
}


class Convert {
    HashMap<String, Integer> romanNumbers = new HashMap<>();
    HashMap<Integer, String> arabicNumbers = new HashMap<>();


    public Convert() {
        romanNumbers.put("I", 1);
        romanNumbers.put("II", 2);
        romanNumbers.put("III", 3);
        romanNumbers.put("IV", 4);
        romanNumbers.put("V", 5);
        romanNumbers.put("VI", 6);
        romanNumbers.put("VII", 7);
        romanNumbers.put("VIII", 8);
        romanNumbers.put("IX", 9);
        romanNumbers.put("X", 10);


        arabicNumbers.put(1, "I");
        arabicNumbers.put(2, "II");
        arabicNumbers.put(3, "III");
        arabicNumbers.put(4, "IV");
        arabicNumbers.put(5, "V");
        arabicNumbers.put(6, "VI");
        arabicNumbers.put(7, "VII");
        arabicNumbers.put(8, "VIII");
        arabicNumbers.put(9, "IX");
        arabicNumbers.put(10, "X");
        arabicNumbers.put(20, "XX");
        arabicNumbers.put(30, "XXX");
        arabicNumbers.put(40, "XL");
        arabicNumbers.put(50, "L");
        arabicNumbers.put(60, "LX");
        arabicNumbers.put(70, "LXX");
        arabicNumbers.put(80, "LXXX");
        arabicNumbers.put(90, "XC");
        arabicNumbers.put(100, "C");

        for (int i = 11; i <= 99; i++) {
            if (!arabicNumbers.containsKey(i)) {
                int tens = i / 10;
                int ones = i % 10;
                String romanNum = "";
                if (tens == 4) {
                    romanNum = "XL";
                } else if (tens == 9) {
                    romanNum = "XC";
                } else {
                    if (tens >= 5) {
                        romanNum = "L";
                        tens -= 5;
                    }
                    for (int j = 0; j < tens; j++) {
                        romanNum += "X";
                    }
                }
                if (ones == 4) {
                    romanNum += "IV";
                } else if (ones == 9) {
                    romanNum += "IX";
                } else {
                    if (ones >= 5) {
                        romanNum += "V";
                        ones -= 5;
                    }
                    for (int j = 0; j < ones; j++) {
                        romanNum += "I";
                    }
                }
                arabicNumbers.put(i, romanNum);
            }
        }
    }

    public String intToRoman(int number) {
        String roman = "";
        roman = arabicNumbers.get(number);
        if (roman == null) {
            throw new IllegalArgumentException("Результат не может быть меньше 1");
        }
        return roman;
    }

    public int romanToInt(String str) {
        Integer arabic = romanNumbers.get(str);
        if (arabic == null) {
            throw new IllegalArgumentException("Диапазон римских цифр должен быть от I до X");
        }
        return arabic;
    }
}