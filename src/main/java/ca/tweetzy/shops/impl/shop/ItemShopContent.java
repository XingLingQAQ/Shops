package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public final class ItemShopContent extends AbstractShopContent {

	@Getter
	@Setter
	private ItemStack item;

	public ItemShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack item, final int minPurchaseQty, final double buyPrice, final double sellPrice) {
		super(id, ShopContentType.ITEM, shopId.toLowerCase(), minPurchaseQty, buyPrice, sellPrice, true, true);
		this.item = item;
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
		return this.sellPrice;
	}

	@Override
	public void setSellPrice(double price) {
		this.sellPrice = price;
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
	public void store(@NonNull Consumer<ShopContent> stored) {
		Shops.getDataManager().insertServerShopContent(this, (error, created) -> {
			if (error == null)
				stored.accept(created);
		});
	}

	@Override
	public void sync(@Nullable Consumer<SynchronizeResult> syncResult) {
		Shops.getDataManager().updateServerShopContent(this, (error, updateStatus) -> {
			if (syncResult != null)
				syncResult.accept(error == null ? updateStatus ? SynchronizeResult.SUCCESS : SynchronizeResult.FAILURE : SynchronizeResult.FAILURE);
		});
	}

	public static ItemShopContent blank(@NonNull final String shopId) {
		return new ItemShopContent(
				UUID.randomUUID(),
				shopId,
				CompMaterial.CHEST.parseItem(),
				1,
				1,
				1
		);
	}
}