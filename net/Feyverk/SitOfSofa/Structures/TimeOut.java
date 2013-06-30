/*
 * Описывает таймауты юзвера
 */
package net.Feyverk.SitOfSofa.Structures;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Пётр
 */
public class TimeOut
{

    /**
     * Время когда игрок создал сообщение о подъёме
     */
    private long MessageStandUpTime;
    /**
     * Время когда игрок создал сообщение о посадке
     */
    private long MessageSitdownTime;
    /**
     * Время когда игрок получил шанс найти предметы
     */
    private long ChanceTime;
    /**
     * Время когда игрок последний раз восстанавливал своё здоровье
     */
    private long timeOutRestoreHealth;
    /**
     * Логгер
     */
    private static final Logger LOG = Logger.getLogger(TimeOut.class.getName());
    /**
     * Время с последнего момента получения опыта комманда на табличках
     */
    private long _xpAddTime;
    /**
     * Время когда игрок получил предмет комманда на табличках
     */
    private Map<ItemStack, Long> _giveItem;

    /**
     * Время когда игрок получил предмет, комманда на табличках
     *
     * @return Время когда игрок получил предмет, комманда на табличках
     */
    public Map<ItemStack, Long> get_comGiveItem()
    {
        if (this._giveItem == null)
        {
            this._giveItem = new HashMap<>();
        }
        return this._giveItem;
    }

    /**
     * Время когда игрок получил опыт, комманда на табличках
     *
     * @return Время когда игрок получил опыт, комманда на табличках
     */
    public long get_comXpAddTime()
    {
        if (this._xpAddTime < 0)
        {
            this._xpAddTime = 0;
        }
        return this._xpAddTime;
    }

    /**
     * Время когда у игрока произошло событие нахождения предметов
     *
     * @return Возвращает Время когда у игрока произошло событие нахождения
     * предметов
     */
    public long getChanceTime()
    {
        if (this.ChanceTime < 0)
        {
            this.ChanceTime = 0;
        }
        return this.ChanceTime;
    }

    /**
     * Время когда игрок при посадке написал сообщение
     *
     * @return Возвращает Время когда игрок при посадке написал сообщение
     */
    public long getMessageSitdownTime()
    {
        if (this.MessageSitdownTime < 0)
        {
            this.MessageSitdownTime = 0;
        }
        return this.MessageSitdownTime;
    }

    /**
     * Время когда игрок при вставании написал сообщение
     *
     * @return Возвращает Время когда игрок при вставании написал сообщение
     */
    public long getMessageStandUpTime()
    {
        if (this.MessageStandUpTime < 0)
        {
            this.MessageStandUpTime = 0;
        }
        return this.MessageStandUpTime;
    }

    /**
     * Задаём время когда игроку выпал шанс получить предмет
     *
     * @param ChanceTime время в милесекундах
     */
    public final void setChanceTime(long ChanceTime)
    {
        if (ChanceTime >= 0)
        {
            this.ChanceTime = ChanceTime;
        } else
        {
            this.ChanceTime = 0l;
        }
    }

    /**
     * Задаёт время когда игрок получил предмет, написанный на табличке
     *
     * @param _giveItem карта где ключ стак предметом, значение таймаут когда игрок сможет их снова получить
     */
    public final void set_comGiveItem(Map<ItemStack, Long> _giveItem)
    {
        if (_giveItem != null)
        {
            this._giveItem = _giveItem;
        } else
        {
            this._giveItem = new HashMap<>();
        }
    }

    /**
     * Задаёт время когда игрок получил опыт, написанный на табличке
     *
     * @param _xpAddTime время от 0 до Long.MAX_VALUE
     */
    public final void set_comXpAddTime(long _xpAddTime)
    {
        if (_xpAddTime >= 0)
        {
            this._xpAddTime = _xpAddTime;
        } else
        {
            this._xpAddTime = 0l;
        }
    }

    /**
     * Задаем время когда игрок написал сообщение при посадке в чат
     *
     * @param MessageSitdownTime Время в милесекундах когда игрок написал
     * мессадж
     */
    public final void setMessageSitdownTime(long MessageSitdownTime)
    {
        if (MessageSitdownTime >= 0)
        {
            this.MessageSitdownTime = MessageSitdownTime;
        } else
        {
            this.MessageSitdownTime = 0l;
        }
    }

    /**
     * Задаем время когда игрок написал сообщение при вставании в чат
     *
     * @param MessageSitdownTime Время в милесекундах когда игрок написал
     * мессадж
     */
    public final void setMessageStandUpTime(long MessageStandUpTime)
    {
        if (MessageStandUpTime >= 0)
        {
            this.MessageStandUpTime = MessageStandUpTime;
        } else
        {
            this.MessageStandUpTime = 0l;
        }
    }

    /**
     * Получает время в милесекундах когда игрок последний раз лечился при
     * помощи стульев
     *
     * @return Возвращает время в милесекундах когда игрок последний раз лечился
     * при помощи стульев
     */
    public long getTimeOutRestoreHealth()
    {
        if (this.timeOutRestoreHealth < 0)
        {
            this.timeOutRestoreHealth = 0;
        }
        return this.timeOutRestoreHealth;
    }

    /**
     * Задаёт время когда игрок последний раз лечился при помощи стульев
     *
     * @param timeOutRestoreHealth время в милисекундах когда игрок лечился
     */
    public final void setTimeOutRestoreHealth(long timeOutRestoreHealth)
    {
        if (timeOutRestoreHealth >= 0)
        {
            this.timeOutRestoreHealth = timeOutRestoreHealth;
        } else
        {
            this.timeOutRestoreHealth = 0l;
        }
    }

    /**
     * Конструктор класса TimeOut, инициализирует объект класса TimeOut
     * указанными значениями
     *
     * @param MessageStandUpTime Время когда игрок встал со стула
     * @param MessageSitdownTime Время когда игрок сел на стул
     * @param ChanceTime время когда игрок получил шанс найти предмет
     * @param timeOutRestoreHealth время когда игрок последний раз
     * восстанавливал здоровье
     */
    public TimeOut(long MessageStandUpTime, long MessageSitdownTime, long ChanceTime, long timeOutRestoreHealth)
    {
        setChanceTime(ChanceTime);
        setMessageSitdownTime(MessageSitdownTime);
        setMessageStandUpTime(MessageStandUpTime);
        setTimeOutRestoreHealth(timeOutRestoreHealth);
    }

    /**
     * Конструктор класса TimeOut, инициализирует объект класса TimeOut
     * указанными значениями
     *
     * @param MessageStandUpTime Время когда игрок встал со стула
     * @param MessageSitdownTime Время когда игрок сел на стул
     * @param ChanceTime время когда игрок получил шанс найти предмет
     */
    public TimeOut(long MessageStandUpTime, long MessageSitdownTime, long ChanceTime)
    {
        setChanceTime(ChanceTime);
        setMessageSitdownTime(MessageSitdownTime);
        setMessageStandUpTime(MessageStandUpTime);
        set_comGiveItem(new HashMap<ItemStack, Long>());
        set_comXpAddTime(0);
    }

    /**
     * Конструктор класса TimeOut, инициализирует объект класса TimeOut
     * указанными значениями
     */
    public TimeOut()
    {
        setChanceTime(0l);
        setMessageSitdownTime(0l);
        setMessageStandUpTime(0l);
        setTimeOutRestoreHealth(0l);
    }

    /**
     * Конструктор класса TimeOut, инициализирует объект класса TimeOut
     * указанными значениями
     *
     * @param ChanceTime время когда игрок получил шанс найти предмет
     */
    public TimeOut(long ChanceTime)
    {
        setChanceTime(ChanceTime);
        setMessageSitdownTime(0l);
        setMessageStandUpTime(0l);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new TimeOut(getMessageStandUpTime(), getMessageSitdownTime(), getChanceTime(), getTimeOutRestoreHealth());
    }

    @Override
    public String toString()
    {
        return "MessageStandUpTime=" + getMessageStandUpTime() + " MessageSitdownTime=" + getMessageSitdownTime() + " ChanceTime=" + getChanceTime() + " TimeOutRestoreHealth=" + getTimeOutRestoreHealth();
    }
}
