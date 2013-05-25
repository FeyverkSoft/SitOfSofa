/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.getspout.spout.block.SpoutCraftBlock;

/**
 * Класс описывает конфигурацию стульев и действия
 *
 * @author Пётр
 */
public class StoolConfig extends FunctionalConfiguration
{

    /**
     * Энергитически Активные Блоки стульев
     */
    private HashMap<Block, Player> stoolBlock = new HashMap<>();
    /**
     * Список сидящих игроков и блоки
     */
    private HashMap<Player, Block> sittingPlayers;
    /**
     * Список проверяемых игроков. Блокаторы для потоков
     */
    private HashMap<String, Boolean> sittingPlayersNameFalg;

    /**
     * Список сидящих игроков и блоки
     *
     * @return Возвращает хеш карту сидящих игроков на стуле
     */
    public HashMap<Player, Block> getSittingPlayers()
    {
        if (this.sittingPlayers == null)
        {
            this.sittingPlayers = new HashMap<>();
        }
        return this.sittingPlayers;
    }

    /**
     * Список проверяемых игроков. Блокаторы для потоков
     *
     * @return Возвращает хеш карту сидящих игроков на стуле и блокирован ли он
     * для изменения состояния.
     */
    public HashMap<String, Boolean> getSittingPlayersNameFalg()
    {
        if (sittingPlayersNameFalg == null)
        {
            this.sittingPlayersNameFalg = new HashMap<>();
        }
        return this.sittingPlayersNameFalg;
    }

    /**
     * Список сидящих игроков и блоки
     *
     * @param sittingPlayers задаёт хеш карту сидящих игроков на стуле
     */
    public final void setSittingPlayers(HashMap<Player, Block> sittingPlayers)
    {
        if (sittingPlayers != null)
        {
            this.sittingPlayers = sittingPlayers;
        } else
        {
            this.sittingPlayers = new HashMap<>();
        }
    }

    /**
     * Список проверяемых игроков. Блокаторы для потоков
     *
     * @param sittingPlayersNameFalg задаёт хеш карту сидящих игроков на стуле и
     * блокирован ли он для изменения состояния.
     */
    public final void setSittingPlayersNameFalg(HashMap<String, Boolean> sittingPlayersNameFalg)
    {
        if (sittingPlayersNameFalg != null)
        {
            this.sittingPlayersNameFalg = sittingPlayersNameFalg;
        } else
        {
            this.sittingPlayersNameFalg = new HashMap<>();
        }
    }
    /**
     * Запрещать садится если перед диваном стоит блок?
     */
    Boolean indentatiOnOnoneBlock;

    /**
     * Запрещать садится если перед диваном стоит блок?
     *
     * @return true - запрещать, false - разрешить
     */
    public Boolean getIndentatiOnOnoneBlock()
    {
        return indentatiOnOnoneBlock;
    }

    /**
     * Запрещать садится если перед диваном стоит блок?
     *
     * @param indentatiOnOnoneBlock true - запрещать, false - разрешить
     */
    public final void setIndentatiOnOnoneBlock(Boolean indentatiOnOnoneBlock)
    {
        this.indentatiOnOnoneBlock = indentatiOnOnoneBlock;
    }
    /**
     * Максимальная длина скамейке
     */
    Integer MaxLenghtBench;
    /*
     * Максимальная длина дивана
     */
    Integer MaxLenghtSofa;
    private String austeritystructure;

    /**
     * Получает ранг сложности анализатора ступенек
     *
     * @return 0 - анализ отключён, 1 - только таблички по бокам 2 - только
     * таблички с зади, 3 - полный анализ, таблички по бокам и сзади
     */
    public final int getAusterityRang()
    {
        if (austeritystructure.equalsIgnoreCase("disabled"))
        {
            return 0;
        }
        if (austeritystructure.equalsIgnoreCase("low"))
        {
            return 1;
        }
        if (austeritystructure.equalsIgnoreCase("medium"))
        {
            return 2;
        }
        if (austeritystructure.equalsIgnoreCase("hard"))
        {
            return 3;
        }
        return 0;
    }
    /**
     * Используется ли Spout
     */
    private boolean useSpout;
    /**
     * Содержит список блоков на которых можно сидеть и высоту посадки
     */
    protected Map<String, Double> sittingBenchBlocks;
    /**
     * Список блоков сидушек для дивана
     */
    protected Map<String, Double> sittingSofaBlocks;
    /**
     * Список блоков спинок для дивана
     */
    protected List<String> backSofaBlocks;

    /**
     * Словарь где ключ id блока с доп байтом, значение высота посадки
     *
     * @return
     */
    public Map<String, Double> getSittingSofaBlocks()
    {
        if (this.sittingSofaBlocks == null)
        {
            this.sittingSofaBlocks = new TreeMap<>();
        }
        return this.sittingSofaBlocks;
    }

    /**
     * Список возможных спиной у диванов
     *
     * @return
     */
    public List<String> getBackSofaBlocks()
    {
        if (this.backSofaBlocks == null)
        {
            this.backSofaBlocks = new ArrayList<>();
        }
        return this.backSofaBlocks;
    }

    /**
     * Словарь где ключ id блока с доп байтом, значение высота посадки
     *
     * @return
     */
    public Map<String, Double> getSittingBenchBlocks()
    {
        if (this.sittingBenchBlocks == null)
        {
            this.sittingBenchBlocks = new TreeMap<>();
        }
        return this.sittingBenchBlocks;
    }
    /**
     * Включены ли скамейки из ступенек?
     */
    private Boolean enableBench;
    /**
     * Включены ли диваны?
     */
    private Boolean enableSofa;

    /**
     * Получает строгость проверки стула
     *
     * @return возвращает строгость проверки стула
     */
    public String getAusteritystructure()
    {
        if (austeritystructure != null && !"".equals(austeritystructure))
        {
            return austeritystructure;
        } else
        {
            return "low";
        }
    }

    /**
     * Получает высоту посадки для данного блока
     *
     * @param block блок
     * @return высота
     */
    public Double getSittingDepthY(Block block)
    {
        if (getSittingBenchBlocks().containsKey(getTypeString(block)))
        {
            return getSittingBenchBlocks().get(getTypeString(block)) - 0.5;
        } else if (getSittingSofaBlocks().containsKey(getTypeString(block)))
        {
            return getSittingSofaBlocks().get(getTypeString(block)) - 0.5;
        }
        return 0d;
    }

    /**
     * Получает глубину посадки для заданного блока по OX
     *
     * @param block блок для которого необходимо проверить глубину посадки по OX
     * @return для ступенек она 0.5, для дивана 0.775 или 0.225 или 0.5 от
     * обстоятельств
     */
    public Double getSittingDepthX(Block block)
    {
        if (getSittingBenchBlocks().containsKey(getTypeString(block)))
        {
            return 0.5;
        } else if (getSittingSofaBlocks().containsKey(getTypeString(block)))
        {
            int _O = getSofaOrientation(block);
            if (_O == 0)
            {
                return 0.775;
            }
            if (_O == 1)
            {
                return 0.225;
            }
        }
        return 0.5;
    }

    /**
     * Получает глубину посадки для заданного блока по OZ
     *
     * @param block блок для которого необходимо проверить глубину посадки по OX
     * @return для ступенек она 0.5, для дивана 0.775 или 0.225 или 0.5 от
     * обстоятельств
     */
    public Double getSittingDepthZ(Block block)
    {
        if (getSittingBenchBlocks().containsKey(getTypeString(block)))
        {
            return 0.5;
        } else if (getSittingSofaBlocks().containsKey(getTypeString(block)))
        {
            int _O = getSofaOrientation(block);
            if (_O == 2)
            {
                return 0.775;
            }
            if (_O == 3)
            {
                return 0.225;
            }
        }
        return 0.5;
    }

    /**
     * Возвращает максимальную длину скамейки из ступенек если отрицательная или
     * >=0 то возвращает 1
     *
     * @return Возвращает максимальную длину скамейки из ступенек
     */
    public Integer getMaxLenghtBench()
    {
        if (this.MaxLenghtBench <= 0)
        {
            this.MaxLenghtBench = 1;
        }
        return this.MaxLenghtBench;
    }

    /**
     * Возвращает максимальную длину дивана если отрицательная или >=0 то
     * возвращает 1
     *
     * @return Возвращает максимальную длину дивана
     */
    public Integer getMaxLenghtSofa()
    {
        if (this.MaxLenghtSofa <= 0)
        {
            this.MaxLenghtSofa = 1;
        }
        return this.MaxLenghtSofa;
    }

    /**
     * Получает включены ли вобще сиденья из ступенек?
     *
     * @return true если да иначе false
     */
    public Boolean getEnableBench()
    {
        return enableBench;
    }

    /**
     * Получает включены ли вобще диваны?
     *
     * @return true если да иначе false
     */
    public Boolean getEnableSofa()
    {
        return enableSofa;
    }

    /**
     * Устанавливает включать ли сиденья из ступенек?
     *
     * @param enableBench true -включать иначе false
     */
    public void setEnableBench(Boolean enableBench)
    {
        this.enableBench = enableBench;
    }

    /**
     * Устанавливает включать ли диваны
     *
     * @param enableSofa true -включать иначе false
     */
    public void setEnableSofa(Boolean enableSofa)
    {
        this.enableSofa = enableSofa;
    }

    /**
     * Задаёт максимальную длину скамейки из ступенек.
     *
     * @param MaxLenghtBench положительное число больше 0, если указано 0 или
     * меньше задаёт 1
     */
    public void setMaxLenghtBench(Integer MaxLenghtBench)
    {
        this.MaxLenghtBench = MaxLenghtBench;
    }

    /**
     * Задаёт максимальную длину дивана.
     *
     * @param MaxLenghtSofa положительное число больше 0, если указано 0 или
     * меньше задаёт 1
     */
    public void setMaxLenghtSofa(Integer MaxLenghtSofa)
    {
        this.MaxLenghtSofa = MaxLenghtSofa;
    }

    /**
     * Устанавливает указаное значение списку предметов сидушек для скамеек
     *
     * @param sittingBenchBlocks массив ступенек и высоты посадки
     */
    public final void setSittingBenchBlocks(Map<String, Double> sittingBenchBlocks)
    {
        if (sittingBenchBlocks != null)
        {
            this.sittingBenchBlocks = sittingBenchBlocks;
        } else
        {
            this.sittingBenchBlocks = new TreeMap<>();

        }
    }

    /**
     * Устанавливает указаное значение списку предметов сидушек для дивана
     *
     * @param sittingSofaBlocks массив сидушек и высоты посадки
     */
    public final void setSittingSofaBlocks(Map<String, Double> sittingSofaBlocks)
    {
        if (sittingSofaBlocks != null)
        {
            this.sittingSofaBlocks = sittingSofaBlocks;
        } else
        {
            this.sittingSofaBlocks = new TreeMap<>();

        }
    }

    /**
     * Задаёт массив блоков спинок для дивана
     *
     * @param backSofaBlocks массив спинок для дивана
     */
    public void setBackSofaBlocks(List<String> backSofaBlocks)
    {
        if (backSofaBlocks != null)
        {
            this.backSofaBlocks = backSofaBlocks;
        } else
        {
            this.backSofaBlocks = new ArrayList<>();
        }
    }

    /**
     * Считывает из конфигурации карту типа Блок высота посадки
     *
     * @param path путь в конфигурационном файле
     * @return считаную карту
     */
    private Map<String, Double> readMap(String path)
    {
        Map<String, Double> _Temp = new TreeMap<>();
        for (String type : getConfig().getConfigurationSection(path).getKeys(true))
        {
            if (!type.contains(":"))
            {
                _Temp.put(type + ":" + 0, getConfig().getDouble(path + "." + type));
            }
            _Temp.put(type, getConfig().getDouble(path + "." + type));
        }
        return _Temp;
    }

    private void LoadConfig()
    {
        setEnableBench(getConfig().getBoolean("bench.enablebench"));
        setEnableSofa(getConfig().getBoolean("sofa.enablesofa"));
        if (getEnableBench())
        {
            setMaxLenghtBench(getConfig().getInt("bench.maxlenghtbench"));
            austeritystructure = getConfig().getString("bench.austeritystructure");
            setSittingBenchBlocks(readMap("bench.blocks"));
        }
        if (getEnableSofa())
        {
            setMaxLenghtSofa(getConfig().getInt("sofa.maxlenghtsofa"));
            setSittingSofaBlocks(readMap("sofa.sittingsofablocks"));
            setIndentatiOnOnoneBlock(getConfig().getBoolean("sofa.indentationononeblock"));
            ArrayList<String> _temp = new ArrayList<>();
            for (String backsofablock : getConfig().getStringList("sofa.backsofablocks"))
            {
                _temp.add(backsofablock);
            }
            setBackSofaBlocks(_temp);
        }
    }

    /**
     * Конструктор класса StoolConfig инициализирующий переменную
     *
     * @param pluginFolder - папка с конфигом плагина
     * @param NameConfigFile - название файла конфига
     * @param useSpout - юзается ли Spout
     */
    public StoolConfig(File pluginFolder, String NameConfigFile, Boolean useSpout)
    {
        try
        {
            setDataFolder(pluginFolder);
            setConfigFileName(NameConfigFile);
            setFileConfiguration(new File(getDataFolder(), getConfigFileName()));
            setSittingPlayersNameFalg(new HashMap<String, Boolean>());
            setSittingPlayers(new HashMap<Player, Block>());
            sittingBenchBlocks = new TreeMap<>();
            this.useSpout = useSpout;
            LoadConfig();
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, "[SitOfSofa] StoolConfig error {0}", e.getStackTrace().toString());
        }

    }

    /**
     * Проверяет ли является ли данный блок часть или целиком сиденьем
     *
     * @param b блок часть стула
     * @param Flag активировать ли энергией все ступеньки.
     * @return если блок не является частью конструкции то -1 если блок часть
     * скамейки то 0, часть дивана 1
     */
    public CommandOnChairs checkChair(Block b)
    {
        CommandOnChairs nChairs = new CommandOnChairs(-1);
        if (getEnableBench())//провиряем на конфигурацию ступеньки
        {
            if (getSittingBenchBlocks().containsKey(getTypeString(b)))
            {
                if ((austeritystructure.compareTo("low") == 0) || (austeritystructure.compareTo("medium")) == 0 || (austeritystructure.compareTo("hard") == 0)) //если включён режим строгих скамеек
                {
                    nChairs = highSecurity(b);
                    return nChairs;
                } else
                {
                    return new CommandOnChairs(0);
                }
            }
        }
        if (getEnableSofa())//проверяем на конфигурацию диван
        {
            if (getSittingSofaBlocks().containsKey(getTypeString(b)))
            {
                nChairs.setIsStool(checkedSofaConstruction(b) ? 1 : -1);
                return nChairs;
            }
        }
        return nChairs;
    }

    /**
     * Возвращает список строк на табличке
     *
     * @param sign Табличка
     * @return Возвращает список строк на табличке
     */
    protected List<String> textOnSign(Sign sign)
    {
        List<String> signCommand = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            if (!sign.getLine(i).isEmpty())
            {
                signCommand.add(sign.getLine(i));
            }
        }
        return signCommand;
    }

    /**
     * Проверяет строгие правила стульев
     *
     * @param block блок из которого получаем мир и координаты
     * @param hardMode Оприделяет включен ли строгий режим
     * @return allTextOnSign если это стул то содержание значения иначе null
     */
    private CommandOnChairs highSecurity(Block block)
    {
        CommandOnChairs nChairs = new CommandOnChairs();
        List<String> allTextOnSign = new ArrayList<>();
        int lenght = 1; // длина стула
        int X = 0, Z = 0;//получаем координаты
        int data = block.getData(); //Получаем ориентацию ступенек
        if (data == 1 || data == 0)//ориентация на север или юг
        {
            //бежим до конца ступенек по ряду
            while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(0, 0, Z))))
            {
                Z++;
            }
            if (getAusterityRang() == 1 || getAusterityRang() == 3)//если установлены правила табличек по бокам или строгое правило
            {
                if ((getTypeString(block.getRelative(0, 0, Z)).compareTo(68 + ":3") != 0))//проверяем следующий блок после конца ступеньки если эта табличка стоит в нужном направление то ок
                {
                    return new CommandOnChairs(-1);
                }
                allTextOnSign.addAll(textOnSign((Sign) block.getRelative(0, 0, Z).getState()));//Считываем текст с табличек
            }
            Z--;
            nChairs.setBlock(block.getRelative(0, 0, Z));
            //бежим обратно и проверяем что бы с зади ступеньки была табличка
            while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(0, 0, Z))))
            {
                if (lenght++ > getMaxLenghtBench())//если длина скамейки больше максимума
                {
                    return new CommandOnChairs(-1); // то сидеть нельзя
                }
                if ((getAusterityRang() == 2 || getAusterityRang() == 3))
                {
                    if (!getTypeString(block.getRelative((data == 0) ? 1 : - 1, 0, Z)).equals((68 + ((data == 0) ? ":5" : ":4"))))
                    {
                        return new CommandOnChairs(-1);
                    }
                    allTextOnSign.addAll(textOnSign((Sign) (block.getRelative((data == 0) ? 1 : - 1, 0, Z).getState())));//Считываем текст с табличек
                }
                Z--;
            }
            if (getAusterityRang() == 1 || getAusterityRang() == 3)//если установлены правила табличек по бокам бла бла бла см выше :)
            {
                if ((getTypeString(block.getRelative(0, 0, Z)).compareTo(68 + ":2") != 0))
                {
                    return new CommandOnChairs(-1);
                }
                allTextOnSign.addAll(textOnSign((Sign) block.getRelative(0, 0, Z).getState()));//Считываем текст с табличек
            }
        } else//ориентация на запад или восток
        {
            while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(X, 0, 0))))
            {
                X++;
            }
            if (getAusterityRang() == 1 || getAusterityRang() == 3)//если установлены правила табличек по бокам
            {
                if (getTypeString(block.getRelative(X, 0, 0)).compareTo(68 + ":5") != 0)
                {
                    return new CommandOnChairs(-1);
                }
                allTextOnSign.addAll(textOnSign((Sign) block.getRelative(X, 0, 0).getState()));//Считываем текст с табличек
            }
            X--;
            nChairs.setBlock(block.getRelative(X, 0, 0));
            while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(X, 0, 0))))
            {
                if (lenght++ > getMaxLenghtBench())//если длина скамейки больше максимума
                {
                    return new CommandOnChairs(-1); // то сидеть нельзя
                }
                if (getAusterityRang() == 2 || getAusterityRang() == 3)
                {
                    if (!getTypeString(block.getRelative(X, 0, (data == 2) ? 1 : - 1)).equals((68 + ((data == 2) ? ":3" : ":2"))))
                    {
                        return new CommandOnChairs(-1);
                    }
                    allTextOnSign.addAll(textOnSign((Sign) block.getRelative(X, 0, (data == 2) ? 1 : - 1).getState()));//Считываем текст с табличек
                }
                X--;
            }
            if (getAusterityRang() == 1 || getAusterityRang() == 3)//если установлены правила табличек по бокам
            {
                if ((getTypeString(block.getRelative(X, 0, 0)).compareTo(68 + ":4") != 0))
                {
                    return new CommandOnChairs(-1);
                }
                allTextOnSign.addAll(textOnSign((Sign) block.getRelative(X, 0, 0).getState()));//Считываем текст с табличек
            }
        }
        nChairs.setTextOnSignList(allTextOnSign);
        nChairs.setIsStool(0);
        nChairs.setLenght(lenght-1);
        return nChairs;
    }

    /**
     * Определяет по блоку сидушке ориентацию дивана в пространстве.
     *
     * @param block Блок часть сидушки
     * @return -1 - не верная конструкция 0 - север, 1 - юг, 2 - запад, 3 -
     * восток.
     */
    public int getSofaOrientation(Block block)
    {

        if (getBackSofaBlocks().contains(getTypeString(block.getRelative(1, 0, 0)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(1, 0, 1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(1, 0, -1))))
        {
            return 0;
        }
        if (getBackSofaBlocks().contains(getTypeString(block.getRelative(-1, 0, 0)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(-1, 0, 1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(-1, 0, -1))))
        {
            return 1;
        }
        //в этом случае на запад и восток 
        if (getBackSofaBlocks().contains(getTypeString(block.getRelative(-1, 0, 1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(0, 0, 1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(1, 0, 1))))
        {
            return 2;
        }

        if (getBackSofaBlocks().contains(getTypeString(block.getRelative(-1, 0, -1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(0, 0, -1)))
                && getBackSofaBlocks().contains(getTypeString(block.getRelative(1, 0, -1))))
        {
            return 3;
        }
        return -1;
    }

    /**
     * Проверяет верная ли конструкция дивана
     *
     * @param block блок сидушки дивана
     * @return true -если конструкция верная иначе false
     */
    private boolean checkedSofaConstruction(Block block)
    {
        int lenght = 1;//длина дивана
        int X = 0, Z = 0;//получаем координаты
        int data = getSofaOrientation(block); //Получаем ориентацию дивана
        if (data == 1 || data == 0)//ориентация на север или юг
        {
            //бежим по блокам сидушек до коца
            while (getSittingSofaBlocks().containsKey(getTypeString(block.getRelative(0, 0, Z))))
            {
                Z++;
            }
            if (!getBackSofaBlocks().contains(getTypeString(block.getRelative(0, 0, Z)))
                    || !getBackSofaBlocks().contains(getTypeString(block.getRelative((data == 0) ? 1 : -1, 0, Z))))
            {
                return false;
            }
            Z--;
            //бежим обратно и проверяем что бы с зади был блок спинка а с переди некакого блока не было
            while (getSittingSofaBlocks().containsKey(getTypeString(block.getRelative(0, 0, Z))))
            {
                if (lenght++ > getMaxLenghtSofa())//если длина дивана больше максимума
                {
                    return false; // то сидеть нельзя
                }
                if (!getBackSofaBlocks().contains(getTypeString(block.getRelative((data == 0) ? 1 : -1, 0, Z)))
                        || (!(getTypeString(block.getRelative((data == 0) ? -1 : 1, 0, Z)).equals("0:0"))
                        && getIndentatiOnOnoneBlock()))
                {
                    return false;
                }
                Z--;
            }

            if (!getBackSofaBlocks().contains(getTypeString(block.getRelative(0, 0, Z)))
                    || !getBackSofaBlocks().contains(getTypeString(block.getRelative((data == 0) ? 1 : -1, 0, Z))))
            {
                return false;
            }
        } else//ориентация на запад или восток
        {
            //бежим по блокам сидушек до коца
            while (getSittingSofaBlocks().containsKey(getTypeString(block.getRelative(X, 0, 0))))
            {
                X++;
            }
            if (!getBackSofaBlocks().contains(getTypeString(block.getRelative(X, 0, 0)))
                    || !getBackSofaBlocks().contains(getTypeString(block.getRelative(X, 0, (data == 2) ? 1 : -1))))
            {
                return false;
            }
            X--;
            //бежим обратно и проверяем что бы с зади был блок спинка
            while (getSittingSofaBlocks().containsKey(getTypeString(block.getRelative(X, 0, 0))))
            {
                if (lenght++ > getMaxLenghtSofa())//если длина дивана больше максимума
                {
                    return false; // то сидеть нельзя
                }
                if (!getBackSofaBlocks().contains(getTypeString(block.getRelative(X, 0, (data == 2) ? 1 : -1)))
                        || (!(getTypeString(block.getRelative(X, 0, (data == 2) ? -1 : 1)).equals("0:0"))
                        && getIndentatiOnOnoneBlock()))
                {
                    return false;
                }
                X--;
            }
            if (!getBackSofaBlocks().contains(getTypeString(block.getRelative(X, 0, 0)))
                    || !getBackSofaBlocks().contains(getTypeString(block.getRelative(X, 0, (data == 2) ? 1 : -1))))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает строковое описание блока с дополнительными данными
     *
     * @param block Блок для получения строки с id и байтом
     * @return строковое представление
     */
    public String getTypeString(Block block)
    {
        if (useSpout && ((SpoutCraftBlock) block).isCustomBlock())
        {
            return (318 + ":" + ((SpoutCraftBlock) block).getCustomBlock().getBlockId());
        } else
        {
            return (block.getTypeId() + ":" + block.getData());
        }
    }

    /**
     * Метод определяющий в какую сторону будет совершён авто поворот
     *
     * @param Y
     * @return
     */
    public Float Rotati(int Y)
    {
        switch (Y)
        {
            case 0:
                return 90f;
            case 1:
                return -90f;
            case 2:
                return 180f;
            case 3:
                return 0f;
        }
        return 0f;
    }

    public boolean poweredAllC(Block block, CommandOnChairs com, Player player)
    {
        if (com.isPoweredAll())
        {
            int X = 0, Z = 0;//получаем координаты
            int data = block.getData(); //Получаем ориентацию ступенек
            if (data == 1 || data == 0)//ориентация на север или юг
            {
                //бежим до конца ступенек по ряду
                while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(0, 0, Z))))
                {
                    stoolBlock.put(block.getRelative(0, 0, Z), player);
                    //block.getRelative(0, 0, Z).getBlockPower();
                    Z++;
                }
            } else
            {
                while (getSittingBenchBlocks().containsKey(getTypeString(block.getRelative(X, 0, 0))))
                {
                    stoolBlock.put(block.getRelative(0, 0, Z), player);
                    X++;
                }
            }
        }
        return true;
    }
    private static final Logger LOG = Logger.getLogger(StoolConfig.class.getName());
}
