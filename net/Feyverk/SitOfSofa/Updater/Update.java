package net.Feyverk.SitOfSofa.Updater;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.Feyverk.SitOfSofa.SitOfSofa;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Пётр
 */
public class Update
{

    /**
     * Текущая версия плагина
     */
    private String current_version;
    /**
     * Новая версия плагина
     */
    private String new_version;
    /**
     * Версия буккита
     */
    private String bukkit_version;
    /**
     * Адрес для проверки версии
     */
    private String version_check_url;
    /**
     * Проверять ли вобще новые версии или нет?
     */
    private Boolean version_check;
    /**
     * Задержка в минутах перед повторной проверкой обновления
     */
    private long TimeOut;

    /**
     * Возвращает надо ли вобще проверять новые версии или нет
     *
     * @return
     */
    public Boolean getVersion_check()
    {
        return this.version_check;
    }

    /**
     * Получает текущую версию
     *
     * @return Получает текущую версию
     */
    public String getCurrent_version()
    {
        if (this.current_version == null)
        {
            this.current_version = "0.0.0";
        }
        return this.current_version;
    }

    /**
     * Получает текущую версию буккита
     *
     * @return Получает текущую версию буккита
     */
    public final String getBukkit_version()
    {
        if (this.bukkit_version == null)
        {
            this.bukkit_version = "0.0.0";
        }
        return this.bukkit_version;
    }

    /**
     * Получает номер новой версии и ссылку
     *
     * @return Получает номер новой версии и ссылку
     */
    public String getNew_version()
    {
        if (this.new_version == null)
        {
            this.new_version = "0.0.0\nUrl: NAN";
        }
        return this.new_version;
    }

    /**
     * Получает адрес для проверки версий
     *
     * @return Получает адрес для проверки версий
     */
    public String getVersion_check_url()
    {
        if (this.version_check_url == null)
        {
            this.version_check_url = "";
        }
        return this.version_check_url;
    }

    /**
     * Задаёт версии буккита
     *
     * @param bukkit_version строка содержащяя версию буккита
     */
    public final void setBukkit_version(String bukkit_version)
    {
        if (bukkit_version != null)
        {
            this.bukkit_version = bukkit_version;
        } else
        {
            this.bukkit_version = "0.0.0";
        }
    }

    /**
     * Задаёт текущую версию плагина
     *
     * @param current_version Строка содержащая текущую версию плагина
     */
    public final void setCurrent_version(String current_version)
    {
        if (current_version != null)
        {
            this.current_version = current_version;
        } else
        {
            this.current_version = "0.0.0";
        }
    }

    /**
     * Устанавливает проверять ли вобще новые версии или нет
     *
     * @param version_check true - да проверять, false - нет не проверять
     */
    public final void setVersion_check(boolean version_check)
    {
        this.version_check = version_check;
    }

    /**
     * Задаёт задержку перед повторной проверкой обновления в минутах
     *
     * @param TimeOut задержка перед повторной проверкой обновления в минутах
     */
    public final void setTimeOut(long TimeOut)
    {
        if (TimeOut > 0)
        {
            this.TimeOut = TimeOut * 60000;
        } else
        {
            this.TimeOut = 60000;
        }
    }

    /**
     * Возвращает задержку перед повторной проверкой обновления в милесекундах
     *
     * @return Возвращает задержку перед повторной проверкой обновления в
     * милесекундах
     */
    public long getTimeOut()
    {
        if (this.TimeOut < 60000)
        {
            this.TimeOut = 60000;
        }
        return this.TimeOut;
    }

    /**
     * Задаём адрес на xml файл для проверки версий
     *
     * @param version_check_url адрес на xml файл для проверки версий
     */
    public final void setVersion_check_url(String version_check_url)
    {
        if (version_check_url != null)
        {
            this.version_check_url = version_check_url;
        } else
        {
            this.version_check_url = "";
        }
    }

    /**
     * Инициализирует объект класса Update значением
     *
     * @param checkingVer - проверять ли обновления вобще
     * @param bukkitVersion - версия буккита по запросу getServer().getVersion()
     * @param pluginVersion - версия плагина можно по запросу
     * getDescription().getVersion();
     * @param pluginName - название плагина на dev.bukkit
     * @param timeOut - время задержки перед повторной проверкой обновления в
     * минутах
     */
    public Update(boolean checkingVer, String bukkitVersion, String pluginVersion, String pluginName, Long timeOut)
    {
        Matcher bkver = Pattern.compile("(?<=MC: )(.*)(?=\\))").matcher(bukkitVersion);
        if (bkver.find())
        {
            setCurrent_version(pluginVersion);
        }
        setVersion_check(checkingVer);
        setBukkit_version(bkver.group());
        setTimeOut(TimeOut);
        if (pluginName.isEmpty())
        {
            setVersion_check(false);
        } else
        {
            setVersion_check_url(("http://dev.bukkit.org/server-mods/" + pluginName + "/files.rss"));
        }
    }

    /**
     * Получает карту где ключ массив {версия плагина; версия буккита} название
     * файлов, значение ссылка для скачивания
     *
     * @return Получает карту где ключ название файлов, значение ссылка для
     * скачивания
     */
    private Map<String[], String> getMapNewVersion()
    {
        Map<String[], String> verList = new HashMap<>();
        if (getVersion_check())
        {
            try
            {
                URL url = new URL(getVersion_check_url());
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("item");
                for (int temp = 0; temp < nList.getLength(); temp++)
                {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) nNode;
                        String text = eElement.getElementsByTagName("title").item(0).getTextContent();
                        //LOG.info(text);
                        Matcher plver = Pattern.compile("(?<=_v)(.*)").matcher(text),
                                bukver = Pattern.compile("(?<=_bk)(.*)(?=_v)").matcher(text);
                        if (plver.find() && bukver.find())
                        {
                            String s1 = plver.group();//Версия плагина
                            String s2 = bukver.group();//версия буккита
                            String s3 = eElement.getElementsByTagName("link").item(0).getTextContent();//сылко на скачивание
                            verList.put(new String[]
                            {
                                s1, s2
                            }, s3);
                        }
                    }
                }
            } catch (ParserConfigurationException | IOException | SAXException | DOMException localException)
            {
                LOG.log(Level.INFO, "[SitOfSofa] Update error{0}", localException.getStackTrace().toString());
            }
        }
        return verList;
    }

    public void Run()
    {
        //Создание потока
        Thread autoChecked;
        autoChecked = new Thread(new Runnable()
        {
            @Override
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                autoChecked();
            }
        });
        autoChecked.start();	//Запуск потока
    }

    /**
     * Функция проверяет наличие новой версии
     */
    public void Checked()
    {
        String max = "0.0.0", link = "";
        if (getVersion_check())
        {
            Map<String[], String> versionsMap = getMapNewVersion();
            for (Map.Entry<String[], String> entry : versionsMap.entrySet())
            {
                if (entry.getKey()[1].equals(getBukkit_version())
                        && entry.getKey()[0].compareTo(getCurrent_version()) > 0)
                {
                    max = entry.getKey()[0];
                    link = entry.getValue();
                }
            }
            if (!max.equals("0.0.0"))
            {
                LOG.log(Level.INFO, "{0} {1}\n{2} {3}", new Object[]
                {
                    SitOfSofa.lang.getMessagePlugin("AVAILABLE_NEW_VERSION"), max, SitOfSofa.lang.getMessagePlugin("YOU_CAN_DOWNLOAD_IT"), link
                });
            }
        }
    }

    private void autoChecked()
    {
        while (getVersion_check())
        {
            Checked();
            try
            {
                Thread.sleep(getTimeOut());
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(Update.class.getName());
}