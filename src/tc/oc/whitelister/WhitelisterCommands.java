package tc.oc.whitelister;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;

public class WhitelisterCommands {
    public static class WhitelisterParentCommand {
        @Command(
            aliases = { "whitelister", "wl" },
            desc = "Commands for managing the whitelist",
            min = 1,
            max = -1
        )
        @NestedCommand({WhitelisterCommands.class})
        public static void whitelist(final CommandContext args, CommandSender sender) throws CommandException {
        }
    }

    @Command(
        aliases = { "reload" },
        desc = "Reload whitelist from a file",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.reload")
    public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
    }

    @Command(
        aliases = { "add", "a" },
        desc = "Add someone to the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.add")
    public static void add(final CommandContext args, final CommandSender sender) throws CommandException {
    }

    @Command(
        aliases = { "remove", "r" },
        desc = "Remove someone from the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.remove")
    public static void remove(final CommandContext args, final CommandSender sender) throws CommandException {
    }

    @Command(
        aliases = { "online" },
        desc = "Add everyone that's online to the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.online")
    public static void online(final CommandContext args, final CommandSender sender) throws CommandException {
    }

    @Command(
        aliases = { "clear" },
        desc = "Clear the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.clear")
    public static void clear(final CommandContext args, final CommandSender sender) throws CommandException {
    }
}
