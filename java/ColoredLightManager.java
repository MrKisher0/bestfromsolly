import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ColoredLightManager {

    private static final int MAX_RADIUS = 6;

    public static void render(World world, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity camera = mc.getRenderViewEntity();

        if (camera == null) return;

        double cx = camera.lastTickPosX +
                (camera.posX - camera.lastTickPosX) * partialTicks;
        double cy = camera.lastTickPosY +
                (camera.posY - camera.lastTickPosY) * partialTicks;
        double cz = camera.lastTickPosZ +
                (camera.posZ - camera.lastTickPosZ) * partialTicks;

        for (TileEntity te : world.loadedTileEntityList) {
            if (!(te instanceof TileColorGlower)) continue;

            TileColorGlower light = (TileColorGlower) te;
            BlockPos pos = te.getPos();

            int rgb = light.getColor();
            renderLightVolume(pos, rgb, cx, cy, cz);
            renderLightOnBlocks(world, pos, rgb);
        }
    }
  
    private static void renderLightVolume(BlockPos pos, int rgb,
                                          double cx, double cy, double cz) {

        float r = ((rgb >> 16) & 255) / 255f;
        float g = ((rgb >> 8) & 255) / 255f;
        float b = (rgb & 255) / 255f;

        GlStateManager.pushMatrix();
        GlStateManager.translate(
                pos.getX() - cx + 0.5,
                pos.getY() - cy + 0.5,
                pos.getZ() - cz + 0.5
        );

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        // fullbright
        int light = 0xF000F0;
        OpenGlHelper.setLightmapTextureCoords(
                OpenGlHelper.lightmapTexUnit,
                light & 0xFFFF,
                light >> 16
        );

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBuffer();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        for (int i = 1; i <= MAX_RADIUS; i++) {
            float alpha = (1f - i / (float) MAX_RADIUS) * 0.15f;
            drawCube(buf, i, r, g, b, alpha);
        }

        t.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    //block coloring

    private static void renderLightOnBlocks(World world, BlockPos src, int rgb) {

        float r = ((rgb >> 16) & 255) / 255f;
        float g = ((rgb >> 8) & 255) / 255f;
        float b = (rgb & 255) / 255f;

        for (BlockPos p : BlockPos.getAllInBoxMutable(
                src.add(-MAX_RADIUS, -MAX_RADIUS, -MAX_RADIUS),
                src.add(MAX_RADIUS, MAX_RADIUS, MAX_RADIUS))) {

            double dist = p.distanceSq(src);
            if (dist > MAX_RADIUS * MAX_RADIUS) continue;

            float power = 1f - (float) Math.sqrt(dist) / MAX_RADIUS;
            if (power <= 0) continue;

            renderBlockOverlay(p, r, g, b, power * 0.25f);
        }
    }

    private static void renderBlockOverlay(BlockPos pos,
                                           float r, float g, float b, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBuffer();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

        RenderUtils.drawBox(buf, bb, r, g, b, alpha);

        t.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private static void drawCube(BufferBuilder buf, float size,
                                 float r, float g, float b, float a) {

        float s = size;

        RenderUtils.drawBox(buf,
                new AxisAlignedBB(-s, -s, -s, s, s, s),
                r, g, b, a
        );
    }
}
