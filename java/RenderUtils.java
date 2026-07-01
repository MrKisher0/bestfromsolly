import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.AxisAlignedBB;

public class RenderUtils {

    public static void drawBox(BufferBuilder buf, AxisAlignedBB bb,
                               float r, float g, float b, float a) {

        double x1 = bb.minX, y1 = bb.minY, z1 = bb.minZ;
        double x2 = bb.maxX, y2 = bb.maxY, z2 = bb.maxZ;

        // 6 faces
        quad(buf, x1,y1,z1, x2,y1,z1, x2,y2,z1, x1,y2,z1, r,g,b,a);
        quad(buf, x1,y1,z2, x2,y1,z2, x2,y2,z2, x1,y2,z2, r,g,b,a);
        quad(buf, x1,y1,z1, x1,y1,z2, x1,y2,z2, x1,y2,z1, r,g,b,a);
        quad(buf, x2,y1,z1, x2,y1,z2, x2,y2,z2, x2,y2,z1, r,g,b,a);
        quad(buf, x1,y2,z1, x2,y2,z1, x2,y2,z2, x1,y2,z2, r,g,b,a);
        quad(buf, x1,y1,z1, x2,y1,z1, x2,y1,z2, x1,y1,z2, r,g,b,a);
    }

    private static void quad(BufferBuilder buf,
                             double x1,double y1,double z1,
                             double x2,double y2,double z2,
                             double x3,double y3,double z3,
                             double x4,double y4,double z4,
                             float r,float g,float b,float a) {

        buf.pos(x1,y1,z1).color(r,g,b,a).endVertex();
        buf.pos(x2,y2,z2).color(r,g,b,a).endVertex();
        buf.pos(x3,y3,z3).color(r,g,b,a).endVertex();
        buf.pos(x4,y4,z4).color(r,g,b,a).endVertex();
    }
             }
