package net.Feyverk.SitOfSofa;

import net.Feyverk.SitOfSofa.Localization.Localization;
import net.Feyverk.SitOfSofa.Logic.CommandOnChairs;
import net.Feyverk.SitOfSofa.Logic.StoolConfig;
import net.Feyverk.SitOfSofa.Logic.PoweredLogic;
import net.Feyverk.SitOfSofa.Logic.MessageLogic;
import net.Feyverk.SitOfSofa.Structures.SittingDataWatcher;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.Feyverk.SitOfSofa.Metrics.MetricsLite;
import net.Feyverk.SitOfSofa.Updater.Update;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
/*
 import net.minecraft.server.v1_5_R3.Packet40EntityMetadata;
 import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
 //*/
//*
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import net.minecraft.server.v1_5_R2.Packet40EntityMetadata;

//*/
/*
 import org.bukkit.craftbukkit.v1_4_R1.entity.CraftPlayer;
 import net.minecraft.server.v1_4_R1.Packet40EntityMetadata;
 //*/
/**
 *
 * @author Пётр
 */
public class SitOfSofa extends JavaPlugin
{

    /**
     * Язык плагина и сообщений
     */
    public static Localization lang;
    private static final Logger LOG = Logger.getLogger(SitOfSofa.class.getName());
    public boolean sneaking, autorotation; // autorotation - авто поворот
    public double distance;//дистанция
    private File pluginFolder;//папка с конфигурацией плагина
    private File configFile; //конфигурационный файл
    boolean antifallThrough = false;
    public static MessageLogic messageLogic; //Сообщения
    StoolConfig Stool;
    Update u;//Объект проверки обновлений
    MetricsLite metricsLite;//метрика
    public static PoweredLogic powered;//объект отвечающий за электро стулья
    Map<Player, CommandOnChairs> CommamdsUser;//карта комманды пользователь

    @Override
    public void onEnable()
    {
        LOG.log(Level.INFO, "[SitOfSofa] version {0} loading config...", getDescription().getVersion());
        pluginFolder = getDataFolder();
        configFile = new File(pluginFolder, "config.yml");
        createConfig();
        saveConfig();
        loadConfig();
        EventListener eventListener = new EventListener(this);
        getServer().getPluginManager().registerEvents(eventListener, this);
        LOG.info("[SitOfSofa] loaded.");
    }

    @Override
    public void onDisable()
    {
        messageLogic = null;
        Stool = null;
        lang = null;
        u = null;
        sneaking = false;
        autorotation = false;
        metricsLite = null;
        CommamdsUser = null;
        powered.poweredDisableAll();
        powered = null;
        LOG.info("[SitOfSofa] Disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("sofa"))
        {
            if (args.length > 0 && (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")))
            {
                sender.sendMessage("§aПлагин стульев и диванов от Feyverk[Kill]Soft aka Kill100 специально для MetroUnion server. \nWMZ - Z354634267000\n"
                        + "WMR - R278197088605\nVersion: " + getDescription().getVersion() + "\nBukkit " + getServer().getBukkitVersion());
                u.Checked();
            } else if (args.length > 0)
            {
                switch (args[0].toLowerCase())
                {
                    case "reload":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.reload"))
                        {
                            return true;
                        }
                        reloadConfig();
                        loadConfig();
                        sender.sendMessage(lang.getMessagePlugin("CONFIGURATION_FILE_RELOADED"));
                        break;
                    case "addmessage":
                        if (args.length > 4)
                        {
                            if (sender instanceof Player && !((Player) sender).hasPermission("sofa.addmessage"))
                            {
                                return true;
                            }
                            try
                            {
                                if (!messageLogic.AddUser(sender, args))
                                {
                                    sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                                } else
                                {
                                    sender.sendMessage(lang.getMessagePlugin("RULE_ADDED_TO_USER", args[1]));
                                }
                            } catch (Exception e)
                            {
                                sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                                LOG.info(e.getStackTrace().toString());
                            }
                        } else
                        {
                            sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                        }
                        break;
                    case "settimeout":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.settimeout"))
                        {
                            return true;
                        }
                        try
                        {
                            if (messageLogic.ChangeTimeOut(Long.parseLong(args[1])))
                            {
                                sender.sendMessage(lang.getMessagePlugin("SET_NEW_TIMEOUT"));
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_SPECIFIED_NUMBER"));
                            LOG.warning(e.getStackTrace().toString());
                        }
                        break;
                    case "setstartmessage":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.setstartmessage"))
                        {
                            return true;
                        }
                        try
                        {
                            String temp = "";
                            for (int i = 1; i < args.length; i++)
                            {
                                temp += args[i] + " ";
                            }
                            if (messageLogic.ChangeStartMessage(temp))
                            {
                                sender.sendMessage(lang.getMessagePlugin("CHANGES_ARE_SAVED"));
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                            LOG.warning(e.getStackTrace().toString());
                        }
                        break;
                    case "setrestoreshealth":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.setrestoreshealth"))
                        {
                            return true;
                        }
                        try
                        {
                            if (args.length == 2 && (args[1].compareTo("true") == 0 || args[1].compareTo("false") == 0))
                            {
                                if (messageLogic.ChangeRestoresHealth(args[1].compareTo("true") == 0 ? true : false))
                                {
                                    sender.sendMessage(lang.getMessagePlugin("CHANGES_ARE_SAVED"));
                                } else
                                {
                                    sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING") + "/sofa setrestoreshealth <true/false>");
                                }
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                            LOG.warning(e.getStackTrace().toString());
                        }
                        break;
                    case "cm":
                        if (args.length > 4)
                        {
                            if (sender instanceof Player && !((Player) sender).hasPermission("sofa.cm"))
                            {
                                return true;
                            }
                            try
                            {
                                args[1] = ((Player) sender).getDisplayName();
                                if (!messageLogic.ChangeUserMessage(sender, args))
                                {
                                    sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                                } else
                                {
                                    sender.sendMessage(lang.getMessagePlugin("YOUR_MESSAGE_HAS_CHANGED"));
                                }
                            } catch (Exception e)
                            {
                                sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                                LOG.info(e.getStackTrace().toString());
                            }
                        } else
                        {
                            sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                        }
                        break;
                    case "changemessage":
                        if (args.length > 4)
                        {
                            if (sender instanceof Player && !((Player) sender).hasPermission("sofa.changemessage"))
                            {
                                return true;
                            }
                            try
                            {
                                if (!messageLogic.ChangeUserMessage(sender, args))
                                {
                                    sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                                } else
                                {
                                    sender.sendMessage(lang.getMessagePlugin("THE_MESSAGE_TO_USER", args[1]));
                                }
                            } catch (Exception e)
                            {
                                sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                                LOG.info(e.getStackTrace().toString());
                            }
                        } else
                        {
                            sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING"));
                        }
                        break;
                    case "removemessage":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.removemessage"))
                        {
                            return true;
                        }
                        try
                        {
                            if (messageLogic.RemoveUserMessage(sender, args[1]))
                            {
                                sender.sendMessage(lang.getMessagePlugin("THE_MESSAGE_TO_USER") + " " + args[1] + " " + lang.getMessagePlugin("DELETED"));
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                            LOG.info(e.getStackTrace().toString());
                        }
                        break;
                    case "changeenable":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.changeenable"))
                        {
                            return true;
                        }
                        try
                        {
                            if (args.length == 2)
                            {
                                boolean flag = (args[1].compareTo("true") == 0 ? true : false);
                                if (messageLogic.ChangeEnableGiveItems(flag))
                                {
                                    if (flag)
                                    {
                                        sender.sendMessage(lang.getMessagePlugin("PLAYERS_CAN_NOW_FIND_ITEMS"));
                                    } else
                                    {
                                        sender.sendMessage(lang.getMessagePlugin("PLAYERS_CANT_NOW_FIND_ITEMS"));
                                    }
                                }
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                            LOG.info(e.getStackTrace().toString());
                        }
                        break;
                    case "setlanguage":
                        if (sender instanceof Player && !((Player) sender).hasPermission("sofa.setlanguage"))
                        {
                            return true;
                        }
                        try
                        {
                            if (args.length == 2)
                            {
                                if (lang.ChangeCurrentLang(args[1]))
                                {
                                    sender.sendMessage(lang.getMessagePlugin("CHANGES_ARE_SAVED"));
                                } else
                                {
                                    sender.sendMessage(lang.getMessagePlugin("INCORRECTLY_STRING") + "/sofa setlanguage <string>");
                                }
                            }
                        } catch (Exception e)
                        {
                            sender.sendMessage(lang.getMessagePlugin("SOMETHING_IS_WRONG"));
                            LOG.warning(e.getStackTrace().toString());
                        }
                        break;
                    default:
                        HelpMessage(sender);
                }
            } else
            {
                HelpMessage(sender);
            }
        }
        return true;
    }

    //Создание конфига
    private void createConfig()
    {
        if (!pluginFolder.exists())//если папка плагина не найдена
        {
            try
            {
                pluginFolder.mkdir();//создаем новую
            } catch (Exception e)//если ошибка создания
            {
                LOG.info(e.getStackTrace().toString());
                //e.printStackTrace();//пишем в консоль
            }
        }

        if (!configFile.exists())//если конфиг не найден
        {
            try
            {
                configFile.createNewFile();//создаём файл конфигурации
                getConfig().options().copyDefaults(true);//копируем из конфига по умолчанию
            } catch (Exception e)//если ошибка
            {
                LOG.info(e.getStackTrace().toString());
                //e.printStackTrace();//пишем в консоль
            }
        }
    }

    /**
     * Загружает конфигурации и инициализирует переменные
     */
    private void loadConfig()
    {

        this.sneaking = getConfig().getBoolean("sneaking");
        this.distance = getConfig().getDouble("distance");
        this.autorotation = getConfig().getBoolean("autorotation");
        this.antifallThrough = getConfig().getBoolean("antifallthrough");
        LOG.info("[SitOfSofa] Language loading...");
        lang = new Localization(getDataFolder(), "lang.yml");
        LOG.info("[SitOfSofa] Language load.");
        LOG.info("[SitOfSofa] Config Stool loading...");
        this.Stool = new StoolConfig(getDataFolder(), "config.yml", getServer().getPluginManager().getPlugin("Spout") != null);
        LOG.info("[SitOfSofa] Config Stool load.");
        LOG.info("[SitOfSofa] Message and chance settings loading...");
        //инициализируем и загружаем конфиг для сообщений
        messageLogic = new MessageLogic("usermessageAndChance.yml", getDataFolder(), true);
        LOG.info("[SitOfSofa] Message and chance settings load.");
        if (this.antifallThrough)
        {
            antiFallThroughThreadyRun();
        }
        try
        {
            this.metricsLite = new MetricsLite(this);
            this.metricsLite.start();
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] MetricsLite error.{0}", e.getStackTrace().toString());
        }
        u = new Update(getConfig().getBoolean("update.updatechecked"), getServer().getVersion(), getDescription().getVersion(), "sitofsofa", getConfig().getLong("update.updatetimeout"));
        u.Run();
        CommamdsUser = new HashMap<>();
        powered = new PoweredLogic();
    }

    /**
     * Поднимает провалившехся игроков
     */
    private void antiFallThrough()
    {
        try
        {
            Random rand = new Random();
            while (true && antifallThrough)
            {
                {
                    for (Map.Entry<Player, Block> plb : Stool.getSittingPlayers().entrySet())
                    {
                        Block block = plb.getValue();
                        Location loc = block.getLocation();
                        Player pl = plb.getKey();
                        loc.add(Stool.getSittingDepthX(block), Stool.getSittingDepthY(block), Stool.getSittingDepthZ(block)).setYaw(pl.getLocation().getYaw());
                        Stool.getSittingPlayersNameFalg().put(plb.getKey().getDisplayName(), true);
                        //LOG.info(loc.toString());
                        pl.teleport(loc);
                        Stool.getSittingPlayersNameFalg().put(plb.getKey().getDisplayName(), false);
                    }
                    Thread.sleep(2000 + rand.nextInt(3) * 12000l);
                }
            }
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "{0}\n{1}", new Object[]
            {
                e.getMessage(), e.getStackTrace().toString()
            });
        }
    }

    /**
     * Запускат подъём провалившехся игроков
     */
    protected void antiFallThroughThreadyRun()
    {

        //Создание потока
        Thread antiFallThroughThready;
        antiFallThroughThready = new Thread(new Runnable()
        {
            @Override
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                antiFallThrough();
            }
        });
        antiFallThroughThready.start();	//Запуск потока
    }

    private void commandOnSign(Player player, CommandOnChairs com, Block block)
    {
        try
        {
            //Выводим сообщение если надо
            messageLogic.PrintMessageSitdown(player);
            if (!CommamdsUser.containsKey(player))
            {
                CommamdsUser.put(player, com);
            }
            com.Run();

            //Шанс получить предмет
            messageLogic.comeСhance(player, com.isChance());
        } catch (Exception e)
        {
            LOG.log(Level.INFO, "[SitOfSofa] COnS {0}", e.getStackTrace().toString());
        }
    }

    //Метод описывающий как игрок садится
    public void sitDown(Player player, Block block, CommandOnChairs com)
    {
        if (player.getGameMode() != GameMode.CREATIVE)//если игрок не креатив
        {
            player.setAllowFlight(!player.getAllowFlight());
            player.setFlying(player.getAllowFlight());
        }
        Location Loc = block.getLocation().add(this.Stool.getSittingDepthX(block), this.Stool.getSittingDepthY(block), this.Stool.getSittingDepthZ(block));

        if (this.autorotation)// если включён авто поворот :)
        {
            int Yaw;
            if (com.getIsStool() == 1)
            {
                Yaw = this.Stool.getSofaOrientation(block);
            } else
            {
                Yaw = block.getData();
            }
            Loc.setYaw(this.Stool.Rotati(Yaw));//поворот игрока
        }
        player.teleport(Loc);//опускаем игрока на пол блока и центруем
        Packet40EntityMetadata packet = new Packet40EntityMetadata(player.getEntityId(), new SittingDataWatcher((byte) 0x4), false);

        //функция для комманд на табличках
        commandOnSign(player, com, block);
        for (Player p : getServer().getOnlinePlayers())//если игрок онлайн
        {
            //применяем изменения
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
        this.Stool.getSittingPlayers().put(player, block);
        this.Stool.getSittingPlayersNameFalg().put(player.getDisplayName(), false);
    }

//Метод вставания игрока
    public void standUp(Player player)
    {
        Location loc = player.getLocation();
        loc.add(0f, 1f, 0f);

        if (player.getGameMode() != GameMode.CREATIVE)
        {
            player.setAllowFlight(!player.getAllowFlight());
            player.setFlying(player.getAllowFlight());
        }
        Packet40EntityMetadata packet = new Packet40EntityMetadata(player.getEntityId(), new SittingDataWatcher((byte) 0x00), false);
        //Выводим сообщение если надо
        messageLogic.PrintMessageStandUp(player);

        for (Player p : getServer().getOnlinePlayers())
        {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }

        if (CommamdsUser.containsKey(player))
        {
            powered.poweredDisable(player);
            CommamdsUser.remove(player);
        }
        Stool.getSittingPlayers().remove(player);
        Stool.getSittingPlayersNameFalg().remove(player.getDisplayName());
        player.setSneaking(false);
        player.teleport(loc);//Подкидываем что бы не провалился сквозь пол :)
    }

    private void HelpMessage(CommandSender sender)
    {
        sender.sendMessage(
                "/sofa reload " + lang.getMessagePlugin("HELP_RELOAD")
                + "§f\n /sofa version " + lang.getMessagePlugin("OR") + "§e /sofa ver" + lang.getMessagePlugin("HELP_VERSION")
                + "§f\n /sofa addmessage <NikName> <true/false> <\"String\"> <true/false> <\"String\"> " + lang.getMessagePlugin("FOR_ADD_NEW_MESSAGE")
                + "§f\n /sofa settimeout <0..long.Max> " + lang.getMessagePlugin("TO_CHANGE_DEALY_INTERVAL_REMESSAGES")
                + "§f\n /sofa setstartmessage <\"String\"> " + lang.getMessagePlugin("CHANGE_FIRST_PART_OF_MESSAGE")
                + "§f\n /sofa setrestoreshealth <true/false> " + lang.getMessagePlugin("RESTORE_HEALTH_OF_THE_PLAYERS_WHEN_SITS")
                + "§f\n /sofa changemessage <NikName> <true/false> <\"String\"> <true/false> <\"String\"> " + lang.getMessagePlugin("CHANGE_MESSAGE_FOR_SPECIFIED_USER")
                + "§f\n /sofa cm <true/false> <\"String\"> <true/false> <\"String\"> " + lang.getMessagePlugin("CHANGE_MESSAGE_FOR_CURRENT_USER")
                + "§f\n /sofa removemessage <NikName> <true/false> <\"String\"> <true/false> <\"String\"> " + lang.getMessagePlugin("REMOVE_MESSAGE_FOR_SPECIFIED_USER")
                + "§f\n /sofa changeenable <true/false> " + lang.getMessagePlugin("CANT_PLAYERS_FIND_ITEMS")
                + "§f\n /sofa setlanguage <Lang> " + "§eTo change the language / Для смены языка");
    }
}
