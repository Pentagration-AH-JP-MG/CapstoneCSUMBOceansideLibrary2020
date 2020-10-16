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

public class CheckinInfo {

  private String itemBarcode;
  private String fillLocation;

  public CheckinInfo(){}

  public CheckinInfo(String itemBarcode, String fillLocation) {
    this.itemBarcode = itemBarcode;
    this.fillLocation = fillLocation;
  }

  public String getItemBarcode() { return itemBarcode; }

  public void setItemBarcode(String itemBarcode) { this.itemBarcode = itemBarcode; }

  public String getFillLocation() { return fillLocation; }

  public void setFillLocation(String fillLocation) { this.fillLocation = fillLocation; }
}
