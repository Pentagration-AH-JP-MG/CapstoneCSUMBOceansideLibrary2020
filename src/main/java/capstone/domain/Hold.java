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

public class Hold {

    private Character fillLocation;
    private int fillPriority;
    private int rowCount;
    private long holdID;
    private long itemID;
    private long birlBib;
    private long patronID;
    private Character itemStatus;
    private String holdType;
    private String fName;
    private String lName;
    private String checkedInDate;
    private String holdPlacedDate;
    private String title;
    private String locationName;
    private String callNumber;
    private String pickupLocation;
    private String branchName;
    private Character pcode2;


    public Hold(){}

    public Hold(Character fillLocation, int fillPriority, int rowCount, long holdID, long itemID,
        long birlBib, long patronID, Character itemStatus, String holdType, String pickupLocation,
        String checkedInDate, String title, String callNumber, String holdPlacedDate,
        String fName, String lName, String locationName, String branchName, Character pcode2){
        super();
        this.fillLocation = fillLocation;
        this.fillPriority = fillPriority;
        this.rowCount = rowCount;
        this.holdID = holdID; //sierra_view.locationname.name
        this.itemID = itemID;
        this.birlBib = birlBib;
        this.patronID = patronID; //geoid.field_content
        this.itemStatus = itemStatus;
        this.holdType = holdType; //hold_type
        this.fName = fName; //sierra_view.patron_record_fullname.first_name
        this.lName = lName; //sierra_view.patron_record_fullname.last_name
        this.checkedInDate = checkedInDate; //sierra_view.item_view.last_checkin_gmt
        this.holdPlacedDate = holdPlacedDate;//sierra_view.hold.placed_gmt
        this.title = title; //sierra_view.bib_view.title
        this.locationName = locationName; //sierra_view.location_name.name
        this.callNumber = callNumber; //sierra_view.item_record_property.call_number_norm
        this.pickupLocation = pickupLocation;
        this.branchName = branchName;
        this.pcode2 = pcode2;

    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public long getHoldID() {
        return holdID;
    }

    public void setHoldID(long holdID) {
        this.holdID = holdID;
    }

    public long getPatronID() {
        return patronID;
    }

    public void setPatronID(long patronID) {
        this.patronID = patronID;
    }

    public String getCheckedInDate() {
        return checkedInDate;
    }

    public void setCheckedInDate(String checkedInDate) {
        this.checkedInDate = checkedInDate;
    }

    public String getHoldPlacedDate() {
        return holdPlacedDate;
    }

    public void setHoldPlacedDate(String holdPlacedDate) {
        this.holdPlacedDate = holdPlacedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public char getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Character itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Character getPcode2() {
        return pcode2;
    }

    public void setPcode2(Character pcode2) {
        this.pcode2 = pcode2;
    }

    public Character getFillLocation() {
        return fillLocation;
    }

    public void setFillLocation(Character fillLocation) {
        this.fillLocation = fillLocation;
    }

    public int getFillPriority() {
        return fillPriority;
    }

    public void setFillPriority(int fillPriority) {
        this.fillPriority = fillPriority;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getBirlBib() {
        return birlBib;
    }

    public void setBirlBib(long birlBib) {
        this.birlBib = birlBib;
    }

    public String getBranchName() { return branchName; }

    public void setBranchName(String branchName) { this.branchName = branchName; }

    @Override
    public String toString() {
        return "Hold{" +
                "holdID=" + holdID +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", patronID=" + patronID +
                ", checkedInDate='" + checkedInDate + '\'' +
                ", holdPlacedDate='" + holdPlacedDate + '\'' +
                ", title='" + title + '\'' +
                ", locationName='" + locationName + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", holdType='" + holdType + '\'' +
                '}';
    }
}
