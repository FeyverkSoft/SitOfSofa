package net.Feyverk.SitOfSofa.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.Feyverk.SitOfSofa.EventListener;
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
     * Блок на который сел юзер
     */
    private Block SitingBlock;
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
     * Задаёт блок скамейки на который сел юзер
     *
     * @param _block блок
     */
    public void setSitingBlock(Block _block)
    {
        this.SitingBlock = _block;
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
     * Возвращает блок скамейки на который сел юзер
     *
     * @return
     */
    public Block getSitingBlock()
    {
        return SitingBlock;
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
     * Возвращает новую строку полученную в результате пременения анализа на
     * наличе формул
     *
     * @param text
     * @return Возвращает новую строку полученную в результате пременения
     * анализа на наличе формул
     */
    String MatimatAnalis(String text)
    {
        String _TempText = text;
        //Выдёргиваем математические выражаения
        Matcher matcher = Pattern.compile("(([\\+\\-\\/\\*\\^]?)([(]*(([(]?(([+-]?\\d+[.,]?(\\d+)?)([e][+-]\\d+)?)[)]?)|([(]?value[)]?))[)]*)?(([(]*([(]?(([+-]?\\d+[.,]?(\\d+)?)([e][+-]\\d+)?)[)]?)|([(]?value[)]?))[)]*)?([\\+\\-\\/\\*\\^])([(]*(([(]?(([+-]?\\d+[.,]?(\\d+)?)([e][+-]\\d+)?)[)]?)|([(]?value[)]?))[)]*))+").matcher(text);
        while (matcher.find())
        {
            String text1 = matcher.group(1);
            if (text1.charAt(0) == '(')
            {
                text1 = text1.substring(1);
            } else if (text1.charAt(text1.length() - 1) == ')')
            {
                text1 = text1.substring(0, text1.length() - 1);
            }
            //LOG.info(text1);
            String num = POLIZ.numbersCalculate(text1).toString();
            //LOG.info(num);
            _TempText = _TempText.replace(text1, num);
        }
        //LOG.info(_TempText);
        return _TempText;
    }

    /**
     * Возвращает Строки в которых содержатся комманды
     *
     * @return Возвращает Строки в которых содержатся паскалеподобные комманды
     */
    private List<String> extractCommand()
    {
        List<String> _Temp = new ArrayList<>();
        String _Text = "", ParseText = "";
        //склеиваем в строку
        for (String s : getTextOnSignList())
        {
            _Text += s;
        }

        //заменяем объёмные операторы на мелкие аналоги
        _Text = _Text.replace(" ", "").toLowerCase().
                replace(">=", "≥").replace("<=", "≤").replace("!=", "≠").replace("==", "=").replace("&&", "&").replace("||", "|").replace("++", "+1").replace("--", "-1");
        _Text = _Text.substring(1, _Text.length() - 1);

        for (Integer i = 0; i < _Text.length(); i++)
        {
            if (_Text.charAt(i) == '_' && i + 1 < _Text.length())
            {
                if (_Text.charAt(i + 1) == 'i' || _Text.charAt(i + 1) == 'e' || _Text.charAt(i + 1) == 'w')
                {
                    while (_Text.charAt(i) != '{' && i < _Text.length())
                    {
                        ParseText += _Text.charAt(i);
                        i++;
                    }
                    _Temp.add(ParseText);
                    ParseText = "";
                    //начало блока
                    _Temp.add("_BEGIN");
                }
            }

            // окончание блока
            if (_Text.charAt(i) == '}')
            {
                _Temp.add("_END");
            }
            //вычлиняю саму комманду
            if (_Text.charAt(i) == '[')
            {
                i++;
                while (_Text.charAt(i) != ']' && i < _Text.length())
                {
                    ParseText += _Text.charAt(i);
                    i++;
                }
                _Temp.add(ParseText);
                ParseText = "";
            }

        }
        LOG.info(_Temp.toString());
        return _Temp;
    }

    /**
     * Возвращает строку к которой были пременены определенные правила
     * преобразующие все вычисления в том числе и логически вычисленные
     * константы
     *
     * @param s строка для обработки
     * @return обработанная строка
     */
    private String stringProcessing(String s)
    {
        String _Temp = s.replace("_xp", getPlayer().getTotalExperience() + "").replace("_lvl", getPlayer().getLevel() + "").
                replace("_health", getPlayer().getHealth() + "").
                replace("_gm", getPlayer().getGameMode().getValue() + "").replace("_food", getPlayer().getFoodLevel() + "");
        _Temp = MatimatAnalis(_Temp);

        return _Temp;
    }

    /**
     * Выполняет комманды но не операторы ветвления и цикла
     *
     * @return Если комманды выполнена без ошибки то true иначе false
     */
    private boolean ExecuteCommand(String[] _Temp)
    {
        if (_Temp[0].contains("_@private"))
        {
            Boolean privatefalg = false;
            _Temp[0] = _Temp[0].replace("_@private:", "");
            String[] _Names = _Temp[0].split(",");
            //есть ли такой юзер в пириватном списке?
            for (String t : _Names)
            {
                if (t.equals(getPlayer().getName()))
                {
                    privatefalg = true;
                    break;
                }
            }
            //если есть то разрешаем садится
            if (privatefalg)
            {
            } else //иначе поднимаем
            {
                SitOfSofa.powered.poweredDisable(getPlayer());
            }
            return true;
        }
        if (_Temp[0].contains("_@give"))
        {
            giveItem(_Temp[0]);
            return true;
        }
        if (_Temp[0].contains("_@addxp"))
        {
            if (_Temp.length > 0)
            {
                _Temp[0] = _Temp[0].replace("_@addxp:", "");
                int[] t = getXpCount(_Temp);
                addXp(t[0], t[1]);
            }
            return true;
        }
        if (_Temp[0].contains("_@sethealth"))
        {
            _Temp[0] = _Temp[0].replace("_@sethealth:", "");
            int h = getHealhCount(_Temp);
            if (h >= 0)
            {
                getPlayer().setHealth(h);
            }
            return true;
        }
        if (_Temp[0].contains("_@xpclear"))
        {
            getPlayer().setTotalExperience(0);
            getPlayer().setLevel(0);
            getPlayer().setExp(0.0F);
            return true;
        }
        if (_Temp[0].contains("_@restorehealth"))
        {
            if (_Temp[0].contains("_@restorehealth:true"))
            {
                getPlayer().setHealth(20);
            }
            return true;
        }
        if (_Temp[0].equals("_@kill"))
        {
            getPlayer().setHealth(0);
            return true;
        }
        if (_Temp[0].equals("_@powered"))
        {
            SitOfSofa.powered.poweredEnable(getSitingBlock(), getBlock(), getPlayer(), getLenght(), false);
            return true;
        }
        if (_Temp[0].equals("_@poweredall"))
        {
            SitOfSofa.powered.poweredEnable(getSitingBlock(), getBlock(), getPlayer(), getLenght(), false);
            return true;
        }
        return false;
    }

    /**
     * Метод выполняет комады описанные на стуле для игрока.
     */
    public void ExecuteCommands()
    {
        List<String> CommandsList = extractCommand();
        Boolean chanceflag = false;
        for (int i = 0; i < CommandsList.size(); i++)
        {
            String processcom = stringProcessing(CommandsList.get(i));
            if (!CommandsList.get(i).contains("_if") || !CommandsList.get(i).contains("_begin") || !CommandsList.get(i).contains("_end"))
            {
                String[] _Temp = processcom.split(",");
                if (_Temp[0].contains("_@chance"))
                {
                    if (_Temp[0].contains("_@chance:false") || _Temp[0].contains("_@chance:off"))
                    {
                        chanceflag = false;
                    } else if (_Temp[0].contains("_@chance:true") || _Temp[0].contains("_@chance:on"))
                    {
                        chanceflag = true;
                    }
                } else if (ExecuteCommand(_Temp) == false)
                {
                    LOG.log(Level.WARNING, "[SitOfSofa] Prograam on chair Error command{0}", _Temp.toString());
                }
            } else
            {
                processcom = stringProcessing(CommandsList.get(i));
                if (processcom.contains("_if(false)"))
                {
                    while (processcom.contains("_end") || processcom.contains("_else") || i < CommandsList.size())
                    {
                        i++;
                    }
                    i--;
                    if (processcom.contains("_else"))
                    {
                        while (processcom.contains("_end") || i < CommandsList.size())
                        {
                            i++;
                            String[] _Temp = processcom.split(",");
                            if (_Temp[0].contains("_@chance"))
                            {
                                if (_Temp[0].contains("_@chance:false") || _Temp[0].contains("_@chance:off"))
                                {
                                    chanceflag = false;
                                } else if (_Temp[0].contains("_@chance:true") || _Temp[0].contains("_@chance:on"))
                                {
                                    chanceflag = true;
                                }
                            } else if (ExecuteCommand(_Temp) == false)
                            {
                                LOG.log(Level.WARNING, "[SitOfSofa] Prograam on chair Error command{0}", _Temp.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Получает стак указанных предметов, на табличках
     *
     * @return Получает стак указанных предметов, на табличках
     */
    public Map<ItemStack, Long> getItemStack(String _Temps)
    {
        Map<ItemStack, Long> itemStackList = new HashMap<>();
        String[] _Temp = _Temps.replace("_@give:", "").split(",");
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
        //LOG.info(itemStackList.toString());
        return itemStackList;
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
     * @param s - МАССИВ СТРОК из которого выдераем необходимое количество xp
     * @return [0] Возвращает количество xp которое надо добавить игроку (в том
     * числе и отрицательное ХД) [1] - таймаут в милесекундах
     */
    private int[] getXpCount(String[] _Temp)
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

    /**
     * Добавлет указанное кол-во опыта юзеру
     *
     * @param xp - количество опыта которое надо добавить юзеру
     * @param timeout - таймаут перед тем как пользователю можно будет добавить
     * опыт
     */
    protected void addXp(int xp, int timeout)
    {
        if (!getTimeOutMap().containsKey(getPlayer()))
        {
            getTimeOutMap().put(getPlayer(), new TimeOut(0, 0, 0));
        }
        if (!(System.currentTimeMillis() - getTimeOutMap().get(getPlayer()).get_comXpAddTime() <= timeout))
        {
            if (xp >= 0)
            {
                getPlayer().giveExp(xp);
            } else
            {
                int _Temp = getPlayer().getTotalExperience();
                _Temp += xp;
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

    /**
     * Получает количество здоровья которое надо установить игроку
     *
     * @param s - строка из которой выдераем необходимое число
     * @return Возвращает количество здоровья которое надо добавить игроку
     */
    private int getHealhCount(String[] _Temp)
    {
        if (_Temp.length > 0)
        {
            return isInt(_Temp[0]) ? Integer.parseInt(_Temp[0]) : -1;
        }
        return -1;
    }

    /**
     * Игрок получает предметы указанные на табличках
     *
     * @return true- если предметы получены, false -если что то пошло не так
     */
    public Boolean giveItem(String _Temp)
    {
        try
        {
            Map<ItemStack, Long> _Map = getItemStack(_Temp);
            if (!getTimeOutMap().containsKey(getPlayer()))
            {;
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

        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] COnC giveItem {0}", e.getStackTrace().toString());
            return false;
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
                ExecuteCommands();
            }
        });
        Comm.start();	//Запуск потока
    }
    private static final Logger LOG = Logger.getLogger(CommandOnChairs.class.getName());
}
