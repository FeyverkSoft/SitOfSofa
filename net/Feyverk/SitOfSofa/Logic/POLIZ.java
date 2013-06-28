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
     * @param formula формула которая будет представленна в виде POLIZ
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
                    || (formula.charAt(i) == '>')
                    || (formula.charAt(i) == '≥')
                    || (formula.charAt(i) == '≤')
                    || (formula.charAt(i) == '≠'))
            {
                outString.push(' ');
                if (stack.size() == 0)
                {
                    outString.push(' ');
                    stack.push(formula.charAt(i));
                    outString.push(' ');
                } else if (prior(formula.charAt(i)) > prior(String.valueOf(
                        stack.peek()).charAt(0)))
                {
                    outString.push(' ');
                    stack.push(formula.charAt(i));
                    outString.push(' ');
                } else
                {
                    while ((stack.size() != 0) && (prior(String.valueOf(stack.peek())
                            .charAt(0)) >= prior(formula.charAt(i))))
                    {
                        outString.push(stack.pop());
                    }
                    outString.push(' ');
                    stack.push(formula.charAt(i));
                }
                outString.push(' ');
            }
        }
        for (int i1 = 0; i1 < outString.size(); i1++)
        {
            rezNotation += outString.get(i1);
        }
        return rezNotation.replaceAll("  ", " ");
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
            case '*':
            case '/':
            case '%':
                return 3;
            case '&':
            case '<':
            case '>':
            case '≥':
            case '≤':
            case '≠':
            case '=':
            case '|':
            case '-':
            case '+':
                return 2;
            case '(':
            case ')':
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
        Stack<Double> stack = new Stack<>();
        LOG.info(poliz);
        String[] Split_POLIZ = poliz.split(" ");//режем текст по пробелам
        String str = "";
        for (String st : Split_POLIZ)
        {
            str = st.replace(" ", "");//На всякий случай грязный хак
            if (!"".equals(str))
            {
                if (!"+".equals(str) && !"-".equals(str) && !"*".equals(str) && !"/".equals(str) && !"%".equals(str))
                {
                    stack.push(Double.parseDouble(str));
                } else
                {
                    switch (str)
                    {
                        case "+":
                            stack.push(stack.pop() + stack.pop());
                            break;
                        case "-":
                            stack.push(stack.pop() - stack.pop());
                            break;
                        case "*":
                            stack.push(stack.pop() * stack.pop());
                            break;
                        case "/":
                            stack.push(stack.pop() / stack.pop());
                            break;
                        case "%":
                            stack.push(stack.pop() % stack.pop());
                            break;
                    }
                }
            }
        }
        if (stack.size() == 1)
        {
            return stack.pop();
        } else
        {
            return 0d;
        }
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
