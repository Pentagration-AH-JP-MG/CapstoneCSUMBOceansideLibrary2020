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

public class Picked {

  private String branchName;
  private long holdID;
  private Character fillLocation;
  private String pickupLocation;
  private String title;
  private String barcode;
  private String recordNum;
  private String patronBarcode;
  private String fName;
  private String lName;
  private String fullName;
  private String address;
  private String gateCode;
  private String phone;
  private Character pcode2;
  private String pickupLocationBranch;

  public Picked(){}

  public Picked(String branchName, long holdID, Character fillLocation, String pickupLocation, String title,
      String barcode, String recordNum, String patronBarcode, String fName, String lName, String fullName,
      String address, String gateCode, String phone, Character pcode2, String pickupLocationBranch) {
    super();
    this.branchName = branchName;
    this.holdID = holdID;
    this.fillLocation = fillLocation;
    this.pickupLocation = pickupLocation;
    this.title = title;
    this.barcode = barcode;
    this.recordNum = recordNum;
    this.patronBarcode = patronBarcode;
    this.fName = fName;
    this.lName = lName;
    this.fullName = fullName;
    this.address = address;
    this.gateCode = gateCode;
    this.phone = phone;
    this.pcode2 = pcode2;
    this.pickupLocationBranch = pickupLocationBranch;
  }

  public String getBranchName() { return branchName; }

  public void setBranchName(String branchName) { this.branchName = branchName; }

  public long getHoldID() { return holdID; }

  public void setHoldID(long holdID) { this.holdID = holdID; }

  public Character getFillLocation() { return fillLocation; }

  public void setFillLocation(Character fillLocation) { this.fillLocation = fillLocation; }

  public String getPickupLocation() { return pickupLocation; }

  public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

  public String getTitle() { return title; }

  public void setTitle(String title) { this.title = title; }

  public String getBarcode() { return barcode; }

  public void setBarcode(String barcode) { this.barcode = barcode; }

  public String getRecordNum() { return recordNum; }

  public void setRecordNum(String recordNum) { this.recordNum = recordNum; }

  public String getPatronBarcode() { return patronBarcode; }

  public void setPatronBarcode(String patronBarcode) { this.patronBarcode = patronBarcode; }

  public String getfName() { return fName; }

  public void setfName(String fName) { this.fName = fName; }

  public String getlName() { return lName; }

  public void setlName(String lName) { this.lName = lName; }

  public String getFullName() { return fullName; }

  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getAddress() { return address; }

  public void setAddress(String address) { this.address = address; }

  public String getGateCode() { return gateCode; }

  public void setGateCode(String gateCode) { this.gateCode = gateCode; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public Character getPcode2() { return pcode2; }

  public void setPcode2(Character pcode2) { this.pcode2 = pcode2; }

  public String getPickupLocationBranch() { return pickupLocationBranch; }

  public void setPickupLocationBranch(String pickupLocationBranch) { this.pickupLocationBranch = pickupLocationBranch; }

  @Override
  public String toString() {
    return "Picked{" +
        "branchName=" + branchName +
        ", holdID=" + holdID +
        ", fillLocation=" + fillLocation +
        ", pickupLocation=" + pickupLocation +
        ", title='" + title + '\'' +
        ", barcode='" + barcode + '\'' +
        ", fName='" + fName + '\'' +
        ", lName='" + lName + '\'' +
        ", address=" + address +
        ", gateCode='" + gateCode + '\'' +
        ", phone='" + phone + '\'' +
        ", pcode2='" + pcode2 + '\'' +
        '}';
  }
}
