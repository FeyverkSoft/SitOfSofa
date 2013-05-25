package net.Feyverk.SitOfSofa.Logic;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.Feyverk.SitOfSofa.Localization.Localization;
import net.Feyverk.SitOfSofa.UnZip;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Пётр
 */
public class FunctionalConfiguration
{

    private static final Logger LOG = Logger.getLogger(Localization.class.getName());
    /**
     * Название файла с конфигурацией
     */
    private String configFileName;
    /**
     * Папка с данными
     */
    private File dataFolder;
    /**
     * файл ЯЗЫКОВОЙ конфигурации
     */
    private File fileConfiguration;
    /**
     * Yaml конфигурация
     */
    private YamlConfiguration YamlConfig;

    /**
     * Имя с файлом конфигурации
     *
     * @return Возвращает имя файла конфигурации
     */
    protected final String getConfigFileName()
    {
        if (this.configFileName == null)
        {
            this.configFileName = "";
        }
        return this.configFileName;
    }

    /**
     * Возвращает папку с пользовательскими данными
     *
     * @return Возвращает папку с пользовательскими данными
     */
    protected final File getDataFolder()
    {
        if (this.dataFolder == null)
        {
            this.dataFolder = new File(Localization.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        }
        return this.dataFolder;
    }

    /**
     * Устанавливает папку с пользовательскими данными
     */
    protected final void setDataFolder(File PluginFolder)
    {
        if (PluginFolder != null)
        {
            this.dataFolder = PluginFolder;
        } else
        {
            String myJarPath = Localization.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            this.dataFolder = new File(myJarPath);
        }
    }

    /**
     * Получает файл конфигурации типа Fil eсли файл Null тогда возвращает
     * значение по умолчанию
     *
     * @return Возвращает файл конфигурации типа File
     */
    public final File getFileConfiguration()
    {
        if (this.fileConfiguration == null)
        {
            this.fileConfiguration = new File(getDataFolder(), getConfigFileName());
        }
        return this.fileConfiguration;
    }

    /**
     * Задаёт файл конфигурации типа File если файл Null тогда присваевает
     * значение по умолчанию
     *
     * @param fileConfiguration файл конфигурации типа File
     */
    public final void setFileConfiguration(File fileConfiguration)
    {
        if (fileConfiguration != null)
        {
            this.fileConfiguration = fileConfiguration;
        } else
        {
            this.fileConfiguration = new File(getDataFolder(), getConfigFileName());
        }

    }

    /**
     *
     * Устанавливает имя файлаконфигурации
     *
     * @param configFileName имя файла конфигурации
     */
    protected final void setConfigFileName(String ConfigFileName)
    {
        if (ConfigFileName != null)
        {
            this.configFileName = ConfigFileName;
        } else
        {
            this.configFileName = "";
        }
    }

    /**
     * Возвращает конфигурацию
     *
     * @return
     */
    public FileConfiguration getConfig()
    {

        if (this.YamlConfig == null)
        {
            this.YamlConfig = YamlConfiguration.loadConfiguration(getFileConfiguration());
        }
        return this.YamlConfig;
    }

    /**
     * Устанавливает конфигурацию из yaml объекта
     *
     * @param YamlConfig Конфигурация
     */
    public void setConfig(YamlConfiguration YamlConfig)
    {
        if (YamlConfig != null)
        {
            this.YamlConfig = YamlConfig;
        } else
        {
            this.YamlConfig = YamlConfiguration.loadConfiguration(fileConfiguration);
        }
    }

    /**
     * Устанавливает конфигурацию
     *
     * @param YamlConfig Конфигурация
     */
    public final void setConfig(File FileConfig)
    {
        if (FileConfig != null)
        {
            this.YamlConfig = YamlConfiguration.loadConfiguration(FileConfig);
        } else
        {
            this.YamlConfig = YamlConfiguration.loadConfiguration(FileConfig);
        }
    }

    /**
     * Сохраняет конфигурацию в файл
     *
     * @return true-если сохранение прошло удачно, иначе false
     */
    protected final boolean SaveConfig()
    {
        try
        {
            //сохраняем
            getConfig().save(new File(getDataFolder(), getConfigFileName()));
        } catch (IOException ex)
        {
            LOG.log(Level.WARNING, "{0}\n{1}", new Object[]
            {
                ex.getMessage(), ex.getStackTrace().toString()
            });
            return false;
        }
        return true;
    }

    /**
     * Создаём конфиг
     *
     * @return true -если конфиг существует или создан успешно, иначе false
     */
    protected final boolean CreateConfig()
    {
        if (!getDataFolder().exists())//если папка плагина не найдена
        {
            try
            {
                getDataFolder().mkdir();//создаем новую
            } catch (Exception e)//если ошибка создания
            {
                LOG.info(e.getStackTrace().toString());//пишем в консоль
                //e.printStackTrace();
                return false;
            }
        }

        if (!getFileConfiguration().exists())//если конфиг не найден
        {
            try
            {
                UnZip.Unzip(Localization.class.getProtectionDomain().getCodeSource().getLocation().getFile(), getDataFolder().toString(), getConfigFileName());
            } catch (Exception e)//если ошибка
            {
                LOG.info(e.getStackTrace().toString());
                return false;
            }
        }
        return true;
    }
}
