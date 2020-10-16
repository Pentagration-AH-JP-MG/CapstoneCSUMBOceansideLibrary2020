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