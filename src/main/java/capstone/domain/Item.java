package capstone.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="item_view", schema = "sierra_view")
public class Item {

  @Id
  @Column(name = "item_id")
  private long itemID;
  @Nullable
  @Column(name = "status")
  private Character status;

  public Item(){}

  public Item(long itemID, Character status) {
    this.itemID = itemID;
    this.status = status;
  }

  public long getItemID() {
    return itemID;
  }

  public void setItemID(long itemID) {
    this.itemID = itemID;
  }

  @Nullable
  public Character getStatus() {
    return status;
  }

  public void setStatus(@Nullable Character status) {
    this.status = status;
  }
}