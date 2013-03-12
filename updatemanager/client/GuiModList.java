package updatemanager.client;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.ForgeVersion;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import updatemanager.common.UpdateManager;
import updatemanager.common.UpdateManagerMod;

/**
 * @author Vazkii, cpw, TheWhiteWolves, Nerd-Boy4 
 */
public class GuiModList extends GuiScreen {

	private GuiUMModSlot modList;
	private int listWidth;
	private int selected = -1;
	private GuiScreen parentGui;

	String motwWeek = null;
	
	private GuiButton specialButton;

	protected LinkedList<UpdateManagerMod> mods;

	public GuiModList(GuiScreen parentGui) {
		super();
		this.parentGui = parentGui;
	}

	@Override
	public void initGui() {
		mods = UpdateManager.sortMods(UpdateManager.loadedModsSet, UpdateManager.loadedModsMap);
		for (UpdateManagerMod mod : mods)
			listWidth = Math.max(listWidth, fontRenderer.getStringWidth(mod.getModName()) + 20);
		StringTranslate.getInstance();
		int shiftx = 10;
		int shifty = 40;

		controlList.add(new GuiButton(0, shiftx, height - 30, fontRenderer.getStringWidth("Back") + 8, 20, "Back"));
		shiftx += fontRenderer.getStringWidth("Back") + 12;
		controlList.add(new GuiButton(1, shiftx, height - 30, fontRenderer.getStringWidth("Update Manager Forum Topic") + 8, 20, "Update Manager Forum Topic"));
		shiftx += fontRenderer.getStringWidth("Update Manager Forum Topic") + 12;
		controlList.add(new GuiButton(2, shiftx, height - 30, fontRenderer.getStringWidth("MotW") + 8, 20, "MotW"));
		shiftx += fontRenderer.getStringWidth("MotW") + 12;
		controlList.add(new GuiButton(3, shiftx, height - 30, fontRenderer.getStringWidth("Settings") + 8, 20, "Settings"));
		shiftx += fontRenderer.getStringWidth("Settings") + 12;
		controlList.add(new GuiButton(4, width - fontRenderer.getStringWidth("Website") - 12, shifty, fontRenderer.getStringWidth("Website") + 8, 20, "Website"));
		shifty += 22;
		controlList.add(new GuiButton(5, width - fontRenderer.getStringWidth("Changelog") - 12, shifty, fontRenderer.getStringWidth("Changelog") + 8, 20, "Changelog"));
		shifty += 22;
		controlList.add(new GuiButton(6, width - fontRenderer.getStringWidth("Download") - 12, shifty, fontRenderer.getStringWidth("Download") + 8, 20, "Download"));
		controlList.add(new GuiButton(7, shiftx, height - 30, fontRenderer.getStringWidth("View Downloaded Mods") + 8, 20, "View Downloaded Mods"));
		for (int i = UpdateManager.online ? 4 : 1; i <= 6; i++)
			((GuiButton) controlList.get(i)).enabled = i == 3;

		if (selected >= 0) selectModIndex(selected);

		modList = new GuiUMModSlot(this, listWidth);
		modList.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		modList.drawScreen(par1, par2, par3);
		int d = updateDownloads();
		drawCenteredString(fontRenderer, UpdateManager.online ? "Mod Update Manager by TheWhiteWolves: " + UpdateManager.getQuantEntries(Boolean.valueOf(true), UpdateManager.loadedModsMap) + "/" + UpdateManager.loadedModsSet.size() + (d > 0 ? " (" + d + " ongoing Downloads)" : ".") : "Mod Update Manager (OFFLINE)", width / 2, 16, 0xFFFFFF);
		if (UpdateManager.online) fontRenderer.drawStringWithShadow(motwWeek == null ? motwWeek = getMotwWeek("https://dl.dropbox.com/u/43671482/Update%20Manager/MotW_Week.txt") : motwWeek, 180, height - 9, 0xFFFFFF);
		int offset = listWidth + 20;
		UpdateManagerMod selectedMod;
		if (getSelected() >= 0 && (selectedMod = mods.get(getSelected())) != null) {
			int shifty = 35;
			Dimension dim = selectedMod.renderIcon(offset, shifty, this);
			offset += dim.width;
			GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			// Allows the title to be split in the correct position.
			fontRenderer.drawSplitString(selectedMod.getModName(), offset/2+1, shifty/2+(dim.height == 0 ? 0 :(dim.height/2-12))+1, (width-offset-75) / 2+1,
					(selectedMod.getModType().getHex() & 0xFCFCFC) >> 2 | (selectedMod.getModType().getHex() & 0xFF000000));
			fontRenderer.drawSplitString(selectedMod.getModName(), offset/2, shifty/2+(dim.height == 0 ? 0 :(dim.height/2-12)), (width-offset-75) / 2, selectedMod.getModType().getHex());
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			shifty += Math.max(25*calcNoLinesForSplitString(fontRenderer, selectedMod.getModName(), (width-offset-75) / 2), dim.height);
			shifty = typeLine(selectedMod.getModType() != ModType.UNDEFINED ? "Mod Type: " + selectedMod.getModType().getName() : null, offset, shifty);
			shifty = typeLine(selectedMod.getReleaseType() != ModReleaseType.RELEASED ? selectedMod.getReleaseType().getName() : null, offset, shifty, selectedMod.getReleaseType().getHex());
			shifty += 5;
			shifty = typeLine("Client Version: " + selectedMod.getUMVersion(), offset, shifty);
			shifty = typeLine("Latest Version: " + ( UpdateManager.getWebVersionFor(selectedMod)), offset, shifty);
			shifty += 15;
			if (selectedMod.addNotes() != null) {
				shifty = typeLine(selectedMod.getNotesHeadliner(), offset + 10, shifty);
				for (String s : selectedMod.addNotes())
					shifty = typeLine(s, offset + 10, shifty);
			}
			if (ThreadDownloadMod.downloadings.contains(selectedMod.getModName())) fontRenderer.drawStringWithShadow("Downloading...", width - 6 - fontRenderer.getStringWidth("Downloading..."), 110, 0x00FF00);
		}
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		UpdateManagerMod mod = null;

		if (selected >= 0) mod = mods.get(getSelected());

		if (button.enabled) switch (button.id) {
		case 0: {
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiModListWithUMButton(parentGui));
			break;
		}
		case 1: {
			UpdateManager.openWebpage(UpdateManager.umWebpage);
			break;
		}
		case 2: {
			try {
				URL url = new URL("https://dl.dropbox.com/u/43671482/Update%20Manager/MotW.txt");
				BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
				UpdateManager.openWebpage(r.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		}
		case 3: {
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiSettings(this));
			break;
		}
		case 4: {
			UpdateManager.openWebpage(mod.getModURL());
			break;
		}
		case 5: {
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiChangelog(this, mod));
			break;
		}
		case 6: {
			if (mod.getDisclaimerURL() == null) {
				if (!ThreadDownloadMod.downloadings.contains(mod.getModName())) {
					new ThreadDownloadMod(mod.getDirectDownloadURL(), mod);
					button.enabled = false;
				}
			}
			else ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiDisclaimer(this, mod));

			break;
		}
		case 7: {
			try {
				Sys.openURL("file://" +UpdateManager.getDownloadedModsDir().getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case 8: {
			mod.onSpecialButtonClicked();
			break;
		}
		}

		super.actionPerformed(button);
	}

	String getMotwWeek(String urlString) {
		try {
			URL url = new URL(urlString);
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			return r.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public void selectModIndex(int var1) {
		selected = var1;
		UpdateManagerMod mod = mods.get(var1);
		GuiButton bWebsite = (GuiButton) controlList.get(4);
		GuiButton bChangelog = (GuiButton) controlList.get(5);
		GuiButton bDownload = (GuiButton) controlList.get(6);

		if (UpdateManager.online) {
			bWebsite.enabled = true;
			bChangelog.enabled = mod.getChangelogURL() != null;
			bDownload.enabled = mod.getDirectDownloadURL() != null && !ThreadDownloadMod.downloadings.contains(mod.getModName());
		}
		
		if(specialButton != null)
			controlList.remove(specialButton);
		
		if(mod.getSpecialButtonName() != null){
			String name = mod.getSpecialButtonName();
			specialButton = new GuiButton(8, width - fontRenderer.getStringWidth(name) - 12, 106, fontRenderer.getStringWidth(name) + 8, 20, name);
			controlList.add(specialButton);
		}
	}

	int updateDownloads() {
		if (selected < 0) return 0;
		int i = ThreadDownloadMod.downloadings.size();
		GuiButton bDownload = (GuiButton) controlList.get(6);
		if (mods.get(selected).getDirectDownloadURL() != null && !ThreadDownloadMod.downloadings.contains(mods.get(selected).getModName())) bDownload.enabled = true;

		return i;
	}

	public boolean isIndexSelected(int var1) {
		return var1 == getSelected();
	}

	public int typeLine(String line, int offset, int shifty) {
		return typeLine(line, offset, shifty, 0xd7edea);
	}

	public int typeLine(String line, int offset, int shifty, int hex) {
		if (line != null) {
			fontRenderer.drawString(line, offset, shifty, hex);
			return shifty + 10;
		}
		return shifty;
	}

	public int getSelected() {
		return selected;
	}

	/**
	 * This method just counts the number of lines required for splitLine rendering. 
	 * It is basically just copied/pasted from the methods in fontrenderer
	 * If you think it would be usefull elsewhere copy/paste it to a static class.
	 */
	private int calcNoLinesForSplitString(FontRenderer fr, String string, int k){
		
		String[] var7 = string.split(" ");
        int var8 = 0;
        String var9;

        int lineCount = 0;
        for (var9 = ""; var8 < var7.length; ++var8)
        {
            String var10 = var7[var8];
            
            if (fr.getStringWidth(var10) >= k)
            {
                if (var9.length() > 0)
                {
                	lineCount++;
                }

                do
                {
                    int var11;

                    for (var11 = 1; fr.getStringWidth(var10.substring(0, var11)) < k; ++var11)
                    {
                        ;
                    }


                    lineCount++;
                    var10 = var10.substring(var11 - 1);
                }
                while (fr.getStringWidth(var10) >= k);

                var9 = var10;
            }
            else if (fr.getStringWidth(var9 + " " + var10) >= k)
            {
            	lineCount++;
                var9 = var10;
            }
            else
            {
                if (var9.length() > 0)
                {
                    var9 = var9 + " ";
                }

                var9 = var9 + var10;
            }
        }
        return lineCount+1;
            
	}
}
