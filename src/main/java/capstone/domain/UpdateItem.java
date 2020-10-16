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