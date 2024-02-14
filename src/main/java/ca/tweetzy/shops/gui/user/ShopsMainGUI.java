package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopsMainGUI extends ShopsBaseGUI {

	public ShopsMainGUI(Gui parent, @NonNull Player player, @NonNull String title, int rows) {
		super(parent, player, title, rows);
	}

	@Override
	protected void draw() {

	}
}
