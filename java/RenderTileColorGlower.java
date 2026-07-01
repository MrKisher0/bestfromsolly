@SideOnly(Side.CLIENT)
public class RenderTileColorGlower extends TileEntitySpecialRenderer<TileColorGlower> {

    @Override
    public void renderTileEntityAt(TileColorGlower te, double x, double y, double z, float partialTicks, int destroyStage) {
        int rgb = te.getColor();

        float r = ((rgb >> 16) & 0xFF) / 255.0f;
        float g = ((rgb >> 8)  & 0xFF) / 255.0f;
        float b = ( rgb        & 0xFF) / 255.0f;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA
        );

        float alpha = 0.5f;

        AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 1, 1, 1).grow(0.15);

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBuffer();

        buf.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);

        addLine(buf, bb.minX, bb.minY, bb.minZ, r, g, b, alpha, bb.maxX, bb.minY, bb.minZ);
        addLine(buf, bb.maxX, bb.minY, bb.minZ, r, g, b, alpha, bb.maxX, bb.minY, bb.maxZ);
        addLine(buf, bb.maxX, bb.minY, bb.maxZ, r, g, b, alpha, bb.minX, bb.minY, bb.maxZ);
        addLine(buf, bb.minX, bb.minY, bb.maxZ, r, g, b, alpha, bb.minX, bb.minY, bb.minZ);

        t.draw();

        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }

    private void addLine(BufferBuilder buf,
                          double x1,double y1,double z1,float r,float g,float b,float a,
                          double x2,double y2,double z2) {
        buf.pos(x1, y1, z1).color(r, g, b, a).endVertex();
        buf.pos(x2, y2, z2).color(r, g, b, a).endVertex();
    }
}
