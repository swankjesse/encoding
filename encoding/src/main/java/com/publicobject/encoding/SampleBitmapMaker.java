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

public final class SampleBitmapMaker {
  public static void main(String[] args) throws IOException {
    Bitmap sample = new Bitmap(new int[][] {
        new int[] { 0x0, /* Pink. */ 0xCDDC39 /* Lime. */},
        new int[] {0xE91E63, /* Blue. */ 0xffffff /* Orange. */}
    });
    sample.encodeToFile(new File("sample.bmp"));

    Bitmap gradient = new GradientBitmapFactory(1920 , 540 ,
        0X0, 0x0000ff, 0x0000ff,0X0).create();
    gradient.encodeToFile(new File("gradient.bmp"));
  }
}
