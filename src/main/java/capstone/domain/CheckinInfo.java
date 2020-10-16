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
