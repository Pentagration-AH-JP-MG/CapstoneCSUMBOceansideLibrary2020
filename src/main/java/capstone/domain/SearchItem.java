package capstone.domain;

import java.math.BigInteger;

public class SearchItem {

  // this class created to parse the item response from SQL lookup

  private BigInteger bib;
  private BigInteger item;
  private int recordNum;

  public SearchItem(){}

  public SearchItem(BigInteger bib, BigInteger item, int recordNum) {
    this.recordNum = recordNum;
    this.bib = bib;
    this.item = item;
  }

  public int getRecordNum() { return recordNum; }

  public void setRecordNum(int recordNum) {
    this.recordNum = recordNum;
  }

  public BigInteger getBib() {
    return bib;
  }

  public void setBib(BigInteger bib) {
    this.bib = bib;
  }

  public BigInteger getItem() {
    return item;
  }

  public void setItem(BigInteger item) {
    this.item = item;
  }
}