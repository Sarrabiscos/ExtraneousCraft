package updatemanager.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.src.ModLoader;

/**
 * @author Vazkii, cpw, TheWhiteWolves
 */
public class GuiModListWithUMButton extends cpw.mods.fml.client.GuiModList {

	private GuiScreen mainMenu;

	public GuiModListWithUMButton(GuiScreen mainMenu) {
		super(mainMenu);
	}

	@Override
	public void initGui() {
		super.initGui();
		controlList.add(new GuiButton(1337, 10, height - 30, fontRenderer.getStringWidth("Update Manager") + 6, 20, "Update Manager"));
	}

	// Bugfix for messed up render if there's a world behind.
	@Override
	public void drawWorldBackground(int par1) {
		drawBackground(par1);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 1337) ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new updatemanager.client.GuiModList(mainMenu));

		super.actionPerformed(button);
	}

}
