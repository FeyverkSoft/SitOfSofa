/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Пётр
 */
public class POLIZ
{
/*
    /// <summary>
    /// Парсер строки в ПОЛИЗ
    /// </summary>
    /// <param name="input">Входная строка</param>
    /// <returns>Результат строка представляющая запись вида ПОЛИЗ</returns>
    public String MyRevers(String input)
    {
        String myoutputString = " ";
        Integer sL = 0;
        Integer sW = 0;

        for (Integer i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) == '(')
            {
                sL = sL + 1;
            }
            if (input.charAt(i) == ')')
            {
                sW = sW + 1;
            }
        }
        if (sL != sW)
        {
            return " ";
        }
        Pattern rx = Pattern.compile("\\(|\\)|\\!|con|dil|euclid|hemming|clearset|lineeuclid|linehemming|∪|\\∩|([A-Z][A-Z]*|[А-Я][А-Я]*)");
        Matcher mc = rx.matcher(input);

        Pattern id = Pattern.compile("[A-Z][A-Z]*|[А-Я][А-Я]*"); // идентификаторы
        Pattern skobki = Pattern.compile("\\(|\\)"); // скобки
        String[] operators =
        {
            "(", ")", "!", "con", "dil", "euclid", "hemming", "clearset", "lineeuclid", "linehemming", "∩", "∪"
        }; // операторы по приоритету

        Pattern opers = Pattern.compile("\\(|\\)|\\!|con|dil|euclid|hemming|clearset|lineeuclid|linehemming|∪|\\∩"); // операторы

        Stack<String> stOper = new Stack();
        List<String> expr = new ArrayList();
        while (mc.find())
        {
            Matcher m1;
            m1 = id.matcher(mc.group());
            if (m1.Success)
            {
                expr.Add(m1.Value);
                continue;
            }
            m1 = skobki.Match(m.Value);
            if (m1.Success)
            {
                if (m1.Value == "(")
                {
                    stOper.Push(m1.Value);
                    continue;
                }
                String op = stOper.pop().toString();
                while (!"(".equals(op))
                {
                    expr.add(op);
                    op = stOper.pop().toString();
                }
                continue;
            }
            m1 = opers.Match(m.Value);
            if (m1.Success)
            {
                try
                {
                    while (Array.IndexOf(operators, m1.Value) > Array.IndexOf(operators, stOper.peek()))
                    {
                        if ("(".equals(stOper.peek().toString()))
                        {
                            break;
                        }
                        expr.add(stOper.pop().toString());
                    }
                } catch (Exception e)
                {
                    return "";
                }
                stOper.Push(m1.Value);
            }
        }
        while (stOper.size() != 0)
        {
            expr.add(stOper.pop().toString());
        }

        for (String s : expr)
        {
            myoutputString += s + " ";
        }
        return myoutputString.substring(1, myoutputString.length() - 2);
    }
     */
    private static final Logger LOG = Logger.getLogger(POLIZ.class.getName());
}
