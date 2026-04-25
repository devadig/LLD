// ============================================================
// ❌ BAD: Rectangle-Square Hierarchy VIOLATING LSP
// ============================================================
// Mathematically, a Square IS-A Rectangle (special case).
// But in code, making Square extend Rectangle BREAKS things.
// This is the most famous LSP violation example.
// ============================================================

public class RectangleBad {

    protected int width;
    protected int height;

    public RectangleBad(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{w=" + width + ", h=" + height +
               ", area=" + getArea() + "}";
    }
}
