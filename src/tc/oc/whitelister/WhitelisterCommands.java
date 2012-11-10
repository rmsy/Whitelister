package tc.oc.whitelister;

import java.util.Random;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;

import com.sk89q.bukkit.pagination.PaginatedResult;
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
        Bukkit.getServer().reloadWhitelist();
        sender.sendMessage(ChatColor.GREEN + "Whitelist reloaded");
    }

    @Command(
        aliases = { "toggle", "t" },
        desc = "Toggle the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.toggle")
    public static void toggle(final CommandContext args, final CommandSender sender) throws CommandException {
        Bukkit.getServer().setWhitelist(!Bukkit.getServer().hasWhitelist());
        sender.sendMessage(ChatColor.GOLD + "Whitelist is now: " + (Bukkit.getServer().hasWhitelist() ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF"));
    }

    @Command(
        aliases = { "on" },
        desc = "Turn the whitelist on",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.on")
    public static void on(final CommandContext args, final CommandSender sender) throws CommandException {
        Bukkit.getServer().setWhitelist(true);
        sender.sendMessage(ChatColor.GOLD + "Whitelist is now: " + ChatColor.GREEN + "ON");
    }

    @Command(
        aliases = { "off" },
        desc = "Turn the whitelist off",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.off")
    public static void off(final CommandContext args, final CommandSender sender) throws CommandException {
        Bukkit.getServer().setWhitelist(false);
        sender.sendMessage(ChatColor.GOLD + "Whitelist is now: " + ChatColor.RED + "OFF");
    }

    @Command(
        aliases = { "status", "s" },
        desc = "Get status of whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.status")
    public static void status(final CommandContext args, final CommandSender sender) throws CommandException {
        Set<OfflinePlayer> whitelisted = Bukkit.getServer().getWhitelistedPlayers();
        Player[] online = Bukkit.getServer().getOnlinePlayers();

        int whitelistedPlayers = whitelisted.size();
        int onlinePlayers = online.length;
        int onlineWhitelistedPlayers = 0;

        for(OfflinePlayer player : online) {
            if(whitelisted.contains(player)) {
                onlineWhitelistedPlayers++;
            }
        }

        sender.sendMessage(ChatColor.GOLD + "Whitelist Status: " + (Bukkit.getServer().hasWhitelist() ? ChatColor.GREEN  + "ON" : ChatColor.RED + "OFF"));
        sender.sendMessage(ChatColor.GREEN + "There " + (onlinePlayers == 1 ? "is" : "are") + " currently " + ChatColor.RED + String.valueOf(onlinePlayers) + ChatColor.GREEN + " player" + (onlinePlayers == 1 ? "" : "s") + " online");
        sender.sendMessage(ChatColor.GREEN + "There " + (whitelistedPlayers == 1 ? "is" : "are") + " currently " + ChatColor.RED + String.valueOf(whitelistedPlayers) + ChatColor.GREEN + " whitelisted player" + (whitelistedPlayers == 1 ? "" : "s"));
        sender.sendMessage(ChatColor.GREEN + "Of the online players, " + ChatColor.RED + String.valueOf(onlineWhitelistedPlayers) + ChatColor.GREEN + (onlineWhitelistedPlayers == 1 ? " is" : " are") + " whitelisted");
    }

    @Command(
        aliases = { "add", "a" },
        desc = "Add someone to the whitelist",
        min = 1,
        max = 1
    )
    @CommandPermissions("whitelister.add")
    public static void add(final CommandContext args, final CommandSender sender) throws CommandException {
        OfflinePlayer player = matchSinglePlayer(sender, args.getString(0));
        Bukkit.getServer().addWhitelist(player);
        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GOLD + player.getName() + ChatColor.GREEN +" to the whitelist");
    }

    @Command(
        aliases = { "remove", "r" },
        desc = "Remove someone from the whitelist",
        min = 1,
        max = 1
    )
    @CommandPermissions("whitelister.remove")
    public static void remove(final CommandContext args, final CommandSender sender) throws CommandException {
        OfflinePlayer player = matchSinglePlayer(sender, args.getString(0));
        Bukkit.getServer().removeWhitelist(player);
        sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.GOLD + player.getName() + ChatColor.GREEN +" from the whitelist");
    }

    @Command(
        aliases = { "online" },
        desc = "Add everyone that's online to the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.online")
    public static void online(final CommandContext args, final CommandSender sender) throws CommandException {
        int count = 0;
        for(Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getServer().addWhitelist(player);
            count++;
        }
        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.RED + String.valueOf(count) + ChatColor.GREEN + " player(s) to the whitelist");
    }

    @Command(
        aliases = { "list", "l" },
        desc = "List players on the whitelist",
        min = 0,
        max = 1
    )
    @CommandPermissions("whitelister.list")
    public static void list(final CommandContext args, final CommandSender sender) throws CommandException {
        Set<OfflinePlayer> whitelisted = Bukkit.getServer().getWhitelistedPlayers();
        int count = whitelisted.size();

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Whitelisted players:");

        new PaginatedResult<OfflinePlayer>(8) {
            @Override public String format(OfflinePlayer player, int index) {
                return (index + 1) + ". " + player.getName();
            }
            @Override public String formatHeader(int page, int maxPages) {
                ChatColor dashColor = ChatColor.RED;
                ChatColor textColor = ChatColor.DARK_AQUA;
                ChatColor highlight = ChatColor.AQUA;

                String message = textColor + "Whitelisted Players" + textColor + " (" + highlight + page + textColor + " of " + highlight + maxPages + textColor + ")";
                return StringUtils.padMessage(message, "-", dashColor, textColor);
            }
        }.display(sender, whitelisted, args.getInteger(0, 1));
    }

    @Command(
        aliases = { "clear" },
        desc = "Clear the whitelist",
        min = 0,
        max = 0
    )
    @CommandPermissions("whitelister.clear")
    public static void clear(final CommandContext args, final CommandSender sender) throws CommandException {
        int count = 0;
        for(OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            Bukkit.getServer().removeWhitelist(player);
            count++;
        }
        sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.RED + String.valueOf(count) + ChatColor.GREEN + " player(s) from the whitelist");
    }

    public static OfflinePlayer matchSinglePlayer(CommandSender sender, String rawUsername) throws CommandException {
        if(rawUsername.startsWith("@")) {
            return Bukkit.getServer().getOfflinePlayer(rawUsername.substring(1));
        } else {
            // look up player according to the who is online now
            List<Player> players = Bukkit.getServer().matchPlayer(rawUsername);
            switch(players.size()) {
            case 0:
                throw new CommandException("No players matched query. Use @<name> for offline lookup.");
            case 1:
                return players.get(0);
            default:
                throw new CommandException("More than one player found! Use @<name> for exact matching.");
            }
        }
    }
}
