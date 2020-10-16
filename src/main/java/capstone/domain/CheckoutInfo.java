package capstone.domain;

public class CheckoutInfo {

  private String itemBarcode;
  private String patronBarcode;

  public CheckoutInfo(){}

  public CheckoutInfo(String itemBarcode, String patronBarcode) {
    this.itemBarcode = itemBarcode;
    this.patronBarcode = patronBarcode;
  }

  public String getItemBarcode() { return itemBarcode; }

  public void setItemBarcode(String itemBarcode) { this.itemBarcode = itemBarcode; }

  public String getPatronBarcode() { return patronBarcode; }

  public void setPatronBarcode(String patronBarcode) { this.patronBarcode = patronBarcode; }
}
