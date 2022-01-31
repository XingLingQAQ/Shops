package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 14 2022
 * Time Created: 7:27 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopItemCommands extends MenuPagged<String> {

	private final Shop shop;
	private final ShopItem shopItem;
	private final boolean editing;

	private final Button addCommandButton;
	private final Button backButton;

	public MenuShopItemCommands(@NonNull final Shop shop, @NonNull final ShopItem shopItem, final boolean editing) {
		super(shopItem.getCommands());
		this.shop = shop;
		this.shopItem = shopItem;
		this.editing = editing;
		setTitle("&e" + shop.getId() + " &8> &eItem Commands");

		this.addCommandButton = Button.makeSimple(ItemCreator.of(CompMaterial.SLIME_BALL, "&E&lAdd Command", "", "&eClick &7to add a new command"), player -> new TitleInput(player, "&eShop Item Edit", "&7Enter new command") {

			@Override
			public boolean onResult(String line) {
				if (line == null || line.length() < 1)
					return false;

				MenuShopItemCommands.this.shopItem.getCommands().add(line);
				newInstance().displayTo(player);
				return true;
			}
		});

		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuAddShopItem(this.shop, this.shopItem, this.editing).displayTo(player));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == getBottomCenterSlot())
			return this.addCommandButton.getItem();
		if (slot == getSize() - 9)
			return this.backButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected List<Button> getButtonsToAutoRegister() {
		return Arrays.asList(this.addCommandButton, this.backButton);
	}

	@Override
	protected ItemStack convertToItemStack(String cmd) {
		return ItemCreator.of(CompMaterial.PAPER).name(cmd).lore("&dPress Drop &7to delete command").make();
	}

	@Override
	protected void onPageClick(Player player, String s, ClickType clickType) {
		this.shopItem.getCommands().remove(s);
		newInstance().displayTo(player);
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuShopItemCommands(this.shop, this.shopItem, this.editing);
	}
}