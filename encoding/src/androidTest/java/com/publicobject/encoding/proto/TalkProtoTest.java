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
package com.publicobject.encoding.proto;

import android.support.test.runner.AndroidJUnit4;
import com.squareup.wire.ProtoAdapter;
import java.io.IOException;
import okio.ByteString;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public final class TalkProtoTest {
  @Test public void test() throws Exception {
    Talk talk = new Talk.Builder()
        .id(72017)
        .date(1475274600000L)
        .room(Talk.Room.RIGHT)
        .title("Decoding the Secrets of Binary Data")
        .speaker("Jesse Wilson")
        .build();

    ByteString goldenData = ByteString.decodeHex("0d511901001140ee377d57010000180222234465636f64696"
        + "e67207468652053656372657473206f662042696e61727920446174612a0c4a657373652057696c736f6e");

    assertThat(protoEncode(talk).hex()).isEqualTo(goldenData.hex());
    assertThat(protoDecode(goldenData, Talk.ADAPTER)).isEqualTo(talk);
  }

  private <T> T protoDecode(ByteString byteString, ProtoAdapter<T> adapter) throws IOException {
    return adapter.decode(byteString);
  }

  private ByteString protoEncode(Talk talk) {
    return ByteString.of(talk.encode());
  }
}
