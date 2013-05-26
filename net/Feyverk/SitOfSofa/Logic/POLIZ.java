/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa.Logic;

import java.util.Stack;
import java.util.logging.Logger;

/**
 *
 * @author Пётр
 */
public class POLIZ
{

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
                    || (formula.charAt(i) == '!'))
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
                            .charAt(0)) >= prior(formula
                            .charAt(i))))
                    {
                        outString.push(stack.pop());
                    }
                    stack.push(formula.charAt(i));
                }
            }
        }
        for (int i1 = 0; i1 < outString.size(); i1++)
        {
            rezNotation = rezNotation + String.valueOf(outString.get(i1));
        }
        return rezNotation;
    }

// Метод получения приоритета операции при формировании обратной польской
// нотации
    private static int prior(char a)
    {
        switch (a)
        {
            case '!':
                return 4;
            case '&':
                return 3;
            case '|':
                return 3;
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
    private static final Logger LOG = Logger.getLogger(POLIZ.class.getName());
}
