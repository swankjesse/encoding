package com.publicobject.encoding;

import android.os.Parcel;
import android.os.Parcelable;

public final class Talk implements Parcelable {
  public static final Parcelable.Creator<Talk> CREATOR = new Parcelable.Creator<Talk>() {
    @Override public Talk createFromParcel(Parcel in) {
      int id = in.readInt();
      long date = in.readLong();
      Room room = Room.values()[in.readInt()];
      String title = in.readString();
      String speaker = in.readString();
      return new Talk(id, date, room, title, speaker);
    }

    @Override public Talk[] newArray(int size) {
      return new Talk[size];
    }
  };

  final int id;
  final long date;
  final Room room;
  final String title;
  final String speaker;

  public Talk(int id, long date, Room room, String title, String speaker) {
    this.id = id;
    this.date = date;
    this.room = room;
    this.title = title;
    this.speaker = speaker;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel out, int flags) {
    out.writeInt(id);
    out.writeLong(date);
    out.writeInt(room.ordinal());
    out.writeString(title);
    out.writeString(speaker);
  }

  @Override public boolean equals(Object other) {
    return other instanceof Talk
        && ((Talk) other).id == id
        && ((Talk) other).date == date
        && ((Talk) other).room.equals(room)
        && ((Talk) other).title.equals(title)
        && ((Talk) other).speaker.equals(speaker);
  }

  @Override public int hashCode() {
    return id;
  }

  @Override public String toString() {
    return String.format("%s at %s in %s: \"%s\" by %s",
        id, date, room, title, speaker);
  }

  enum Room {
    UP, RIGHT, DOWN, LEFT
  }
}
