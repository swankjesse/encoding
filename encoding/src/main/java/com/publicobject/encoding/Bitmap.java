/*
 * Copyright (C) 2016 Jesse Wilson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.publicobject.encoding;

import java.io.File;
import java.io.IOException;
import okio.BufferedSink;
import okio.Okio;

public final class Bitmap {
  private final int[][] pixels;

  public Bitmap(int[][] pixels) {
    this.pixels = pixels;
  }

  /** https://en.wikipedia.org/wiki/BMP_file_format */
  public void encode(BufferedSink sink) throws IOException {
    int height = pixels.length;
    int width = pixels[0].length;

    int bytesPerPixel = 3;
    int rowByteCountWithoutPadding = (bytesPerPixel * width);
    int rowByteCount = ((rowByteCountWithoutPadding + 3) / 4) * 4;
    int pixelDataSize = rowByteCount * height;
    int bmpHeaderSize = 14;
    int dibHeaderSize = 40;

    // BMP Header
    sink.writeUtf8("BM"); // ID.
    sink.writeIntLe(bmpHeaderSize + dibHeaderSize + pixelDataSize); // File size.
    sink.writeShortLe(0); // Unused.
    sink.writeShortLe(0); // Unused.
    sink.writeIntLe(bmpHeaderSize + dibHeaderSize); // Offset of pixel data.

    // DIB Header
    sink.writeIntLe(dibHeaderSize);
    sink.writeIntLe(width);
    sink.writeIntLe(height);
    sink.writeShortLe(1);  // Color plane count.
    sink.writeShortLe(bytesPerPixel * Byte.SIZE);
    sink.writeIntLe(0);    // No compression.
    sink.writeIntLe(16);   // Size of bitmap data including padding.
    sink.writeIntLe(2835); // Horizontal print resolution in pixels/meter. (72 dpi).
    sink.writeIntLe(2835); // Vertical print resolution in pixels/meter. (72 dpi).
    sink.writeIntLe(0);    // Palette color count.
    sink.writeIntLe(0);    // 0 important colors.

    // Pixel data.
    for (int y = height - 1; y >= 0; y--) {
      int[] row = pixels[y];
      for (int x = 0; x < width; x++) {
        int pixel = row[x];
        sink.writeByte((pixel & 0x0000ff));       // b
        sink.writeByte((pixel & 0x00ff00) >> 8);  // g
        sink.writeByte((pixel & 0xff0000) >> 16); // r
      }

      // Padding for 4-byte alignment.
      for (int p = rowByteCountWithoutPadding; p < rowByteCount; p++) {
        sink.writeByte(0);
      }
    }
  }

  public void encodeToFile(File file) throws IOException {
    try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
      encode(sink);
    }
  }
}
