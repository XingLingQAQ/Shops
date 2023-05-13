package ca.tweetzy.shops.database;

import ca.tweetzy.flight.database.Callback;
import ca.tweetzy.flight.database.DataManagerAbstract;
import ca.tweetzy.flight.database.DatabaseConnector;
import ca.tweetzy.flight.database.UpdateCallback;
import ca.tweetzy.flight.utils.SerializeUtil;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.impl.shop.ServerShop;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DataManager extends DataManagerAbstract {

	public DataManager(DatabaseConnector databaseConnector, Plugin plugin) {
		super(databaseConnector, plugin);
	}

	public void insertServerShop(@NonNull final Shop shop, final Callback<Shop> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "shop (id, display_name, description, icon, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "shop WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, shop.getId().toLowerCase());

				preparedStatement.setString(1, shop.getId().toLowerCase());
				preparedStatement.setString(2, shop.getDisplayName());
				preparedStatement.setString(3, String.join(";;;", shop.getDescription()));
				preparedStatement.setString(4, SerializeUtil.encodeItem(shop.getDisplayIcon()));
				preparedStatement.setLong(5, shop.getTimeCreated());
				preparedStatement.setLong(6, shop.getLastUpdated());

				preparedStatement.executeUpdate();

				if (callback != null) {
					final ResultSet res = fetch.executeQuery();
					res.next();
					callback.accept(null, extractServerShop(res));
				}

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void getServerShops(@NonNull final Callback<List<Shop>> callback) {
		final List<Shop> shops = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "shop")) {
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					final Shop shop = extractServerShop(resultSet);
					shops.add(shop);
				}

				callback.accept(null, shops);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	private Shop extractServerShop(final ResultSet resultSet) throws SQLException {
		return new ServerShop(
				resultSet.getString("id"),
				resultSet.getString("display_name"),
				List.of(resultSet.getString("description").split(";;;")),
				SerializeUtil.decodeItem(resultSet.getString("icon")),
				new ArrayList<>(),
				resultSet.getLong("created_at"),
				resultSet.getLong("updated_at")
		);
	}


	private void resolveUpdateCallback(@Nullable UpdateCallback callback, @Nullable Exception ex) {
		if (callback != null) {
			callback.accept(ex);
		} else if (ex != null) {
			ex.printStackTrace();
		}
	}

	private void resolveCallback(@Nullable Callback<?> callback, @NotNull Exception ex) {
		if (callback != null) {
			callback.accept(ex, null);
		} else {
			ex.printStackTrace();
		}
	}
}
