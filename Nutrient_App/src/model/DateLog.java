package src.model;

import java.util.Date;

public class DateLog {
    private int userID;
    private int dateLogId;
    private Date date;

    public DateLog(int userID, Date date) {
        this.userID = userID;
        this.date = date;
    }
    public int getUserID() {
        return userID;
    }

    public int getDateLogId() {
        return dateLogId;
    }

    public Date getDate() {
        return date;
    }

    public void setDateLogId(int dateLogId) {
        this.dateLogId = dateLogId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
