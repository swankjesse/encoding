package com.publicobject.encoding;

/** Generates bitmaps with gradients from each of the four corners. */
public final class GradientBitmapFactory {
  final int width;
  final int height;
  final int size;
  final int nw;
  final int ne;
  final int se;
  final int sw;

  public GradientBitmapFactory(int width, int height, int nw, int ne, int se, int sw) {
    this.width = width;
    this.height = height;
    this.size = width * height;
    this.nw = nw;
    this.ne = ne;
    this.se = se;
    this.sw = sw;
  }

  public Bitmap create() {
    int[][] pixels = new int[height][];
    for (int y = 0; y < height; y++) {
      pixels[y] = new int[width];
      for (int x = 0; x < width; x++) {
        pixels[y][x] = color(x, y);
      }
    }
    return new Bitmap(pixels);
  }

  /** Returns the color of the pixel at {@code x, y}. */
  private int color(int x, int y) {
    return subpixelColor(x, y, 0xff0000, 16, 32)
        | subpixelColor(x, y, 0x00ff00, 8, 16)
        | subpixelColor(x, y, 0x0000ff, 0, 32);
  }

  /**
   * Use {@code mask} and {@code shift} to extract a single component (red, green, or blue) from
   * each of the corners and compute its color at {@code x, y}.
   */
  private int subpixelColor(int x, int y, int mask, int shift, int i) {
    int nx = width - x;
    int ny = height - y;
    int a = ((nw & mask) >> shift) * nx * ny / size;
    int b = ((ne & mask) >> shift) * x * ny / size;
    int c = ((se & mask) >> shift) * x * y / size;
    int d = ((sw & mask) >> shift) * nx * y / size;
    return ((a + b + c + d) << shift) / i * i;
  }
}
