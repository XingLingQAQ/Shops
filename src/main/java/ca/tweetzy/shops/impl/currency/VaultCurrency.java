package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.api.AbstractShopCurrency;
import ca.tweetzy.tweety.model.HookManager;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 9:00 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultCurrency extends AbstractShopCurrency {

	@Override
	public boolean isEnabled() {
		return HookManager.isVaultLoaded();
	}

	@Override
	public @NonNull String getPluginName() {
		return "Vault";
	}

	@Override
	public String getName() {
		return "Vault";
	}

	@Override
	public boolean withdraw(@NonNull Player player, double amount) {
		try {
			HookManager.withdraw(player, amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deposit(@NonNull Player player, double amount) {
		try {
			HookManager.deposit(player, amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean has(@NonNull Player player, double amount) {
		return HookManager.getBalance(player) >= amount;
	}
}
