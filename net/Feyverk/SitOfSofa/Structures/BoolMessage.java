package net.Feyverk.SitOfSofa.Structures;

import java.util.logging.Logger;

/**
 *
 * @author Пётр Класс Описывает структуру вида, разрешение, текст сообщения
 */
public class BoolMessage
{

    private boolean flag = false;
    private String message = "";

    public BoolMessage(Boolean Flag, String Message)
    {
        this.flag = Flag;
        this.message = Message;
    }

    public BoolMessage()
    {
        this.flag = false;
        this.message = "";
    }

    /**
     * Получает флаг, можно ли писать данное сообщение или нет
     *
     * @return Получает флаг, можно ли писать сообщение или нет
     */
    public boolean GetFlag()
    {
        return flag;
    }

    /**
     * Устанавливает флаг, можно ли писать данное сообщение или нет
     *
     * @return Устанавливает флаг, можно ли писать сообщение или нет
     */
    public void SetFlag(boolean flag)
    {
        this.flag = flag;
    }

    /**
     * Поучает сообщение которое будет выводится
     *
     * @return Поучает сообщение которое будет выводится
     */
    public String GetMessage()
    {
        if (this.message == null)
        {
            this.message = "";
        }
        return this.message;
    }

    /**
     * Устанавливает сообщение которое будет выводится
     *
     * @return Устанавливает сообщение которое будет выводится
     */
    public void SetMessage(String Message)
    {
        if (Message != null)
        {
            this.message = Message;
        } else
        {
            this.message = "";
        }
    }

    /**
     * Разбирает строку и возвращает созданный объект типа BoolMessage
     * построеный на её основе если это возможно
     *
     * @param string строка для разбора
     * @return объект типа BoolMessage построенный из строки
     */
    public BoolMessage parse(String string)
    {
        String[] _t = string.split(":");
        String s = "";
        for (int i = 1; i < _t.length; i++)
        {
            s += _t[i];
        }
        return new BoolMessage((_t[0].compareTo("true") == 0), s);
    }

    /**
     * Преобразует объект данного класса в его строковое представление
     *
     * @return Возвращает строковое предстваление объекта данного класса
     */
    @Override
    public String toString()
    {
        return (GetFlag() ? ":true:" : ":false:") + GetMessage();
    }
    private static final Logger LOG = Logger.getLogger(BoolMessage.class
            .getName());
}