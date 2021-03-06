package sonar.logistics.integration.multipart;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.core.api.BlockCoords;
import sonar.core.integration.fmp.FMPHelper;
import sonar.core.integration.fmp.handlers.TileHandler;
import sonar.logistics.Logistics;
import sonar.logistics.api.LogisticsAPI;
import sonar.logistics.api.connecting.IInfoEmitter;
import sonar.logistics.api.connecting.IInfoReader;
import sonar.logistics.api.info.ILogicInfo;
import sonar.logistics.client.renderers.RenderHandlers;
import sonar.logistics.common.handlers.InfoReaderHandler;
import sonar.logistics.integration.multipart.ForgeMultipartHandler.MultiPart;
import sonar.logistics.network.LogisticsGui;
import sonar.logistics.registries.BlockRegistry;
import codechicken.lib.vec.Cuboid6;

public class InfoReaderPart extends ConnectionPart implements IInfoEmitter, IInfoReader {

	public InfoReaderHandler handler = new InfoReaderHandler(true, tile());

	public InfoReaderPart() {
		super();
	}

	public InfoReaderPart(int meta) {
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
	public ILogicInfo[] getSelectedInfo() {
		return new ILogicInfo[] { handler.currentInfo(tile()), handler.getSecondaryInfo(tile()) };
	}

	@Override
	public ILogicInfo currentInfo() {
		return handler.currentInfo(tile());
	}

	public void sendAvailableData(EntityPlayer player) {
		handler.sendAvailableData(tile(), player);
	}

	public boolean activate(EntityPlayer player, MovingObjectPosition pos, ItemStack stack) {
		if (player != null) {
			sendAvailableData(player);
			player.openGui(Logistics.instance, LogisticsGui.infoNode, tile().getWorldObj(), x(), y(), z());
			return true;

		}
		return false;
	}

	@Override
	public Cuboid6 getBounds() {
		if (meta == 2 || meta == 3) {
			return new Cuboid6(6 * 0.0625, 6 * 0.0625, 0.0F, 1.0F - 6 * 0.0625, 1.0F - 6 * 0.0625, 1.0F);
		}
		if (meta == 4 || meta == 5) {
			return new Cuboid6(0.0F, 6 * 0.0625, 6 * 0.0625, 1.0F, 1.0F - 6 * 0.0625, 1.0F - 6 * 0.0625);
		}
		return new Cuboid6(4 * 0.0625, 4 * 0.0625, 4 * 0.0625, 1 - 4 * 0.0625, 1 - 4 * 0.0625, 1 - 4 * 0.0625);
	}

	@Override
	public Object getSpecialRenderer() {
		return new RenderHandlers.InfoReader();
	}

	@Override
	public MultiPart getPartType() {
		return MultiPart.INFO_READER;
	}

	@Override
	public BlockCoords getCoords() {
		return new BlockCoords(tile());
	}

	@Override
	public void addConnections() {
		LogisticsAPI.getCableHelper().addConnection(tile(), ForgeDirection.getOrientation(FMPHelper.getMeta(tile())));
	}

	@Override
	public void removeConnections() {
		LogisticsAPI.getCableHelper().removeConnection(tile(), ForgeDirection.getOrientation(FMPHelper.getMeta(tile())));
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.infoReader;
	}
}
