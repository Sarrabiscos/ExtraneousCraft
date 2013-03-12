package exc.update.common.commands;

import exc.update.common.Settings;
import exc.update.common.UpdateManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.src.ModLoader;
import net.minecraft.server.management.ServerConfigurationManager;

/**
 * @author Vazkii, TheWhiteWolves, Kovu
 */
public class CommandUMEnable extends CommandBase {

	@Override
	public String getCommandName() {
		return "um-enable";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		MinecraftServer server = ModLoader.getMinecraftServerInstance();
		ServerConfigurationManager manager = UpdateManager.getServerConfig(server);
		
		if(var1.canCommandSenderUseCommand(0, getCommandName()) || !Settings.getBoolean("opOnly")){
		Settings.setBoolean("loginCheck", true);
		Settings.setInt("checkDelay", 900);
		UpdateManager.sendChatMessageToPlayer(manager.getPlayerForUsername(var1.getCommandSenderName()), "Update Manager has been re-enabled.");
		}
	}

}
