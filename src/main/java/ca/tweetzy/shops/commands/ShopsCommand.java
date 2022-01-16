package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.menu.main.MenuDynamicMain;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.annotation.AutoRegister;
import ca.tweetzy.tweety.command.SimpleCommandGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:03 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShopsCommand extends SimpleCommandGroup {

	@Getter
	private final static ShopsCommand instance = new ShopsCommand();

	@Override
	protected void zeroArgActions(CommandSender sender) {
		if (!(sender instanceof Player)) return;
		final Player player = (Player) sender;

		if (Settings.DYNAMIC_FILL_MAIN_MENU)
			new MenuDynamicMain().displayTo(player);
	}

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new CommandCreate());
		registerSubcommand(new CommandDelete());
		registerSubcommand(new CommandEdit());
		registerSubcommand(new CommandImport());
	}

	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.YELLOW + ChatColor.BOLD;
	}

	@Override
	protected boolean useZeroArgAction() {
		return true;
	}

	@Override
	protected String getCredits() {
		return null;
	}
}
