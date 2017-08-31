package com.serjltt.listsdbs.data.db.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "repository")
public final class LocalRepository {
  @PrimaryKey private final String name; // All repository names are unique.
  @Nullable private final String description;
  @Embedded private final LocalUser owner;

  public LocalRepository(String name, @Nullable String description,
      LocalUser owner) {
    this.name = name;
    this.description = description;
    this.owner = owner;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LocalRepository that = (LocalRepository) o;
    return name.equals(that.name)
        && (description != null ? description.equals(that.description) : that.description == null)
        && owner.equals(that.owner);
  }

  @Override public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + owner.hashCode();
    return result;
  }

  @Override public String toString() {
    return "LocalRepository{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", owner=" + owner +
        '}';
  }

  public String name() {
    return name;
  }

  @Nullable public String description() {
    return description;
  }

  public LocalUser owner() {
    return owner;
  }
}
