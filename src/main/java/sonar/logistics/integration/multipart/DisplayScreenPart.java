package sonar.logistics.integration.multipart;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.core.api.BlockCoords;
import sonar.core.integration.fmp.handlers.TileHandler;
import sonar.logistics.api.connecting.IInfoScreen;
import sonar.logistics.api.connecting.IInfoTile;
import sonar.logistics.api.connecting.IInfoScreen.ScreenLayout;
import sonar.logistics.api.info.ILogicInfo;
import sonar.logistics.client.renderers.RenderDisplayScreen;
import sonar.logistics.common.handlers.DisplayScreenHandler;
import sonar.logistics.integration.multipart.ForgeMultipartHandler.MultiPart;
import sonar.logistics.registries.BlockRegistry;
import sonar.logistics.registries.ItemRegistry;
import codechicken.lib.vec.Cuboid6;

public class DisplayScreenPart extends LogisticsPart.Handler implements IInfoScreen {

	public DisplayScreenHandler handler = new DisplayScreenHandler(true, tile());

	public DisplayScreenPart() {
		super();
	}

	public DisplayScreenPart(int meta) {
		super(meta);
	}

	@Override
	public TileHandler getTileHandler() {
		return handler;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return handler.canConnect(tile(), dir);
	}

	@Override
	public ILogicInfo[] getDisplayInfo() {
		return handler.getDisplayInfo();
	}

	@Override
	public ScreenLayout getScreenLayout() {
		return handler.getScreenLayout();
	}

	@Override
	public Cuboid6 getBounds() {
		float f = 0.28125F;
		float f1 = 0.78125F;
		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = 0.125F;
		if (meta == 2) {
			return new Cuboid6(f2, f, 1.0F - f4, f3, f1, 1.0F);
		}

		if (meta == 3) {
			return new Cuboid6(f2, f, 0.0F, f3, f1, f4);
		}

		if (meta == 4) {
			return new Cuboid6(1.0F - f4, f, f2, 1.0F, f1, f3);
		}

		if (meta == 5) {
			return new Cuboid6(0.0F, f, f2, f4, f1, f3);
		}
		return new Cuboid6(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ItemStack pickItem(MovingObjectPosition hit) {
		return new ItemStack(ItemRegistry.displayScreen);
	}

	@Override
	public Iterable<ItemStack> getDrops() {
		return Arrays.asList(new ItemStack(ItemRegistry.displayScreen));
	}

	@Override
	public MultiPart getPartType() {
		return MultiPart.DISPLAY_SCREEN;
	}

	@Override
	public Object getSpecialRenderer() {
		return new RenderDisplayScreen();
	}

	@Override
	public BlockCoords getCoords() {
		return new BlockCoords(tile());
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.displayScreen;
	}
}
