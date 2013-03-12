package exc.update.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.src.ModLoader;
import net.minecraft.command.ServerCommandManager;

/**
 * @author Vazkii, TheWhiteWolves, Kovu
 */
public class UpdateManagerCommands {

	public static boolean init = false;
	
	public static void init(){
		if(init) return;
			init = true;
			
		ServerCommandManager manager = (ServerCommandManager) ModLoader.getMinecraftServerInstance().getCommandManager();
		
		manager.registerCommand(new CommandUM());
		manager.registerCommand(new CommandUMForce());
		manager.registerCommand(new CommandUMIntSetting("delay", "checkTime", "�9Changed check time from %s to %s."));
		manager.registerCommand(new CommandUMBoolSetting("op", "opOnly", "�9Set OP Only Warns to %s."));
		manager.registerCommand(new CommandUMBoolSetting("login", "loginCheck", "�9Set OP Only Warns to %s."));
		manager.registerCommand(new CommandUMReset());
		manager.registerCommand(new CommandUMDisable());
		manager.registerCommand(new CommandUMViewSettings());
		manager.registerCommand(new CommandUMEnable());
	}
	
}
