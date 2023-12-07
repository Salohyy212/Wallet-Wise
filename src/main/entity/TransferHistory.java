package main.entity;

import java.time.LocalDateTime;

public class TransferHistory {
    private int idTransfer;
    private int debitTransactionId;
    private int creditTransactionId;
    private String transferDate;

    public TransferHistory(int idTransfer, int debitTransactionId, int creditTransactionId, String transferDate) {
        this.idTransfer = idTransfer;
        this.debitTransactionId = debitTransactionId;
        this.creditTransactionId = creditTransactionId;
        this.transferDate = transferDate;
    }

    public int getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        this.idTransfer = idTransfer;
    }

    public int getDebitTransactionId() {
        return debitTransactionId;
    }

    public void setDebitTransactionId(int debitTransactionId) {
        this.debitTransactionId = debitTransactionId;
    }

    public int getCreditTransactionId() {
        return creditTransactionId;
    }

    public void setCreditTransactionId(int creditTransactionId) {
        this.creditTransactionId = creditTransactionId;
    }


    public String  getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}

