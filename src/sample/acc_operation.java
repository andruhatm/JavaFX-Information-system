package sample;

import java.util.Date;

/* Class for filling table using obj-s generated from DB*/
public class acc_operation {

    private int Id;
    private int id_rentor;
    private int id_recvizit;
    private Date date;
    private double amount;
    private String comment;
    private int renter_type;
    private String currency;
    private String op_type;

    public acc_operation(int Id, int id_rentor, int id_recvizit, Date date, double amount, String comment, int renter_type, String currency, String op_type){

        this.Id=Id;
        this.id_rentor=id_rentor;
        this.id_recvizit=id_recvizit;
        this.date=date;
        this.amount=amount;
        this.comment=comment;
        this.renter_type=renter_type;
        this.currency=currency;
        this.op_type=op_type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_rentor() {
        return id_rentor;
    }

    public void setId_rentor(int id_rentor) {
        this.id_rentor = id_rentor;
    }

    public int getId_recvizit() {
        return id_recvizit;
    }

    public void setId_recvizit(int id_recvizit) {
        this.id_recvizit = id_recvizit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRenter_type() {
        return renter_type;
    }

    public void setRenter_type(int renter_type) {
        this.renter_type = renter_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }
}
