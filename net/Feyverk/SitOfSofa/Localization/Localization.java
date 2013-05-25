/*
 * Отвичает за локализацию плагина.
 */
package net.Feyverk.SitOfSofa.Localization;

import net.Feyverk.SitOfSofa.Logic.FunctionalConfiguration;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Отвечает за мультиязычность плагина.
 *
 * @author Пётр
 */
public class Localization extends FunctionalConfiguration
{

    private static final Logger LOG = Logger.getLogger(Localization.class.getName());
    /**
     * Текущий язык плагина
     */
    private String CurrentLang;
    /**
     * Словарь где ключ - оригинальное название придмета, перевод на указанную
     * локализацию.
     */
    private Map<String, String> LangItemMap;
    /**
     * Словарь где ключ - внутринее название строки, выводимое сообщение.
     */
    private Map<String, String> LangMessageMap;

    /**
     * Словарь где ключ оригенальное название придмета, а значение перевод
     *
     * @return
     */
    public Map<String, String> getLangItemMap()
    {
        if (this.LangItemMap == null)
        {
            this.LangItemMap = new TreeMap<>();
        }
        return this.LangItemMap;
    }

    /**
     * Словарь где ключ внутренее название сообщеня, а значение перевод
     *
     * @return
     */
    public Map<String, String> getMessageMap()
    {
        if (this.LangMessageMap == null)
        {
            this.LangMessageMap = new TreeMap<>();
        }
        return this.LangMessageMap;
    }

    /**
     * Текущий язык плагина
     *
     * @return Возвращает текущий язык плагина
     */
    public String getCurrentLang()
    {
        if (this.CurrentLang == null)
        {
            this.CurrentLang = "";
        }
        return this.CurrentLang;
    }

    /**
     * Устанавливает текущий язык плагина
     */
    public void setCurrentLang(String CurrentLang)
    {
        if (CurrentLang != null)
        {
            this.CurrentLang = CurrentLang;
        } else
        {
            this.CurrentLang = "";
        }
    }

    /**
     * Конструктор инициализирующий объект типа Localization
     *
     * @param pluginFolder Путь к папке с данными программы
     * @param NameConfigFile Название конфигурационного языкового файла
     */
    public Localization(File pluginFolder, String NameConfigFile)
    {
        try
        {
            setDataFolder(pluginFolder);
            setConfigFileName(NameConfigFile);
            setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
            LangItemMap = new TreeMap<>();
            LangMessageMap = new TreeMap<>();
            LoadConfig();
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] Lang {0}", e.getStackTrace().toString());
        }
    }

    /**
     * Конструктор инициализирующий объект типа Localization Название файла
     * языковой конфигурации в данном случае принимается за lang.yml
     *
     * @param pluginFolder Путь к папке с данными программы
     */
    public Localization(File pluginFolder)
    {
        try
        {
            setDataFolder(pluginFolder);
            setConfigFileName("lang.yml");
            setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
            LangItemMap = new TreeMap<>();
            LangMessageMap = new TreeMap<>();
            LoadConfig();
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] Lang {0}", e.getStackTrace().toString());
        }
    }

    /**
     * Конструктор инициализирующий объект типа Localization Название файла
     * языковой конфигурации в данном случае принимается за lang.yml Папка
     * конфигурации принимается по умолчанию
     */
    public Localization()
    {
        try
        {
            setConfigFileName("lang.yml");
            setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
            LangItemMap = new TreeMap<>();
            LangMessageMap = new TreeMap<>();
            LoadConfig();
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa]+Lang {0}", e.getStackTrace().toString());
        }
    }

    /**
     * Загружает информацию из файла конфигураций языка
     *
     * @return Если загрузка удалась то true иначе false
     */
    protected final boolean LoadConfig()
    {
        if (!getFileConfiguration().exists())//если конфига не найден
        {
            CreateConfig();
        }
        try
        {
            String path = "blocklang"; //строка пути в конфиге
            setCurrentLang(getConfig().getString("currentlang"));//получаем текущий язык
            if (getCurrentLang().compareTo("") == 0)//провиряем не пустой ли он
            {
                LOG.warning("Неверная конфигурация языка.\nIncorrect configuration language.");
                return false;
            }
            LangItemMap = new TreeMap<>();
            LangMessageMap = new TreeMap<>();
            //-----------Получаем название предметов
            if (getConfig().getConfigurationSection(path).getKeys(false).contains(getCurrentLang()))
            {
                path += "." + getCurrentLang();
                for (String keys : getConfig().getConfigurationSection(path).getKeys(true))
                {
                   getLangItemMap().put(keys, getConfig().getString(path + "." + keys));
                }
            } else
            {
                LOG.warning("[SitOfSofa][blocklang section] Неверно указан язык.\nIncorrect language.");
                return false;
            }
            //-----------Получаем перевод плагина
            path = "pluginlang";
            if (getConfig().getConfigurationSection(path).getKeys(false).contains(getCurrentLang()))
            {
                path += "." + getCurrentLang();
                for (String keys : getConfig().getConfigurationSection(path).getKeys(true))
                {
                    getMessageMap().put(keys, getConfig().getString(path + "." + keys));
                }
            } else
            {
                LOG.warning("[SitOfSofa][pluginlang section] Неверно указан язык.\nIncorrect language.");
                return false;
            }

        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] Lang load config eroor {0}", e.getStackTrace().toString());
            return false;
        }
        return true;
    }

    /**
     * Меняет текущий язык и сохраняет изменение в файл
     *
     * @param newLang новый язык
     * @return true если смена произошла удачно, иначе false
     */
    public boolean ChangeCurrentLang(String newLang)
    {
        if (getConfig().getConfigurationSection("blocklang").getKeys(false).contains(getCurrentLang()))
        {
            //добавляем в файл конфигурации новую запись
            getConfig().set("currentlang", newLang);

        } else
        {
            LOG.warning("[SitOfSofa] Неверно указан язык.\nIncorrect language.");
            return false;
        }
        if (SaveConfig())//сохраняем на диск и возвращаем результат сохранения
        {
            LoadConfig();
        } else
        {
            return false;
        }
        return true;
    }

    /**
     * Получает нормальное имя предмета по внутренему если такое существует в
     * файле перевода, иначе возвращает внутренее
     *
     * @param internalName Внутренее имя предмета
     * @return Возвращает нормальное имя предмета по внутренему если такое
     * существует в файле перевода, иначе возвращает внутренее
     */
    public String getItemName(String internalName)
    {
        return getLangItemMap().containsKey(internalName) ? getLangItemMap().get(internalName) : internalName;
    }

    /**
     * Получает нормальную строку сообщения по внутренней если такая существует
     * в файле перевода, иначе возвращает внутренее
     *
     * @param internalName Внутренее сообщение
     * @return Возвращает нормальную строку сообщения по внутренней если такая
     * существует в файле перевода, иначе возвращает внутренее
     */
    public String getMessagePlugin(String internalMessage)
    {
        return getMessageMap().containsKey(internalMessage) ? getMessageMap().get(internalMessage) : internalMessage;
    }

    /**
     * Получает нормальную строку сообщения по внутренней если такая существует
     * в файле перевода, иначе возвращает внутренее
     *
     * @param internalName Внутренее сообщение
     * @param player - имя игрока на которое будут заменены вхождения %UserName%
     * @return Возвращает нормальную строку сообщения по внутренней если такая
     * существует в файле перевода, иначе возвращает внутренее
     */
    public String getMessagePlugin(String internalMessage, String player)
    {
        return (getMessageMap().containsKey(internalMessage) ? getMessageMap().get(internalMessage) : internalMessage).replace("%UserName%", player);
    }

    /**
     * Получает нормальную строку сообщения по внутренней если такая существует
     * в файле перевода, иначе возвращает внутренее
     *
     * @param internalName Внутренее сообщение
     * @param player - игрок на которого будут заменены вхождения %UserName%
     * @return Возвращает нормальную строку сообщения по внутренней если такая
     * существует в файле перевода, иначе возвращает внутренее
     */
    public String getMessagePlugin(String internalMessage, org.bukkit.entity.Player player)
    {
        return (getMessageMap().containsKey(internalMessage) ? getMessageMap().get(internalMessage) : internalMessage).replace("%UserName%", player.getDisplayName());
    }
}
