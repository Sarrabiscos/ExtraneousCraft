package com.astorian.exc.block;

import java.util.Random;

import com.astorian.exc.lib.RenderIDs;
import com.astorian.exc.tile.TileEntityVoidHeliograph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockVoidHeliograph extends BlockContainer {

	public BlockVoidHeliograph(int id, Material par2Material) {
		super(id, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setUnlocalizedName("voidHeliograph");
		this.setHardness(2.0F);
		this.setResistance(6000F);
	}
	
	/**
	 * How many does it drop when broken...
	 */
	public int quantityDropped(Random par1Random) {
	    return 1;
	}
	
	/**
	 * Returns the bounding black box, in this case 0.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getAABBPool().getAABB((double)par2, (double)par3, (double)par4, (double)par2, (double)par3, (double)par4);
    }
	
	/**
	 * Obviously it's not an opaque cube.
	 */
	@Override
    public boolean isOpaqueCube() {
        return false;
    }
	
	/**
	 * Block does not render as Normal blocks it has a Model...
	 */
	@Override
    public boolean renderAsNormalBlock() {
        return false;
    }
	
	/**
	 * So that the texture does show up correct.
	 */
	@Override
    public int getRenderType() {
        return -2;
    }

	/**
	 * Creates a new TileEntity instance.
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVoidHeliograph();
	}

}
