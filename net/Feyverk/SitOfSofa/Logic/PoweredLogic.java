package net.Feyverk.SitOfSofa.Logic;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import net.Feyverk.SitOfSofa.Structures.PlayersBenchs;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Пётр
 */
public class PoweredLogic
{

    private PlayersBenchs list;
    private static final Logger LOG = Logger.getLogger(PoweredLogic.class.getName());

    /**
     * Конструктор Бла бла бла
     *
     * @param old_New_Block
     */
    public PoweredLogic()
    {
        list = new PlayersBenchs();
    }

    /**
     * Активирует ред и добавляет блоки
     *
     * @param block блок с которого всё начинается то есть берется мир
     * @param player игрок
     */
    private void addPowerBlock(Block block, Player player)
    {
        for (int i = -1; i <= 1; i++)
        {
            for (int k = -1; k <= 1; k++)
            {
                if (i == 0 && k != 0
                        || i != 0 && k == 0)
                {
                    switch (block.getRelative(i, 0, k).getType())
                    {
                        case REDSTONE_WIRE:
                        {
                            list.addPlayerBlock(player, block.getRelative(i, 0, k));
                            block.getRelative(i, 0, k).setType(Material.REDSTONE_TORCH_ON);
                        }
                        break;
                        case WALL_SIGN:
                        {
                            switch (block.getRelative(i, 0, k).getData())
                            {
                                case (byte) 2:
                                    if (block.getRelative(i, 0, k - 1).getType() == Material.REDSTONE_WIRE)
                                    {
                                        list.addPlayerBlock(player, block.getRelative(i, 0, k - 1));
                                        block.getRelative(i, 0, k - 1).setType(Material.REDSTONE_TORCH_ON);
                                    }
                                    break;
                                case (byte) 3:
                                    if (block.getRelative(i, 0, k + 1).getType() == Material.REDSTONE_WIRE)
                                    {
                                        list.addPlayerBlock(player, block.getRelative(i, 0, k + 1));
                                        block.getRelative(i, 0, k + 1).setType(Material.REDSTONE_TORCH_ON);
                                    }
                                    break;
                                case (byte) 4:
                                    if (block.getRelative(i - 1, 0, k).getType() == Material.REDSTONE_WIRE)
                                    {
                                        list.addPlayerBlock(player, block.getRelative(i - 1, 0, k));
                                        block.getRelative(i - 1, 0, k).setType(Material.REDSTONE_TORCH_ON);
                                    }
                                    break;
                                case (byte) 5:
                                    if (block.getRelative(i + 1, 0, k).getType() == Material.REDSTONE_WIRE)
                                    {
                                        list.addPlayerBlock(player, block.getRelative(i + 1, 0, k));
                                        block.getRelative(i + 1, 0, k).setType(Material.REDSTONE_TORCH_ON);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Включение энергии на скамейко
     *
     * @param block блок скамейки
     * @param flag Флаг
     */
    public void poweredEnable(Block block, Player player, CommandOnChairs com)
    {
        Block endBlock = com.getBlock();
        Integer Lenght = com.getLenght();
        if (com.isPoweredAll())
        {
            //LOG.info(endBlock.toString());
            list.addPlayerBlock(player, endBlock);
            int data = block.getData(); //Получаем ориентацию ступенек
            if (data == 1 || data == 0)//ориентация на север или юг
            {
                for (Integer i = 0; i < Lenght; i++)
                {
                    addPowerBlock(endBlock.getRelative(0, 0, i - (Lenght - 1)), player);
                }
            } else
            {
                for (Integer i = 0; i < Lenght; i++)
                {
                    addPowerBlock(endBlock.getRelative(i - (Lenght - 1), 0, 0), player);
                }
            }
        } else if (com.isPowered())
        {
            addPowerBlock(block, player);
        }
    }

    /**
     * Выключение энергии у скамейко.
     *
     * @param block блок часть скамейки
     * @param flag флаг выключать всю или только часть.
     */
    public void poweredDisable(Player player)
    {
        List<Block> d;
        if ((d = list.getBlokListForPlayer(player)) != null)
        {
            if (list.getCountPlayerOnBench(d.get(0)) <= 1)
            {
                for (Block b : d)
                {
                    if (b.getType() != Material.WALL_SIGN && (b.getType() == Material.REDSTONE_WIRE || b.getType() == Material.REDSTONE_TORCH_ON))
                    {
                        b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ()).setType(Material.REDSTONE_WIRE);
                        b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ()).setData(b.getData());
                    }
                }
                list.removePlayerBlock(player);
            }
        } else
        {
            list.removePlayerBlock(player);
        }
    }

    public void poweredDisableAll()
    {
        List<Player> p = list.getAllPlayer();
        for (Player pl : p)
        {
            poweredDisable(pl);
        }

    }
}
