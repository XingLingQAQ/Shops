package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.gui.PluginListGUI;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopsAdminMainGUI extends ShopsBaseGUI {

	public ShopsAdminMainGUI(@NonNull Player player) {
		super(null, player, TranslationManager.string(Translations.GUI_ADMIN_MAIN_TITLE, "plugin_version", Shops.getInstance().getVersion()), 6);
		draw();
	}

	@Override
	protected void draw() {

		setButton(1, 1, QuickItem.of("https://textures.minecraft.net/texture/f2784307b892f52b92f74fa9db4984c4f0f02eb81c6752e5eba69ad67858427e")
				.name("<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF>")
				.lore(
						"&7You can create, update, and delete shops",
						"&7here. These are normal shops that players",
						"&7can buy items from."
				)
				.make(), click -> click.manager.showGUI(click.player, new AdminShopListGUI(this, click.player)));

		setButton(1, 4, QuickItem.of("https://textures.minecraft.net/texture/26599cbb8868237e3d864bb128ac51a0ec4a5a85e241232ee3ed6b0afac9b5c7")
				.name("<GRADIENT:5CAEFF>&lVillager Trades</GRADIENT:9F57FF>")
				.lore(
						"&7You can create, update, and delete",
						"&7custom villager trades here."
				)
				.make(), click -> {

		});

		// Discord Button
		setButton(4, 1, QuickItem.of("http://textures.minecraft.net/texture/4d42337be0bdca2128097f1c5bb1109e5c633c17926af5fb6fc20000011aeb53")
				.name("&e&LDiscord")
				.lore(
						"&8Ask questions, Get support",
						"&7Need help with &eShops&7?, Join our",
						"&7Discord server to ask questions.",
						"",
						"&8» &e&ldiscord.tweetzy.ca"
				)
				.make(), click -> {

			click.gui.close();
			Common.tellNoPrefix(click.player,
					"&8&m-----------------------------------------------------",
					"",
					ChatUtil.centerMessage("&E&lShops Support"),
					ChatUtil.centerMessage("&bhttps://discord.tweetzy.ca"),
					"&8&m-----------------------------------------------------"
			);
		});


		// Patron
		setButton(4, 4, QuickItem.of(CompMaterial.DIAMOND)
				.name("&e&lPatreon")
				.lore(
						"&8Support me on Patreon",
						"&7By supporting me on Patreon you will",
						"&7be helping me be able to continue updating",
						"&7and creating free plugins.",
						"",
						"&e&lClick &8» &7To view Patreon"
				)
				.glow(true)
				.make(), click -> {

			click.gui.close();
			Common.tellNoPrefix(click.player,
					"&8&m-----------------------------------------------------",
					"",
					ChatUtil.centerMessage("&E&lTweetzy Patreon"),
					ChatUtil.centerMessage("&bhttps://patreon.tweetzy.ca"),
					"&8&m-----------------------------------------------------"
			);
		});

		// More Plugins Button
		setButton(4, 7, QuickItem.of("http://textures.minecraft.net/texture/b94ac36d9a6fbff1c558941381e4dcf595df825913f6c383ffaa71b756a875d3")
				.name("<GRADIENT:00a87f>&lMore Plugins</GRADIENT:00ce74>")
				.lore(
						"&8View more of my plugins",
						"&7Like &eShops&7? Take a look at my other",
						"&7plugins, you might like them.",
						"",
						"&e&lClick &8» &7To view Plugins"
				)
				.make(), click -> click.manager.showGUI(click.player, new PluginListGUI(player)));
	}
}
