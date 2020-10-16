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


public class UpdateItem {

  // this class created to parse the item response from SQL lookup

  private String token;
  private String recordNum;
  private String itemStatus;

  public UpdateItem(){}

  public UpdateItem(String token, String recordNum, String itemStatus) {
    this.token = token;
    this.recordNum = recordNum;
    this.itemStatus = itemStatus;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRecordNum() {
    return recordNum;
  }

  public void setRecordNum(String recordNum) {
    this.recordNum = recordNum;
  }

  public String getItemStatus() {
    return itemStatus;
  }

  public void setItemStatus(String itemStatus) {
    this.itemStatus = itemStatus;
  }
}