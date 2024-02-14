package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.settings.Translations;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
@Setter
public final class CommandShopContent extends AbstractShopContent {


	private ItemStack icon;
	private String command;
	private String name;
	private String desc;


	public CommandShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack icon, @NonNull final String name, @NonNull final String desc, @NonNull final String command, final int minPurchaseQty, final double price) {
		super(id, ShopContentType.COMMAND, shopId.toLowerCase(), minPurchaseQty, price, 0, true, false);
		this.command = command;
		this.icon = icon;
		this.name = name;
		this.desc = desc;
	}

	@Override
	public double getBuyPrice() {
		return this.buyPrice;
	}

	@Override
	public void setBuyPrice(double price) {
		this.buyPrice = price;
	}

	@Override
	public double getSellPrice() {
		return 0;
	}

	@Override
	public void setSellPrice(double price) {
	}

	@Override
	public boolean isAllowBuy() {
		return this.allowBuy;
	}

	@Override
	public boolean isAllowSell() {
		return this.allowSell;
	}

	@Override
	public void setAllowBuy(boolean allowBuy) {
		this.allowBuy = allowBuy;
	}

	@Override
	public void setAllowSell(boolean allowSell) {
		this.allowSell = allowSell;
	}

	@Override
	public ItemStack generateDisplayItem(ShopContentDisplayType displayType) {
		final QuickItem genItem = QuickItem.of(this.icon);

		genItem.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_CMD_CONTENT_LORE,
				"is_buy_enabled", TranslationManager.string(this.allowBuy ? Translations.ENABLED : Translations.DISABLED),
				"shop_item_buy_price", this.buyPrice,
				"shop_item_purchase_qty", this.minPurchaseQty,
				"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
				"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)));

		return genItem.make();
	}

	public static CommandShopContent blank(@NonNull final String shopId) {
		return new CommandShopContent(
				UUID.randomUUID(),
				shopId,
				CompMaterial.HEART_OF_THE_SEA.parseItem(),
				"Heal",
				"You will be healed",
				"heal %player%",
				1,
				1
		);
	}
}
