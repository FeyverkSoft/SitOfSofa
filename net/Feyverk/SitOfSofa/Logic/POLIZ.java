package net.Feyverk.SitOfSofa.Logic;

import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Logger;

/**
 *
 * @author Пётр
 */
public class POLIZ
{

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

    /**
     * Это разделитель?
     *
     * @param c символ для проверки
     * @return если это разделитель то true иначе false
     */
    static boolean isDelim(char c)
    {
        return c == ' ';
    }

    /**
     * Проверяет является ли указанный символ оператором
     *
     * @param c символ для проверки
     * @return если символ это оператор то true, иначе false
     */
    public static boolean isOperator(char c)
    {
        return ((c == '+')
                || (c == '-')
                || (c == '/')
                || (c == '*')
                || (c == '|')
                || (c == '%')
                || (c == '&')
                || (c == '!')
                || (c == '=')
                || (c == '<')
                || (c == '>')
                || (c == '≥')
                || (c == '≤')
                || (c == '≠')
                || (c == '^'));
    }

    /**
     * Вычисляет приоритет заданного оператора
     *
     * @param op оператор для получения его приоритета
     * @return приотритет оператора от 1 до 3 если -1 то ошибка
     */
    static int priority(char op)
    {
        switch (op)
        {
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
                return 1;
            case '*':
            case '/':
            case '%':
            case '^':
                return 2;
            case '!':
                return 3;
            default:
                return -1;
        }
    }

    /**
     * Вычисляет МАТЕМАТИЧЕСКОЕ значение в зависимости от оператора
     *
     * @param st
     * @param op
     */
    static void processMathOperator(LinkedList<Double> st, char op)
    {
        Double r = st.removeLast();
        Double l = st.removeLast();
        switch (op)
        {
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                st.add(l / r);
                break;
            case '%':
                st.add(l % r);
                break;
            case '^':
                st.add(Math.pow(l, r));
                break;
        }
    }

    /**
     * Вычисляет численное значение указаной математической записи
     *
     * @param poliz запись выражения в виде обычной мат записи
     * @return число ответ
     */
    public static Integer numbersCalculate(String s)
    {
        LinkedList<Double> st = new LinkedList<>();
        LinkedList<Character> op = new LinkedList<>();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (isDelim(c))
            {
                continue;
            }
            if (c == '(')
            {
                op.add('(');
            } else if (c == ')')
            {
                while (op.getLast() != '(')
                {
                    processMathOperator(st, op.removeLast());
                }
                op.removeLast();
            } else if (isOperator(c))
            {
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                {
                    processMathOperator(st, op.removeLast());
                }
                op.add(c);
            } else
            {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i)))
                {
                    operand += s.charAt(i++);
                }
                --i;
                st.add(Double.parseDouble(operand));
            }
        }
        while (!op.isEmpty())
        {
            processMathOperator(st, op.removeLast());
        }
        return (int) Math.round(st.get(0));
    }
    private static final Logger LOG = Logger.getLogger(POLIZ.class.getName());
}
