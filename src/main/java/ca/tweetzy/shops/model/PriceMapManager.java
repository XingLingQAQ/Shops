package ca.tweetzy.shops.model;

import ca.tweetzy.tweety.collection.StrictMap;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 11:38 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class PriceMapManager extends Manager {

	private final StrictMap<ItemStack, Double> prices = new StrictMap<>();

	@Override
	public void load() {

	}
}
