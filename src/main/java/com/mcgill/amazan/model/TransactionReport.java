package com.mcgill.amazan.model;

import java.util.UUID;

public class TransactionReport
{
  private String report;
  private String sellerUsername;
  private String buyerUsername;
  private String uuid = UUID.randomUUID().toString();

  public TransactionReport(float price, String report, String sellerUsername, String buyerUsername)
  {
    this.report = report;
    this.sellerUsername = sellerUsername;
    this.buyerUsername = buyerUsername;
    Amazan.getInstance().addTransactionReport(this);
  }

  public String getReport()
  {
    return report;
  }

  public String getSellerUsername()
  {
    return sellerUsername;
  }

  public String getBuyerUsername()
  {
    return buyerUsername;
  }

  public String getUuid() {
    return uuid;
  }

  public void setItem(String report)
  {
    this.report = report;
  }

  public void setSeller(String sellerUsername)
  {
    this.sellerUsername = sellerUsername;
  }

  public void setBuyer(String buyerUsername)
  {
    this.buyerUsername = buyerUsername;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String toString()
  {
    return super.toString() + "["+
            "report" + ":" + getReport() + "," +
          "sellerUsername" + ":" + getSellerUsername() + "," +
          "buyerUsername" + ":" + getBuyerUsername() + "]";
  }
}