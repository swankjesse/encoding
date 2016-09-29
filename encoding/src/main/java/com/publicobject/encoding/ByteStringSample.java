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

import okio.ByteString;

import static com.google.common.truth.Truth.assertThat;

public final class ByteStringSample {
  public static void main(String[] args) {
    ByteString byteString = ByteString.decodeHex("436166c3a920f09f8da9");
    assertThat(byteString.size()).isEqualTo(10);
    assertThat(byteString.getByte(0)).isEqualTo((byte) 0x43);
    assertThat(byteString.getByte(0)).isEqualTo((byte) 67);

    String cafeDonuts = byteString.utf8();
    assertThat(cafeDonuts).isEqualTo("Café \uD83C\uDF69");

    ByteString cafe = ByteString.encodeUtf8("Café");
    assertThat(byteString.startsWith(cafe)).isTrue();
  }
}
