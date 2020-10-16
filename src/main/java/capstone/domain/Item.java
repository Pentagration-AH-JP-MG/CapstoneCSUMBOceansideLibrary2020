/*
 * Copyright 2020 Marcus Gonzalez, Adam Houser, Jason Pettit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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