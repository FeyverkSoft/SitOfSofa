package net.Feyverk.SitOfSofa;

import net.Feyverk.SitOfSofa.Logic.CommandOnChairs;
import net.Feyverk.SitOfSofa.Structures.SittingDataWatcher;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Material;
/*
 import net.minecraft.server.v1_5_R3.EntityPlayer;
 import net.minecraft.server.v1_5_R3.Packet18ArmAnimation;
 import net.minecraft.server.v1_5_R3.Packet40EntityMetadata;
 import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
 /*/
//*
import net.minecraft.server.v1_5_R2.EntityPlayer;
import net.minecraft.server.v1_5_R2.Packet18ArmAnimation;
import net.minecraft.server.v1_5_R2.Packet40EntityMetadata;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
//*/
/*
 import net.minecraft.server.v1_4_R1.EntityPlayer;
 import net.minecraft.server.v1_4_R1.Packet18ArmAnimation;
 import net.minecraft.server.v1_4_R1.Packet40EntityMetadata;
 import org.bukkit.craftbukkit.v1_4_R1.entity.CraftPlayer;
 //*/

public class EventListener implements Listener
{

    public SitOfSofa plugin;

    public EventListener(SitOfSofa plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent event)
    {
        if (!event.getPlayer().hasPermission("sofa.programm"))
        {
            for (int i = 0; i < 4; i++)
            {
                event.setLine(0, event.getLine(i).replace("_@", "@_"));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();

            if (plugin.distance > 0 && player.getLocation().distance(block.getLocation().add(0.5, 0.5, 0.5)) > plugin.distance)
            {
                return;
            }
            // если игрок сидит
            if (plugin.Stool.getSittingPlayers().containsKey(player))
            {
                plugin.standUp(player);
                return;
            }
            
            // проверяем разрешения на посадку
            if (!player.hasPermission("sofa.sit"))
            {
                return;
            }
            
            CommandOnChairs com = plugin.Stool.checkChair(block);//комманды на стуле
            //-1 не стул, 0 - скамья, 1 - диван
            int type = com.getIsStool();
            if (type != -1)
            {
                com.setPlayer(player);
                if (!com.isPrivate(player))
                {
                    return;
                }
                // Check if player is sneaking.
                if (plugin.sneaking == false || (plugin.sneaking == true && player.isSneaking()))
                {
                    plugin.sitDown(player, block, com);
                    // Cancel BlockPlaceEvent Result, if player is rightclicking with a block in his hand.
                    event.setUseInteractedBlock(Result.DENY);
                }
            }
        }
    }

    // Если стул разрушили то игрок встаёт.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();

        //LOG.info(block.getTypeId() + " " + block.getX() + " " + block.getY() + " " + block.getZ());
        if (plugin.Stool.getSittingPlayers().containsValue(block))
        {
            //LOG.info("Conteints");
            for (Map.Entry<Player, Block> e : plugin.Stool.getSittingPlayers().entrySet())
            {
                //LOG.info(e.getValue().toString());
                if (e.getValue().equals(block))
                {
                    //LOG.info("standUp");
                    plugin.standUp((Player) e.getKey());
                    return;
                }
            }
        }
        //LOG.info(plugin.sittingPlayers.toString());
    }

    // Make standing up possible for any blocks
    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event)
    {
        if (event.getBed().getType() != Material.BED || event.getBed().getType() != Material.BED_BLOCK)
        {
            EntityPlayer ep = ((CraftPlayer) event.getPlayer()).getHandle();
            Packet18ArmAnimation arm = new Packet18ArmAnimation(ep, 3);

            for (Player p : plugin.getServer().getOnlinePlayers())
            {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(arm);
            }

            event.getPlayer().setAllowFlight(false);
            event.getPlayer().setFlying(false);
            plugin.Stool.getSittingPlayersNameFalg().remove(event.getPlayer().getDisplayName());
            plugin.Stool.getSittingPlayers().remove(event.getPlayer());
        }
    }

    // Отправляет пакеты, о приходе игроков. Но так как в момент подключения нельзя дадержка в 1 цикл.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            @Override
            public void run()//новый параллельный поток
            {
                for (Map.Entry<Player, Block> e : plugin.Stool.getSittingPlayers().entrySet())
                {
                    Packet40EntityMetadata packet = new Packet40EntityMetadata(e.getKey().getEntityId(), new SittingDataWatcher((byte) 0x04), false);
                    //((CraftPlayer) player).getHandle().netServerHandler.sendPacket(packet);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }, 1L);
    }

    // Игрок встаёт при отключении от сервера, и очищается карта с настройками сообщений
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (plugin.messageLogic.getTimeOutMap().containsKey(event.getPlayer()))
        {
            plugin.messageLogic.getTimeOutMap().remove(event.getPlayer());
        }
        if (plugin.Stool.getSittingPlayers().containsKey(event.getPlayer()))
        {
            plugin.standUp(event.getPlayer());
        }
    }

    // Если телепортанули
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        Block block = event.getTo().getBlock();

        if (event.getCause() != TeleportCause.UNKNOWN && plugin.Stool.getSittingPlayers().containsKey(event.getPlayer()))
        {
            if (plugin.antifallThrough)
            {
                if (!plugin.Stool.getSittingPlayersNameFalg().get(event.getPlayer().getDisplayName()))
                {
                    if (plugin.Stool.checkChair(block).getIsStool() == -1)
                    {
                        plugin.standUp(event.getPlayer());
                    }
                }
            } else if (plugin.Stool.checkChair(block).getIsStool() == -1)
            {
                plugin.standUp(event.getPlayer());
            }
        }
    }

    // замораживает игрока что бы тот не мог идти пока сидит
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (plugin.Stool.getSittingPlayers().containsKey(event.getPlayer()) && event.getFrom().distance(event.getTo()) > 0)
        {
            event.setTo(event.getFrom());
        }
    }

    // Prevent players from standing up by starting to sprint
    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event)
    {
        if (plugin.Stool.getSittingPlayers().containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    // Prevent players from standing up by starting to crouch
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
    {
        if (plugin.Stool.getSittingPlayers().containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        plugin.standUp(event.getEntity());
        plugin.Stool.getSittingPlayers().remove(event.getEntity());
    }
    private static final Logger LOG = Logger.getLogger(EventListener.class.getName());
}
