package capstone.domain;

import java.math.BigInteger;

public class SearchHold {

  // this class created to parse the item response from SQL lookup

  private BigInteger hold;

  public SearchHold(){}

  public SearchHold(BigInteger hold) {
    this.hold = hold;
  }

  public BigInteger getHold() { return hold; }

  public void setHold(BigInteger hold) { this.hold = hold; }
}