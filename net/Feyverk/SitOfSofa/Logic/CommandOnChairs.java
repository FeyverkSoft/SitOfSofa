package net.Feyverk.SitOfSofa.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.Feyverk.SitOfSofa.SitOfSofa;
import net.Feyverk.SitOfSofa.Structures.TimeOut;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Пётр
 */
public class CommandOnChairs
{

    /**
     * Карта где ключ это игрок а значение время когда происходило событие
     */
    private HashMap<Player, TimeOut> timeOutMap;
    /**
     * Это стул? -1 нет, 0 скамейка, 1 кресло
     */
    private int isStool = -1;
    /**
     * Длинна скамейки
     */
    private int lenght = 0;
    /**
     * Блок на который тыкнул юзвер
     */
    private Block _block;
    /**
     * Игрок совершивший действие
     */
    private Player player;
    /**
     * список строк с возможными коммандами
     */
    private List<String> textOnSignList;
    /**
     * Список строк с коммандами
     */
    private List<String> commandList;

    /**
     * Задаёт крайний блок скамейки для которой выполняются комманды
     *
     * @param _block блок
     */
    public void setBlock(Block _block)
    {
        this._block = _block;
    }

    /**
     * Возвращает крайний блок скамейки для которой выполняются команды
     *
     * @return
     */
    public Block getBlock()
    {
        return _block;
    }

    /**
     * Длина скамейки для которой выполняется комманда
     *
     * @return Цифра от 0 до максимальной длины (смотри в конфиге)
     */
    public int getLenght()
    {
        if (this.lenght < 0)
        {
            this.lenght = 0;
        }
        return this.lenght;
    }

    /**
     * Карта где ключ это игрок а значение время когда происходило событие
     *
     * @return возвращает карту где ключ это игрок а значение время когда
     * происходило событие
     */
    public HashMap<Player, TimeOut> getTimeOutMap()
    {
        if (this.timeOutMap == null)
        {
            this.timeOutMap = new HashMap<>();
        }
        return this.timeOutMap;
    }

    /**
     * Проверяет является ли строка числом Int
     *
     * @param string строка для проверки
     * @return true является, false не является
     */
    private boolean isInt(String string)
    {
        try
        {
            Integer.parseInt(string);
        } catch (NumberFormatException e)
        {
            getPlayer().sendMessage(SitOfSofa.lang.getMessagePlugin("INCORRECT_COMMAND_ON_SIGN") + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Инициализирует объект типа CommandOnChairs значениями по умолчанию
     */
    public CommandOnChairs()
    {
        this.timeOutMap = new HashMap<>();
    }

    /**
     * Конструктор инициализирует объект класса значениями
     *
     * @param isStool параметр результата распознования стула
     */
    public CommandOnChairs(Integer isStool)
    {
        this.timeOutMap = new HashMap<>();
        setIsStool(isStool);
    }

    /**
     * Задать игрока
     *
     * @param player Игрок
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * Получает игрока
     *
     * @return
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * Устанавливает длину скамейки
     *
     * @param lenght Длина получиная в результате анализа
     */
    public void setLenght(int lenght)
    {
        if (lenght >= 0)
        {
            this.lenght = lenght;
        } else
        {
            this.lenght = 0;
        }
    }

    /**
     * Получает принадлежат ли эти комманды стулу или нет
     *
     * @return -1 нет, 0 - стул, 1 - диван
     */
    public int getIsStool()
    {
        if (this.isStool > 1 || this.isStool < -1)
        {
            this.isStool = -1;
        }
        return this.isStool;
    }

    /**
     * Получает весь текст с табличек
     *
     * @return весь текст с табличек
     */
    public List<String> getTextOnSignList()
    {
        if (this.textOnSignList == null)
        {
            this.textOnSignList = new ArrayList<>();
        }
        return this.textOnSignList;
    }

    /**
     * Спосок возможных комманд на сиденье
     *
     * @return Возвращает Список комманд
     */
    public List<String> getCommandList()
    {
        if (this.commandList == null)
        {
            this.commandList = extractCommand();
        }
        return this.commandList;
    }

    /**
     * Задаёт стул ли это или нет
     *
     * @param isStool -1 нет, 0 - стул, 1 - диван
     */
    public final void setIsStool(int isStool)
    {
        if (isStool > 1 || isStool < -1)
        {
            this.isStool = -1;
        }
        this.isStool = isStool;
    }

    /**
     * Задаёт весь текст с табличек стула
     *
     * @param textOnSignList Текст на табличках
     */
    public void setTextOnSignList(List<String> textOnSignList)
    {
        if (textOnSignList != null)
        {
            this.textOnSignList = textOnSignList;
        } else
        {
            this.textOnSignList = new ArrayList<>();
        }
    }

    /**
     * Возвращает Строки в которых содержатся комманды
     *
     * @return Возвращает Строки в которых содержатся комманды
     */
    private List<String> extractCommand()
    {
        List<String> _Temp = new ArrayList<>();
        String text = getTextOnSignList().toString().replace(" ", "").replace(",", "").toLowerCase();
        text = text.replace("_xp", getPlayer().getTotalExperience() + "").replace("_lvl", getPlayer().getLevel() + "").
                replace("_health", getPlayer().getHealth() + "").
                replace("_gm", getPlayer().getGameMode().getValue() + "").replace("_food", getPlayer().getFoodLevel() + "");
        text = text.substring(1, text.length() - 1);
        //LOG.info(text.substring(1, text.length() - 1));
        LOG.info(POLIZ.MyReverls("!(5+8)-9"));
        Matcher matcher = Pattern.compile("\\[([^\\[\\]]+?)\\]").matcher(text);
        //LOG.info(text);
        while (matcher.find())
        {
            text = matcher.group(1);
            _Temp.add(text);
        }
        //LOG.info(_Temp.toString());
        return _Temp;
    }

    /**
     * Может ли пользователь с данным ником сидеть на ступеньках?
     *
     * @param player Пользователь для которого производится проверка
     * @return false - пользователь не может сидеть
     */
    public boolean isPrivate(Player player)
    {
        boolean flag = true;
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@private:"))
            {
                flag = false;
                String[] _Temp = s.replace("_@private:", "").split(";");
                for (String _PlName : _Temp)
                {
                    //LOG.info(_PlName);
                    if (_PlName.equals(player.getDisplayName().toLowerCase()))
                    {
                        return true;
                    }
                }
            }

        }
        return flag;
    }

    /**
     * Возвращает надоли убивать люього игрока при посадке на кресло или нет
     *
     * @return true - игрока надо убить, false - оставить живым
     */
    public boolean isKillAll()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@kill"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает надо ли активировать редстоун механизмы по близости только
     * рядом с тем блоком на котором сидит игрок
     *
     * @return true - редстоун активируется, false - редстоун не активируется
     */
    public boolean isPowered()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@powered"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает надо ли активировать редстоун механизмы по близости на
     * протяжении всей ступеньки
     *
     * @return true - редстоун активируется, false - редстоун не активируется
     */
    public boolean isPoweredAll()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@poweredall"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает надо ли игрокам при посадке на данную супеньку давать предметы
     * или нет
     *
     * @return true - true игроки получаю предмет, false - игроки не получают
     * предмет
     */
    public boolean isGiveItem()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@give:"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Получает стак указанных предметов, на табличках
     *
     * @return Получает стак указанных предметов, на табличках
     */
    public Map<ItemStack, Long> getItemStack()
    {
        Map<ItemStack, Long> itemStackList = new HashMap<>();
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@give:"))
            {
                String[] _Temp = s.replace("_@give:", "").split(";");
                if (_Temp.length > 1)
                {
                    int DopMn = timeSet(_Temp[3]);
                    _Temp[3] = _Temp[3].replaceAll("[smh]", "");
                    int id = isInt(_Temp[0]) ? Integer.parseInt(_Temp[0]) : 0;
                    int data = isInt(_Temp[1]) ? Integer.parseInt(_Temp[1]) : 0;
                    int count = 0;
                    if (_Temp.length > 2)
                    {
                        count = isInt(_Temp[2]) ? (Integer.parseInt(_Temp[2]) >= 0 ? Integer.parseInt(_Temp[2]) : 0) : 0;
                    }
                    long timeOut = 0l;
                    if (_Temp.length > 3)
                    {
                        timeOut = isInt(_Temp[3]) ? Long.parseLong(_Temp[3]) * DopMn : 10000;
                    }
                    ItemStack i = new ItemStack(id, count);
                    i.getData().setData((byte) data);
                    itemStackList.put(i, timeOut);
                }
            }
        }
        LOG.info(itemStackList.toString());
        return itemStackList;
    }

    /**
     * Возвращает, добавлять ли xp игроку при посадке на этот стул или нет
     *
     * @return true - добавлять, false - не добавлять
     */
    public boolean isAddXp()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@addxp:"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает, надо ли менять игроку уровень здоровья при посадке
     *
     * @return true - добавлять, false - не добавлять
     */
    public boolean isSetHealh()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@sethealth:"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает, снести ли вобще весь XP игроку при посадке
     *
     * @return true - снести, false - не сносить
     */
    public boolean isClearXp()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@xpclear"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Будет ли при посадке на это кресло у игроков восстанавливаться здоровье?
     *
     * @return 1 - при посадке на данное крсло игроки восстанавливают флаг true
     * здоровье 0 - нет флаг false 2 - флага нету. По настройкам и пермишенам
     */
    public int isRestoreHealth()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@restorehealth:false"))
            {
                return 0;
            }
            if (s.contains("_@restorehealth:true"))
            {
                return 1;
            }
        }
        return 2;
    }

    /**
     * Может ли игрок иметь шанс получать предметы на этом стуле?
     *
     * @return true - да, false - нет
     */
    public boolean isChance()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@chance:false") || s.contains("_@chance:off"))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает множитель, который переводить число в минуты, секунды или часы
     *
     * @param time строка с временем
     * @return число
     */
    private int timeSet(String time)
    {
        int DopMn = 1000;
        if (time.contains("s"))
        {
            DopMn = 1000;
        } else if (time.contains("m"))
        {
            DopMn *= 60;
        } else if (time.contains("h"))
        {
            DopMn *= 360;
        }
        return DopMn;
    }

    /**
     * Получает количество xp которое надо добавить игроку (в том числе и
     * отрицательное ХД)
     *
     * @return [0] Возвращает количество xp которое надо добавить игроку (в том
     * числе и отрицательное ХД) [1] - таймаут в милесекундах
     */
    public int[] getXpCount()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@addxp:"))
            {
                String[] _Temp = s.replace("_@addxp:", "").split(";");
                if (_Temp.length > 0)
                {
                    int DopMn;
                    long timeOut = 0l;
                    if (_Temp.length > 1)
                    {
                        DopMn = timeSet(_Temp[1]);
                        _Temp[1] = _Temp[1].replaceAll("[smh]", "");
                        timeOut = isInt(_Temp[1]) ? Long.parseLong(_Temp[1]) * DopMn : 10000;
                    }
                    return new int[]
                    {
                        isInt(_Temp[0]) ? Integer.parseInt(_Temp[0]) : 0,
                        (int) timeOut
                    };
                }
            }
        }
        return new int[]
        {
            0, 0
        };
    }

    /**
     * Получает количество здоровья которое надо установить игроку
     *
     * @return Возвращает количество здоровья которое надо добавить игроку
     */
    public int getHealhCount()
    {
        List<String> com = getCommandList();
        for (String s : com)
        {
            if (s.contains("_@sethealth:"))
            {
                String[] _Temp = s.replace("_@sethealth:", "").split(";");
                if (_Temp.length > 0)
                {
                    return isInt(_Temp[0]) ? Integer.parseInt(_Temp[0]) : -1;
                }
            }
        }
        return -1;
    }

    public Boolean addXp()
    {
        if (isAddXp())
        {
            int[] t = getXpCount();
            if (!getTimeOutMap().containsKey(getPlayer()))
            {
                getTimeOutMap().put(getPlayer(), new TimeOut(0, 0, 0));
            }
            if (!(System.currentTimeMillis() - getTimeOutMap().get(getPlayer()).get_comXpAddTime() <= t[1]))
            {
                if (t[0] >= 0)
                {
                    getPlayer().giveExp(t[0]);
                } else
                {
                    int _Temp = getPlayer().getTotalExperience();
                    _Temp += t[0];
                    if (_Temp >= 0)
                    {
                        getPlayer().setTotalExperience(0);
                        getPlayer().setLevel(0);
                        getPlayer().setExp(0.0F);
                        getPlayer().giveExp(_Temp);
                    } else
                    {
                        getPlayer().setTotalExperience(0);
                        getPlayer().setLevel(0);
                        getPlayer().setExp(0.0F);
                        getPlayer().giveExp(0);
                    }
                }
                getTimeOutMap().get(getPlayer()).set_comXpAddTime(System.currentTimeMillis());
            }
        }
        return true;
    }

    /**
     * Сносит весь XP у игрока, есть есть команда на табличках
     *
     * @param player игрок
     * @param com списко команд на табличках
     * @return true-если снесли, false - если нет
     */
    public Boolean clearXp()
    {
        if (isClearXp())
        {
            getPlayer().setTotalExperience(0);
            getPlayer().setLevel(0);
            getPlayer().setExp(0.0F);
            return true;
        }
        return false;
    }

    /**
     * Игрок получает предметы указанные на табличках
     *
     * @param player игрок
     * @param com команды
     * @return true- если предметы получены, false -если что то пошло не так
     */
    public Boolean giveItem()
    {
        try
        {
            if (isGiveItem())
            {
                Map<ItemStack, Long> _Map = getItemStack();
                if (!getTimeOutMap().containsKey(getPlayer()))
                {
                    LOG.info("0");
                    getTimeOutMap().put(getPlayer(), new TimeOut());
                } else
                {
                    for (Map.Entry<ItemStack, Long> entry : _Map.entrySet())
                    {
                        if (!getTimeOutMap().get(getPlayer()).get_comGiveItem().containsKey(entry.getKey()))
                        {
                            getTimeOutMap().get(getPlayer()).get_comGiveItem().put(entry.getKey(), 0l);
                        }
                        if (!(System.currentTimeMillis() - getTimeOutMap().get(getPlayer()).get_comGiveItem().get(entry.getKey()) <= entry.getValue()))
                        {
                            getPlayer().getInventory().addItem(entry.getKey());
                            getTimeOutMap().get(getPlayer()).get_comGiveItem().remove(entry.getKey());
                            getTimeOutMap().get(getPlayer()).get_comGiveItem().put(entry.getKey(), System.currentTimeMillis());
                        }

                    }
                }
                return true;
            }
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] COnC giveItem {0}", e.getStackTrace().toString());
            return false;
        }
        return false;
    }

    public void setHealth()
    {
        if (isSetHealh())
        {
            int h = getHealhCount();
            if (h >= 0)
            {
                getPlayer().setHealth(h);
            }
        }
    }

    public void Run()
    {
        //Создание потока
        Thread Comm;
        Comm = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                giveItem();
                addXp();
                clearXp();
                setHealth();
                if (isKillAll())// если надо убить то убиваем
                {
                    getPlayer().setHealth(0);
                }
            }
        });
        Comm.start();	//Запуск потока
    }
    private static final Logger LOG = Logger.getLogger(CommandOnChairs.class.getName());
}
