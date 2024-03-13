package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.cart.CartContent;
import ca.tweetzy.shops.api.currency.TransactionResult;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.model.ItemParser;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.Map;

public final class ShopCheckoutGUI extends ShopsBaseGUI {

	private final Shop shop;
	private final CartContent checkoutItem;

	public ShopCheckoutGUI(Gui parent, @NonNull Player player, @NonNull final Shop shop, @NonNull CartContent checkoutItem) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_CHECKOUT_TITLE));
		this.shop = shop;
		this.checkoutItem = checkoutItem;
		draw();
	}

	@Override
	protected void draw() {
		drawSelectedItem();

		Settings.GUI_CHECKOUT_ITEMS_DECREASE_ITEMS.getStringList().forEach(itemLine -> {
			final Map<String, List<String>> keyed = ItemParser.parseString(itemLine);
			final int changeValue = Integer.parseInt(keyed.get("change").get(0));

			setButton(Integer.parseInt(keyed.get("slot").get(0)), QuickItem
					.of(keyed.get("item").get(0))
					.name(TranslationManager.string(this.player, Translations.GUI_CHECKOUT_ITEMS_DECR_NAME, "decrement_value", changeValue))
					.lore(TranslationManager.list(this.player, Translations.GUI_CHECKOUT_ITEMS_DECR_LORE))
					.amount(Integer.parseInt(keyed.get("quantity").get(0)))
					.make(), click -> {

				this.checkoutItem.removeQuantity(changeValue);
				drawPriceBreakdown();
				drawSelectedItem();
			});
		});


		Settings.GUI_CHECKOUT_ITEMS_INCREASE_ITEMS.getStringList().forEach(itemLine -> {
			final Map<String, List<String>> keyed = ItemParser.parseString(itemLine);
			final int changeValue = Integer.parseInt(keyed.get("change").get(0));

			setButton(Integer.parseInt(keyed.get("slot").get(0)), QuickItem
					.of(keyed.get("item").get(0))
					.name(TranslationManager.string(this.player, Translations.GUI_CHECKOUT_ITEMS_INCR_NAME, "increment_value", changeValue))
					.lore(TranslationManager.list(this.player, Translations.GUI_CHECKOUT_ITEMS_INCR_LORE))
					.amount(Integer.parseInt(keyed.get("quantity").get(0)))
					.make(), click -> {

				this.checkoutItem.addQuantity(changeValue);
				drawPriceBreakdown();
				drawSelectedItem();
			});
		});

		setButton(getRows() - 1, 4, QuickItem
				.of(Settings.GUI_CHECKOUT_ITEMS_CHECKOUT.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_CHECKOUT_ITEMS_PURCHASE_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_CHECKOUT_ITEMS_PURCHASE_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				)).make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				final TransactionResult transactionResult = this.checkoutItem.executePurchase(player);
				click.manager.showGUI(click.player, new ShopContentsGUI(new ShopsMainGUI(null, click.player), click.player, this.shop));
			}

			if (click.clickType == ClickType.RIGHT) {
				Shops.getCartManager().getOrAdd(click.player).addItem(this.checkoutItem);
				click.manager.showGUI(click.player, new ShopContentsGUI(new ShopsMainGUI(null, click.player), click.player, this.shop));
			}

		});

		drawPriceBreakdown();
		applyBackExit();
	}

	private void drawSelectedItem() {
		setItem(1, 4, this.checkoutItem.getItem().generateDisplayItem(ShopContentDisplayType.CHECKOUT, this.checkoutItem.getQuantity()));
	}

	private void drawPriceBreakdown() {
		setItem(getRows() - 3, 4, QuickItem
				.of(Settings.GUI_CHECKOUT_ITEMS_BREAKDOWN.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_CHECKOUT_ITEMS_BREAKDOWN_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_CHECKOUT_ITEMS_BREAKDOWN_LORE,
						"checkout_item_qty", this.checkoutItem.getQuantity(),
						"checkout_item_buy_subtotal", this.checkoutItem.getQuantity() * this.checkoutItem.getItem().getBuyPrice()
				)).make());
	}
}
