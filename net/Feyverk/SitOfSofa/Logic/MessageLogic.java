package net.Feyverk.SitOfSofa.Logic;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.Feyverk.SitOfSofa.SitOfSofa;
import net.Feyverk.SitOfSofa.Structures.BoolMessage;
import net.Feyverk.SitOfSofa.Structures.Items;
import net.Feyverk.SitOfSofa.Structures.TimeOut;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

/**
 * Класс описывает логику сообщений
 *
 * @author Пётр
 */
public class MessageLogic extends FunctionalConfiguration
{

    /**
     * Задержка перед повторным восстановлением здоровья.
     */
    private long timeoutRestoresHealth;
    /**
     * Карта где ключ это игрок а значение время когда происходило событие
     */
    private HashMap<Player, TimeOut> timeOutMap;
    /**
     * Максимальное количество разных предметов которые игрок может найти за раз
     */
    private int maxcountitem;
    /**
     * Писать ли сообщения о находках в общий чат или нет
     */
    private Boolean publicannounce;
    /**
     * логгер
     */
    private static final Logger LOG = Logger.getLogger(MessageLogic.class.getName());
    /**
     * Имя общего игрока, то есть все ироки кроме тех кто в списке
     */
    private String NameAnyPlayer = "*";
    /**
     * начальная фраза для всех пользователей Например "Игрок Имя"
     */
    private String startmessag;
    /**
     * Лимит через который можно писать повторное сообщение в чат
     */
    private long TimeoutMessage = 5000;
    /**
     * Лимит через который можно повторно найти предмет
     */
    private long TimeoutChance = 5000;
    /**
     * Можно ли вобще писать сообщение в чат при посадке?
     */
    private boolean EnableSitingMessage = false;
    /**
     * Можно ли вобще писать сообщение в чат при вставании со стула?
     */
    private boolean EnableStandUpMessage = false;
    /**
     * Словарь где ключ это имя игрока, а значение разрешения и сообщения
     */
    private Map<String, BoolMessage[]> NameMessage;
    /**
     * Словарь где ключ - предмет, значение шанс выпадания.
     */
    private Map<Items, Integer> ItemChanceMap;
    /**
     * Лечить ли игроков при посадке на стул
     */
    private Boolean restoresHealth = false;
    /**
     * Включена ли вобще способность получать предмет.
     */
    private Boolean enableChance = false;

    /**
     * Словарь где ключ это имя игрока, а значение разрешения и сообщения
     *
     * @return возвращает Словарь где ключ это имя игрока, а значение разрешения
     * и сообщения
     */
    public Map<String, BoolMessage[]> getNameMessage()
    {
        if (this.NameMessage == null)
        {
            this.NameMessage = new TreeMap<>();
        }
        return this.NameMessage;
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
     * Возвращает Писать ли сообщения о находках в общий чат или только лично
     * игроку?
     *
     * @return true/false Писать ли сообщения о находках в общий чат или только
     * лично игроку?
     */
    public Boolean getPublicAnnounce()
    {
        return publicannounce;
    }

    /**
     * Задаёт Писать ли сообщения о находках в общий чат или только лично
     * игроку?
     *
     * @param publicannounce - false - оповещать об этом всех, true - писать
     * публично
     */
    public void setPublicannounce(Boolean publicannounce)
    {
        this.publicannounce = publicannounce;
    }

    /**
     * Возвращает время через которое игроки могут повторно восстанавливать
     * здаровье при посадке на стул
     *
     * @return Возвращает время через которое игроки могут повторно
     * восстанавливать здаровье при посадке на стул
     */
    public long getTimeoutRestoresHealth()
    {
        if (this.timeoutRestoresHealth < 0)
        {
            this.timeoutRestoresHealth = 0;
        }
        return timeoutRestoresHealth;
    }

    /**
     * Максимальное количество разных предметов которые игрок может найти за раз
     *
     * @return Возвращает Максимальное количество разных предметов которые игрок
     * может найти за раз
     */
    public int getMaxCountItem()
    {
        if (this.maxcountitem < 0)
        {
            this.maxcountitem = 0;
        }
        return this.maxcountitem;
    }

    /**
     * Задаёт Максимальное количество разных предметов которые игрок может найти
     * за раз
     *
     * @param maxcountitem Задаёт Максимальное количество разных предметов
     * которые игрок может найти за раз
     */
    public final void setMaxCountItem(int maxcountitem)
    {
        if (maxcountitem >= 0)
        {
            this.maxcountitem = maxcountitem;
        } else
        {
            this.maxcountitem = 0;
        }
    }

    /**
     * Возвращает имя обозначающего любого игрока
     *
     * @return Возвращает имя обозначающего любого игрока
     */
    public final String getNameAnyPlayer()
    {
        if (this.NameAnyPlayer == null)
        {
            this.NameAnyPlayer = "*";
        }
        return this.NameAnyPlayer;

    }

    /**
     * Задаёт имя обозначающего любого игрока
     *
     * @param NameAnyPlayer имя обозначающее любого игрока
     */
    public final void setNameAnyPlayer(String NameAnyPlayer)
    {
        if (NameAnyPlayer != null)
        {
            this.NameAnyPlayer = NameAnyPlayer;
        } else
        {
            this.NameAnyPlayer = "*";
        }
    }

    /**
     * Получает начальную фразу одинаковую для всех пользователей
     *
     * @return Получает начальную фразу одинаковую для всех пользователей
     */
    public String getStartMessage()
    {
        if (this.startmessag == null)
        {
            this.startmessag = "";
        }
        return this.startmessag;
    }

    /**
     * Задаёт начальную фразу одинаковую для всех пользователей
     *
     * @return Задаёт начальную фразу одинаковую для всех пользователей
     */
    private void setStartMessage(String startmessag)
    {
        if (startmessag != null)
        {
            this.startmessag = startmessag;
        } else
        {
            this.startmessag = "";
        }
    }

    /**
     * Лимит через который можно писать повторное сообщение в чат
     *
     * @return Возвращает Лимит через который можно писать повторное сообщение в
     * чат
     */
    public long getTimeoutMessage()
    {
        if (this.TimeoutMessage < 0)
        {
            this.TimeoutMessage = 0l;
        }
        return this.TimeoutMessage;
    }

    /**
     * задаёт Лимит через который можно писать повторное сообщение в чат
     *
     * @return задаёт Лимит через который можно писать повторное сообщение в чат
     */
    private void setTimeoutMessage(Long TimeoutMessage)
    {
        if (TimeoutMessage >= 0)
        {
            this.TimeoutMessage = TimeoutMessage;
        } else
        {
            this.TimeoutMessage = 0;
        }
    }

    /**
     * Лимит через который можно получить шанс повторно найти предметы на
     * стульях
     *
     * @return Возвращает Лимит через который можно получить шанс повторно найти
     * предметы на стульях чат
     */
    public long getTimeoutChance()
    {
        if (this.TimeoutChance < 0)
        {
            this.TimeoutChance = 0l;
        }
        return this.TimeoutChance;
    }

    /**
     * задаёт Лимит через который можно получить шанс повторно найти предметы
     *
     * @return задаёт Лимит через который можно получить шанс повторно найти
     * предметы
     */
    private void setTimeoutChance(Long TimeoutChance)
    {
        if (TimeoutChance >= 0)
        {
            this.TimeoutChance = TimeoutChance;
        } else
        {
            this.TimeoutChance = 0;
        }
    }

    /**
     * Можно ли вобще писать сообщение в чат при посадке на стул?
     *
     * @return Возвращает true если можно иначе false
     */
    public boolean getEnableSitingMessage()
    {
        return EnableSitingMessage;
    }

    /**
     * Можно ли вобще писать сообщение в чат при вставании со стула?
     *
     * @return Возвращает true если можно иначе false
     */
    public boolean getEnableStandUpMessage()
    {
        return EnableStandUpMessage;
    }

    /**
     * Получает и возращает Можно ли лечить игроков при посадке на стул
     *
     * @return Возвращает true если можно иначе false
     */
    public boolean getRestoresHealth()
    {
        return restoresHealth;
    }

    /**
     * Задаёт Можно ли лечить игроков при посадке на стул
     *
     * @return Возвращает true если можно иначе false
     */
    private void setRestoresHealth(Boolean restoresHealth)
    {
        this.restoresHealth = restoresHealth;
    }

    /**
     * Устанавливает время через которое можно повторно лечить игроков
     *
     * @param timeoutRestoresHealth время задержки, перед повторным лечением
     * игроков
     */
    public void setTimeoutRestoresHealth(long timeoutRestoresHealth)
    {
        if (timeoutRestoresHealth >= 0)
        {
            this.timeoutRestoresHealth = timeoutRestoresHealth;
        } else
        {
            this.timeoutRestoresHealth = 0l;
        }
    }

    /**
     * Получает список игроков и их сообщений с разрешениями
     *
     * @return Возвращает список игроков и их сообщений с разрешениями
     */
    public Map<String, BoolMessage[]> getNamePermissinsMessage()
    {
        if (this.NameMessage == null)
        {
            this.NameMessage = new TreeMap<>();
        }
        return this.NameMessage;
    }

    /**
     * Естиль шанс у игроков получать предметы?
     *
     * @return true - шанс есть, false - шанса нет
     */
    public Boolean getEnableChance()
    {
        return enableChance;
    }

    /**
     * Получает словарь Предмет - Шанс выпадения
     *
     * @return Получает словарь Предвет - Шанс выпадения
     */
    public Map<Items, Integer> getItemChanceMap()
    {
        if (this.ItemChanceMap == null)
        {
            this.ItemChanceMap = new HashMap<>();
        }
        return this.ItemChanceMap;
    }

    /**
     * Конструктор класса MessageLogic
     *
     * @param ConfigFile Файл конфигурации
     * @param pluginFolder Директория настроек плагина
     * @param NameAllPlayer Метка обозначающая любого игрока
     */
    public MessageLogic(String ConfigFileName, File pluginFolder, String NameAnyPlayer)
    {
        setNameAnyPlayer(NameAnyPlayer);
        setConfigFileName(ConfigFileName);
        setDataFolder(pluginFolder);
        setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
        timeOutMap = new HashMap<>();
    }

    /**
     * Конструктор класса MessageLogic
     *
     * @param ConfigFile Файл конфигурации
     * @param pluginFolder Директория настроек плагина
     */
    public MessageLogic(String ConfigFileName, File pluginFolder)
    {
        setNameAnyPlayer("*");
        setConfigFileName(ConfigFileName);
        setDataFolder(pluginFolder);
        setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
        timeOutMap = new HashMap<>();
    }

    /**
     * Конструктор класса MessageLogic
     *
     * @param ConfigFile Файл конфигурации
     * @param pluginFolder Директория настроек плагина
     * @param NameAllPlayer Метка обозначающая любого игрока
     * @param LoadConf Загружать ли конфигурацию при инициализации true/false
     */
    public MessageLogic(String ConfigFileName, File pluginFolder, String NameAnyPlayer, boolean LoadConf)
    {
        setNameAnyPlayer(NameAnyPlayer);
        setConfigFileName(ConfigFileName);
        setDataFolder(pluginFolder);
        setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
        timeOutMap = new HashMap<>();
        if (LoadConf)
        {
            loadConfig();
        }
    }

    /**
     * Конструктор класса MessageLogic
     *
     * @param ConfigFile Файл конфигурации
     * @param pluginFolder Директория настроек плагина
     * @param LoadConf Загружать ли конфигурацию при инициализации true/false
     */
    public MessageLogic(String ConfigFileName, File pluginFolder, boolean LoadConf)
    {
        setNameAnyPlayer("*");
        setConfigFileName(ConfigFileName);
        setDataFolder(pluginFolder);
        setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
        timeOutMap = new HashMap<>();
        if (LoadConf)
        {
            loadConfig();
        }
    }

    /**
     * Печатает сообщение от указанного пользователя при посадке на стул
     *
     * @param player Пользователь от чьего имени будет выводится сообщение
     * @return Если вывод на экран произошёл успешно то true иначе false
     */
    public boolean PrintMessageSitdown(Player player)
    {
        try
        {
            if (getEnableSitingMessage())
            {
                if (!getTimeOutMap().containsKey(player))
                {
                    getTimeOutMap().put(player, new TimeOut(0, 0, 0));
                } else if (!(System.currentTimeMillis() - getTimeOutMap().get(player).getMessageSitdownTime() <= getTimeoutMessage()))
                {
                    if (getNamePermissinsMessage().containsKey(player.getDisplayName()))
                    {
                        if (getNamePermissinsMessage().get(player.getDisplayName())[0].GetFlag())
                        {
                            player.getServer().broadcastMessage(getStartMessage()
                                    + (getNamePermissinsMessage().get(player.getDisplayName())[0].GetMessage()).replace("%UserName%", player.getDisplayName()));
                        }
                    } else
                    {
                        if (getNamePermissinsMessage().containsKey(getNameAnyPlayer()))
                        {
                            if (getNamePermissinsMessage().get(getNameAnyPlayer())[0].GetFlag())
                            {
                                player.getServer().broadcastMessage(
                                        (getStartMessage() + getNamePermissinsMessage().get(getNameAnyPlayer())[0].GetMessage()).replace("%UserName%", player.getDisplayName()));
                            }
                        }
                    }
                }
                getTimeOutMap().get(player).setMessageSitdownTime(System.currentTimeMillis());
                //Шанс получить предмет
            }
            return true;
        } catch (Exception e)
        {
            LOG.info(e.getStackTrace().toString());
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Печатает сообщение от указанного пользователя при подъема со стула
     *
     * @param player Пользователь от чьего имени будет выводится сообщение
     * @return Если вывод на экран произошёл успешно то true иначе false
     */
    public boolean PrintMessageStandUp(Player player)
    {
        try
        {
            if ((!(System.currentTimeMillis() - getTimeOutMap().get(player).getMessageStandUpTime() <= getTimeoutMessage())) && getEnableStandUpMessage())
            {
                if (getNamePermissinsMessage().containsKey(player.getDisplayName()))
                {
                    if (getNamePermissinsMessage().get(player.getDisplayName())[1].GetFlag())
                    {
                        //player.chat(NameMessage.get(player.getDisplayName())[1].GetMessage());
                        player.getServer().broadcastMessage((getStartMessage() + getNamePermissinsMessage().get(player.getDisplayName())[1].GetMessage()).replace("%UserName%", player.getDisplayName()));
                    }
                } else
                {
                    if (getNamePermissinsMessage().containsKey(getNameAnyPlayer()))
                    {
                        if (getNamePermissinsMessage().get(getNameAnyPlayer())[1].GetFlag())
                        {
                            //player.chat(NameMessage.get(GetNameAllPlayer())[1].GetMessage());
                            player.getServer().broadcastMessage((getStartMessage() + getNamePermissinsMessage().get(getNameAnyPlayer())[1].GetMessage()).replace("%UserName%", player.getDisplayName()));
                        }
                    }
                }
            }
            getTimeOutMap().get(player).setMessageStandUpTime(System.currentTimeMillis());
            return true;
        } catch (Exception e)
        {
            LOG.info(e.getStackTrace().toString());
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Устанавливает Хеш где ключ предмет, значение его шанс выпадения.
     *
     * @param ItemChanceMap Хеш где ключ предмет, значение его шанс выпадения.
     */
    public void setItemChanceMap(Map<Items, Integer> ItemChanceMap)
    {
        if (ItemChanceMap != null)
        {
            this.ItemChanceMap = ItemChanceMap;
        } else
        {
            this.ItemChanceMap = new HashMap<>();
        }
    }

    private boolean AddNameMessage(String namemessage)
    {
        if (namemessage.contains(":"))
        {
            String[] temp = namemessage.split(":");
            if (temp.length == 5)
            {
                getNameMessage().put(temp[0], new BoolMessage[]
                {
                    new BoolMessage((temp[1].compareTo("true") == 0) ? true : false, temp[2]),
                    new BoolMessage((temp[3].compareTo("true") == 0) ? true : false, temp[4])
                });
            }
        } else
        {
            return false;
        }
        return true;
    }

    /**
     * Получает список параметров пользователей
     *
     * @return Возвращает список параметров пользователей
     */
    public List<String> getNameMessageStringArray()
    {
        List<String> StringList = new LinkedList<>();
        for (Map.Entry<String, BoolMessage[]> e : getNameMessage().entrySet())
        {
            StringList.add(e.getKey().toString() + e.getValue()[0].toString() + e.getValue()[1].toString());
        }
        return StringList;
    }

    /**
     * Получает словарь из указанной дирриктории со словарём
     *
     * @param Path Директория в конфиге
     * @return Словарь со значениями
     */
    private Map<Items, Integer> getItemMapList(String Path)
    {
        Map<Items, Integer> Temp = new HashMap<>();
        for (String Keys : getConfig().getConfigurationSection(Path).getKeys(false))
        {
            String sd = getConfig().getString(Path + "." + Keys).replaceAll(" ", "");
            String[] _TempStrings = sd.split("(\\|)");
            if (_TempStrings.length >= 4)
            {
                Integer chance = Integer.parseInt(_TempStrings[0]), min_Count = Integer.parseInt(_TempStrings[1]), max_Count = Integer.parseInt(_TempStrings[2]);
                byte data_Byte = Byte.parseByte(_TempStrings[3]);
                Items items = new Items(Integer.parseInt(Keys), min_Count, max_Count, data_Byte, null);
                if (_TempStrings.length == 5)// если = 5 значит есть чары
                {
                    _TempStrings = _TempStrings[4].replace("{", "").replace("}", "").split("(\\,)");
                    if (_TempStrings.length % 2 == 0)
                    {
                        Map<Enchantment, Integer> magic = new HashMap<>();
                        for (Integer i = 0; i < _TempStrings.length; i += 2)
                        {
                            magic.put(Enchantment.getByName(_TempStrings[i]), Integer.parseInt(_TempStrings[i + 1]));
                        }
                        //LOG.info(magic.toString());
                        items.setEnchantment(magic);
                    }
                }
                Temp.put(items, chance);
            } else
            {
                try
                {
                    String[] S = sd.split(":");
                    if (S.length == 2)
                    {
                        Items items = new Items(Integer.parseInt(S[0]), 1, 1, (byte) 0, null);
                        Temp.put(items, Integer.parseInt(S[1]));
                    } else
                    {
                        LOG.log(Level.WARNING, "[SitOfSofa] getItemMapList(..) Incorrect string: \"{0}\"", sd);
                    }
                } catch (Exception e)
                {
                    LOG.warning("[SisOfSofa] Incorrerct chance config.");
                }

            }
            //LOG.log(Level.INFO, "{0} {1}", new Object[]{Integer.parseInt(Keys), getConfig().getString(Path + "." + Keys)});
            //Temp.put(Integer.parseInt(Keys), getConfig().getInt(Path + "." + Keys));
        }
        return Temp;
    }

    /**
     * Устанавливает может ли вобще игрок получить шанс найти предметы
     *
     * @param enableChance true - дa, false - нет
     */
    public final void setEnableChance(Boolean enableChance)
    {
        this.enableChance = enableChance;
    }

    /**
     * Загружает конфигурацию из файла
     */
    public final void loadConfig()
    {
        try
        {
            NameMessage = new TreeMap<>();
            setItemChanceMap(new TreeMap<Items, Integer>());
            if (!getFileConfiguration().exists())//если конфига не найден
            {
                CreateConfig();
            }
            EnableSitingMessage = getConfig().getBoolean("message.enablesitingmessage");//можно ли писать сообщение при посадке на стул
            EnableStandUpMessage = getConfig().getBoolean("message.enablestandupmessage");//можно ли писать сообщение при вставании со стула
            setTimeoutMessage(getConfig().getLong("message.timeoutmessage"));//Время задержки перед повторным сообщением о посадке/подъёме
            setTimeoutChance(getConfig().getLong("chance.timeoutchance"));//Время задержки перед повторным нахождением предмета
            setStartMessage(getConfig().getString("message.startmessage")); //Первая часть сообщения
            setRestoresHealth(getConfig().getBoolean("health.restoreshealth")); // будет ли у игроков восстанавливаться здаровие?
            setTimeoutRestoresHealth(getConfig().getLong("chance.timeoutrestoreshealth"));// Время через которое игрока можно повторно лечить.
            setEnableChance(getConfig().getBoolean("chance.chancegiveitems"));//могут ли игроки вобще получать предметы
            setMaxCountItem(getConfig().getInt("chance.maxcountitem"));//могут ли игроки вобще получать предметы
            setPublicannounce(getConfig().getBoolean("chance.publicannounce"));//могут ли игроки вобще получать предметы
            //LOG.info("[enableChance]" + (enableChance ? "true" : "false"));
            if (getEnableChance())
            {
                setItemChanceMap(getItemMapList("chance.giveitems"));
                //LOG.info(ItemChanceMap.toString());
            }
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] messagelog load config error {0}", e.getStackTrace().toString());
            //e.printStackTrace();
        }

        try
        {
            if (getEnableSitingMessage() != false && getEnableStandUpMessage() != false)
            {
                for (String namemessage : getConfig().getStringList("message.namemessage"))
                {
                    AddNameMessage(namemessage);
                }
            }
        } catch (Exception e)
        {
            LOG.info(e.getStackTrace().toString());
        }
    }

    /**
     * Строит строку из параметров для добавления записи о пользователе
     *
     * @param args Аргументы
     * @return Возвращает строку из параметров для добавления записи о
     * пользователе
     */
    private String BuildUserStringFromArgs(String[] args)
    {
        if (args.length < 6)
        {
            return "";
        }
        for (int i = 1; i < args.length; i++)
        {
            args[i] = args[i].replace(":", "");
        }

        String temp = args[1];//добавляем ник
        Boolean flag = false;
        //формируем строку
        for (int i = 2; i < args.length; i++)
        {
            if (args[i].charAt(0) == '"' && args[i].charAt(args[i].length() - 1) == '"')
            {
                temp += ":" + args[i];
            } else
            {
                if (flag == false && (args[i].compareTo("true") == 0 || args[i].compareTo("false") == 0))
                {
                    temp += ":" + args[i];
                } else if (flag)
                {
                    temp += " " + args[i];
                } else
                {
                    temp += ":" + args[i];
                }
                if (args[i].charAt(0) == '"' || args[i].charAt(args[i].length() - 1) == '"')
                {
                    flag = !flag;
                }
            }
        }
        temp = temp.replace("\"", "");
        //проверяем строку на корректность в лоб
        if (!(temp.contains(":true:") || !temp.contains(":false:")))
        {
            return "";
        }
        return temp;
    }

    /**
     * Добавляет правила для указанного пользователя
     *
     * @param sender
     * @param args правила
     * @return true - если все ok, иначе false
     */
    public boolean AddUser(CommandSender sender, String[] args)
    {
        try
        {
            //проверяем на существование правил
            if (getNamePermissinsMessage().containsKey(args[1]))
            {
                sender.sendMessage(SitOfSofa.lang.getMessagePlugin("RULES_FOR_USER_EXITIS"));
                String mes = ((getNamePermissinsMessage().get(args[1])[0].GetFlag() == true) ? "true" : "false") + ":" + getNamePermissinsMessage().get(args[1])[0].GetMessage()
                        + ":" + ((getNamePermissinsMessage().get(args[1])[1].GetFlag() == true) ? "true" : "false") + ":" + getNamePermissinsMessage().get(args[1])[1].GetMessage();
                sender.sendMessage(mes);
                return false;
            }

            String Temp = BuildUserStringFromArgs(args);
            if (Temp.compareTo("") != 0)
            {
                AddNameMessage(Temp);
            } else
            {
                return false;
            }

            //добавляем в файл конфигурации новую запись
            getConfig().set("message.namemessage", getNameMessageStringArray());
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] addUser error {0}", e.getStackTrace().toString());
            return false;
        }
        return SaveConfig();//сохраняем на диск и возвращаем результат сохранения;
    }

    /**
     * Меняет правила для указанного пользователя
     *
     * @param sender
     * @param args правила
     * @return true - если все ok, иначе false
     */
    public boolean ChangeUserMessage(CommandSender sender, String[] args)
    {
        if (!getNamePermissinsMessage().containsKey(args[1]))
        {
            sender.sendMessage(SitOfSofa.lang.getMessagePlugin("RULES_FOR_USER_NOT_EXITIS") + " " + SitOfSofa.lang.getMessagePlugin("WILL_CREATE_NEW"));
            AddUser(sender, args);
        } else
        {
            try
            {
                RemoveUserMessage(sender, args[1]);
                AddUser(sender, args);
            } catch (Exception e)
            {
                LOG.log(Level.WARNING, "[SitOfSofa] ChangeUserMessage error {0}", e.getStackTrace().toString());
                return false;
            }
        }
        return true;
    }

    /**
     * Удаляет настройки для указанного пользователя
     *
     * @param sender сендер
     * @param username Имя пользователя
     * @return true - если удаление прошло успешно иначе false
     */
    public boolean RemoveUserMessage(CommandSender sender, String username)
    {
        try
        {
            if (!getNamePermissinsMessage().containsKey(username))
            {
                sender.sendMessage(SitOfSofa.lang.getMessagePlugin("RULES_FOR_USER_NOT_EXITIS"));
                return false;
            } else
            {
                getNameMessage().remove(username);
            }
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] RemoveUserMessage error {0}", e.getStackTrace().toString());
            return false;
        }
        return true;
    }

    /**
     * Производит смену таймаута между повторными сообщениями в конфигурационном
     * файле
     *
     * @param TimeOut - время задержки в милисекундах
     * @return Возвращает true - если смена произошла удачно иначе false
     */
    public boolean ChangeTimeOut(long TimeOut)
    {
        try
        {
            setTimeoutMessage(TimeOut);
            getConfig().set("message.timeoutmessageandchance", getTimeoutMessage());
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] ChangeTimeOut error {0}", e.getStackTrace().toString());
            return false;
        }
        return SaveConfig();//сохраняем на диск и возвращаем результат сохранения
    }

    /**
     * Меняет первую часть сообщения в в конфигурационном файле
     *
     * @param StartMessage Новое начало сообщения
     * @return Возвращает true - если смена произошла удачно иначе false
     */
    public boolean ChangeStartMessage(String StartMessage)
    {
        try
        {
            setStartMessage(StartMessage);
            getConfig().set("message.startmessage", getStartMessage());
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] ChangeStartMessage error {0}", e.getStackTrace().toString());
            return false;
        }
        return SaveConfig();//сохраняем на диск и возвращаем результат сохранения
    }

    /**
     * Устанавливает можно ли восстанавливать игроку здоровье при посадке
     *
     * @param restoresHealth - восстанавливать ли здоровье при посадке?
     * @return Возвращает true - если смена произошла удачно иначе false
     */
    public boolean ChangeRestoresHealth(Boolean restoresHealth)
    {
        try
        {
            setRestoresHealth(restoresHealth);
            getConfig().set("restoreshealth", getRestoresHealth());
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] ChangeRestoresHealth error {0}", e.getStackTrace().toString());
            return false;
        }
        return SaveConfig();//сохраняем на диск и возвращаем результат сохранения
    }

    /**
     * Даёт возможность пользователю найти на скамейке предмет с указанной в
     * конфигурации вероятностью
     *
     * @param player Игрок который сел на скамейку
     * @return true - если игрок что то нашел, иначе false
     */
    public boolean comeСhance(Player player, boolean com)
    {
        if (com)
        {
            if (getEnableChance() || com)
            {
                if (!getTimeOutMap().containsKey(player))
                {
                    getTimeOutMap().put(player, new TimeOut(0, 0, 0));
                }
                if (!(System.currentTimeMillis() - getTimeOutMap().get(player).getChanceTime() < getTimeoutChance()))
                {
                    int counts = 0;
                    if (counts < getMaxCountItem())
                    {
                        String Itemss = "";
                        Random rand = new Random();
                        for (Map.Entry<Items, Integer> item : getItemChanceMap().entrySet())
                        {
                            //LOG.info("Начали проверку шансов " + item.getKey());
                            if (rand.nextInt(1000)
                                    <= item.getValue())
                            {
                                Items d = item.getKey();
                                d.reGenerateCount();
                                ItemStack i = d.getItem();
                                counts++;
                                player.getInventory().addItem(i);
                                Itemss += SitOfSofa.lang.getItemName(i.getType().name()) + "(" + i.getAmount() + "), ";
                            }
                        }
                        if (!"".equals(Itemss))
                        {
                            if (getPublicAnnounce())
                            {
                                player.getServer().broadcastMessage((getStartMessage() + SitOfSofa.lang.getMessagePlugin("PLAYER_FOUND_ON_CHAIR", player) + " " + Itemss));
                            } else
                            {
                                player.sendMessage((SitOfSofa.lang.getMessagePlugin("YOU_FOUND_ON_CHAIR", player) + " " + Itemss));
                            }
                        }
                        //player.sendMessage(Itemss);
                        getTimeOutMap().get(player).setChanceTime(System.currentTimeMillis());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Устанавливает вобще может ли игрок получать предметы или нет
     *
     * @param enableChance - true - игрок может получать, false - не может
     * @return Возвращает true - если смена произошла удачно иначе false
     */
    public boolean ChangeEnableGiveItems(boolean enableChance)
    {
        try
        {
            this.enableChance = enableChance;
            getConfig().set("chance.chancegiveitems", enableChance);
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] ChangeEnableGiveItems error {0}", e.getStackTrace().toString());
            return false;
        }
        return SaveConfig();//сохраняем на диск и возвращаем результат сохранения
    }

    /**
     * Восстанавливает игроку здоровье если разрешено в конфигурации
     *
     * @param player - игрок которому надо восстановить здоровье
     * @return true - если восстановленно удачно иначе false
     */
    public Boolean restoreHealth(Player player, int com)
    {
        if (com == 2)
        {
            if (!player.hasPermission("sofa.restorehealth"))
            {
                return false;
            }
        }
        if (com == 1)
        {
            try
            {
                if (!getTimeOutMap().containsKey(player))
                {
                    getTimeOutMap().put(player, new TimeOut());
                }
                if (getRestoresHealth())
                {
                    if (!(System.currentTimeMillis() - getTimeOutMap().get(player).getTimeOutRestoreHealth() < getTimeoutRestoresHealth()))
                    {
                        player.setHealth(20);
                        getTimeOutMap().get(player).setTimeOutRestoreHealth(System.currentTimeMillis());
                    }
                }
            } catch (Exception e)
            {
                LOG.log(Level.INFO, "[SitOfSofa] RestoreHealth error{0}", e.getStackTrace().toString());
                //e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
