package updatemanager.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author Vazkii, TheWhiteWolves
 */
public class CapeHandler {

	public static final String ironCapeURL = "https://dl.dropbox.com/u/43671482/Update%20Manager/capeModder.png";
	public static final String goldCapeURL = "https://dl.dropbox.com/u/43671482/Update%20Manager/capeHelper.png";
	public static final String diamondCapeURL = "https://dl.dropbox.com/u/43671482/Update%20Manager/capeCreator.png";

	public static List<String> ironPlayers = new LinkedList();
	public static List<String> goldPlayers = new LinkedList();
	public static List<String> umCreators = new LinkedList();

	private static Thread linkedThread;

	public static void initCapes() {
		loadCapes();

		if (linkedThread == null) linkedThread = new CapeUpdateThread();
	}

	public static void loadCapes() {
		ironPlayers.clear();
		fillModders();
		goldPlayers.clear();
		fillHelpers();
		umCreators.clear();
		System.out.println("");
	}

	static void fillModders() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropbox.com/u/43671482/Update%20Manager/Capes%20for%20Modders.txt").openStream()));
			String line;
			while ((line = reader.readLine()) != null)
				ironPlayers.add(line.toLowerCase());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void fillHelpers() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropbox.com/u/43671482/Update%20Manager/Capes%20for%20Contributers.txt").openStream()));
			String line;
			while ((line = reader.readLine()) != null)
				goldPlayers.add(line.toLowerCase());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void fillCreators() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropbox.com/u/43671482/Update%20Manager/Capes%20for%20Creators.txt").openStream()));
			String line;
			while ((line = reader.readLine()) != null)
				umCreators.add(line.toLowerCase());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCapes(World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer p : players)
			if (ironPlayers.contains(p.username.toLowerCase())) {
				p.cloakUrl = ironCapeURL;
				p.playerCloakUrl = ironCapeURL;
			}
			else if (goldPlayers.contains(p.username.toLowerCase())) {
				p.cloakUrl = goldCapeURL;
				p.playerCloakUrl = goldCapeURL;
			}
			else if (umCreators.contains(p.username.toLowerCase())) {
				p.cloakUrl = diamondCapeURL;
				p.playerCloakUrl = diamondCapeURL;
			}
	}
}
