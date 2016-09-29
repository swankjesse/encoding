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

public final class CharacterLoops {
  public void printCodePoints(String s) {
    for (int i = 0, size = s.length(); i < size; ) {
      int c = s.codePointAt(i);
      System.out.printf("The code point at %d is '%c'%n", i, c);
      i += Character.charCount(c);
    }
  }

  public void printChars(String s) {
    for (int i = 0, size = s.length(); i < size; i++) {
      char c = s.charAt(i);
      System.out.printf("The code point at %d is '%c'%n", i, c);
    }
  }

  public static void main(String[] args) {
    CharacterLoops characterLoops = new CharacterLoops();
    System.out.println("CHARS");
    characterLoops.printChars("Café \uD83C\uDF69.");
    System.out.println();

    System.out.println("CODE POINTS");
    characterLoops.printCodePoints("Café \uD83C\uDF69.");
  }
}
