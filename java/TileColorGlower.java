public class TileColorGlower extends TileEntity {

    private int color = 0xFFFF00; // RRGGBB

    public int getColor() { return color; }

    public void setColor(int rgb) {
        this.color = rgb & 0xFFFFFF;
        markDirty();
        if (world != null && !world.isRemote) {
            // опционально: sync в клиент через packet
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("color", color);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        color = compound.getInteger("color") & 0xFFFFFF;
    }
}
