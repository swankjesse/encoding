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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;
import com.publicobject.encoding.Talk.Room;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import okio.ByteString;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class ParcelingTest {

  @Test public void test() {
    Talk talk = new Talk(72017, millis("2016-09-30T18:30:00Z"), Room.RIGHT,
        "Decoding the Secrets of Binary Data", "Jesse Wilson");
    ByteString goldenData = ByteString.decodeHex("010000005119010040345c7c5701000001000000230000004"
        + "400650063006f00640069006e00670020007400680065002000530065006300720065007400730020006f006"
        + "6002000420069006e006100720079002000440061007400610000000c0000004a00650073007300650020005"
        + "70069006c0073006f006e0000000000");
    assertThat(parcelEncode(talk)).isEqualTo(goldenData);
    assertThat(parcelDecode(goldenData, Talk.CREATOR)).isEqualTo(talk);
  }

  private <T extends Parcelable> T parcelDecode(
      ByteString byteString, Parcelable.Creator<T> creator) {
    Parcel parcel = Parcel.obtain();
    try {
      parcel.unmarshall(byteString.toByteArray(), 0, byteString.size());
      parcel.setDataPosition(0);
      return parcel.readTypedObject(creator);
    } finally {
      parcel.recycle();
    }
  }

  private <T extends Parcelable> ByteString parcelEncode(T t) {
    Parcel parcel = Parcel.obtain();
    try {
      parcel.writeTypedObject(t, 0);
      return ByteString.of(parcel.marshall());
    } finally {
      parcel.recycle();
    }
  }

  private long millis(String s) {
    try {
      DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
      iso8601.setLenient(false);
      iso8601.setTimeZone(TimeZone.getTimeZone("GMT"));
      return iso8601.parse(s).getTime();
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
