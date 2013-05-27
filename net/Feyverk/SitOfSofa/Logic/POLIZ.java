package net.Feyverk.SitOfSofa.Logic;

import java.util.Stack;
import java.util.logging.Logger;

/**
 *
 * @author Пётр
 */
public class POLIZ
{

    /**
     * Метод получения приоритета операции при формировании обратной польской
     * нотации
     *
     * @param formula формуля которая будет представленна в виде POLIZ
     * @return Строка представляющаяя формулу в виде ПОЛИЗ
     */
    public static String MyReverls(String formula)
    {
        String rezNotation = ""; // Результирующая запись
        Stack<Character> stack = new Stack<>();
        Stack<Character> outString = new Stack<>(); // Стек выходной строки
        for (int i = 0; i < formula.length(); i++)
        {
            if (formula.charAt(i) == ')')
            {
                while (String.valueOf(stack.peek()).charAt(0) != '(')
                {
                    outString.push(stack.pop()); // Записываем в выходную
                }
                stack.pop(); // Удаляем саму скобку
            }
            if (formula.charAt(i) == '(')
            {
                stack.push('(');
            }
            if ((formula.charAt(i) >= 'A') && (formula.charAt(i) <= 'Z')
                    || (formula.charAt(i) >= 'a') && (formula.charAt(i) <= 'z')
                    || (formula.charAt(i) >= '0') && (formula.charAt(i) <= '9'))
            {
                outString.push(formula.charAt(i));
            }
            if ((formula.charAt(i) == '+')
                    || (formula.charAt(i) == '-')
                    || (formula.charAt(i) == '/')
                    || (formula.charAt(i) == '*')
                    || (formula.charAt(i) == '|')
                    || (formula.charAt(i) == '%')
                    || (formula.charAt(i) == '&')
                    || (formula.charAt(i) == '!')
                    || (formula.charAt(i) == '=')
                    || (formula.charAt(i) == '<')
                    || (formula.charAt(i) == '>'))
            {
                if (stack.size() == 0)
                {
                    stack.push(formula.charAt(i));
                } else if (prior(formula.charAt(i)) > prior(String.valueOf(
                        stack.peek()).charAt(0)))
                {
                    stack.push(formula.charAt(i));
                } else
                {
                    while ((stack.size() != 0) && (prior(String.valueOf(stack.peek())
                            .charAt(0)) >= prior(formula.charAt(i))))
                    {
                        outString.push(stack.pop());
                    }
                    stack.push(formula.charAt(i));
                }
                outString.push(' ');
            }
        }
        for (int i1 = 0; i1 < outString.size(); i1++)
        {
            rezNotation += outString.get(i1);
        }
        return rezNotation;
    }

    /**
     * Приоритеты операторов
     *
     * @param a - оператор
     * @return приоритет оператора
     */
    private static int prior(char a)
    {
        switch (a)
        {
            case '!':
                return 4;
            case '&':
                return 2;
            case '<':
                return 2;
            case '>':
                return 2;
            case '=':
                return 2;
            case '|':
                return 2;
            case '*':
                return 3;
            case '/':
                return 3;
            case '%':
                return 3;
            case '-':
                return 2;
            case '+':
                return 2;
            case '(':
                return 1;
        }
        return 0;
    }

    /**
     * Вычисляет численное значение указаной ПОЛИЗ
     *
     * @param poliz запись выражения в виде ПОЛИЗ
     * @return число ответ
     */
    public static Double numbersCalculate(String poliz)
    {
        String[] _Temp = poliz.split(" ");
        return 0d;
    }

    /**
     * Вычисляет логическое значение указаной ПОЛИЗ
     *
     * @param poliz запись выражения в виде ПОЛИЗ
     * @return логическое значение результат truе/false
     */
    public static Boolean logicCalculate(String poliz)
    {
        String[] _Temp = poliz.split(" ");
        return false;
    }
    private static final Logger LOG = Logger.getLogger(POLIZ.class.getName());
}
