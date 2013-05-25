/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.Feyverk.SitOfSofa.Structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Пётр
 */
public class PlayersBenchs
{

    private List<HashMap<Player, List<Block>>> player_New_Block;

    public List<HashMap<Player, List<Block>>> getplayer_New_Block()
    {
        if (this.player_New_Block == null)
        {
            this.player_New_Block = new ArrayList<>();
        }
        return this.player_New_Block;
    }

    public final void setplayer_New_Block(List<HashMap<Player, List<Block>>> player_New_Block)
    {
        if (player_New_Block != null)
        {
            this.player_New_Block = player_New_Block;
        } else
        {
            this.player_New_Block = new ArrayList<>();
        }
    }

    public void addPlayerBlock(Player player, Block block)
    {
        if (block.getType() != Material.REDSTONE_TORCH_ON)
        {
            boolean flag = false;
            for (int i = 0; i < getplayer_New_Block().size(); i++)
            {
                if (getplayer_New_Block().get(i).containsKey(player))
                {
                    getplayer_New_Block().get(i).get(player).add(block);
                    flag = true;
                }
            }
            if (!flag)
            {
                HashMap<Player, List<Block>> _t = new HashMap<>();
                List<Block> _b = new ArrayList<>();
                _b.add(block);
                _t.put(player, _b);
                getplayer_New_Block().add(_t);
            }
        }
    }

    public void removePlayerBlock(Player player)
    {
        for (int i = 0; i < getplayer_New_Block().size(); i++)
        {
            if (getplayer_New_Block().get(i).containsKey(player))
            {
                getplayer_New_Block().get(i).remove(player);
            }
        }
    }

    public List<Block> getBlokListForPlayer(Player p)
    {
        for (int i = 0; i < getplayer_New_Block().size(); i++)
        {
            if (getplayer_New_Block().get(i).containsKey(p))
            {
                return getplayer_New_Block().get(i).get(p);
            }
        }
        return null;
    }

    public PlayersBenchs()
    {
        this.player_New_Block = new ArrayList<>();
    }

    public Integer getCountPlayerOnBench(Block block)
    {
        Integer count = 0;
        for (int i = 0; i < getplayer_New_Block().size(); i++)
        {
            for (Map.Entry<Player, List<Block>> entry : getplayer_New_Block().get(i).entrySet())
            {
                if (entry.getValue().contains(block))
                {
                    count++;
                }
            }
        }
        return count;
    }

    public List<Player> getAllPlayer()
    {
        List<Player> p = new ArrayList<>();
        for (HashMap<Player, List<Block>> entry : getplayer_New_Block())
        {
            for (Map.Entry<Player, List<Block>> entry1 : entry.entrySet())
            {
                if (!p.contains(entry1.getKey()))
                {
                    p.add(entry1.getKey());
                }
            }
        }
        return p;
    }
    private static final Logger LOG = Logger.getLogger(PlayersBenchs.class.getName());
}
