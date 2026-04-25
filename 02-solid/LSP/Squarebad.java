// ============================================================
// ❌ BAD: Square extends Rectangle — LSP VIOLATION
// ============================================================
// A square must have equal sides. So when you set width,
// height must also change (and vice versa).
//
// This BREAKS the Rectangle contract! A Rectangle user expects:
//   "If I set width to 5, only width changes."
//
// But Square silently changes BOTH dimensions. This causes
// wrong area calculations and unexpected behavior.
// ============================================================

public class SquareBad extends RectangleBad {

    public SquareBad(int side) {
        super(side, side);
    }

    // Override to maintain square invariant: sides must be equal
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;   // ⚠️ SURPRISE! height also changes
    }

    @Override
    public void setHeight(int height) {
        this.width = height;   // ⚠️ SURPRISE! width also changes
        this.height = height;
    }
}
