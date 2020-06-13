package sample;

import java.math.BigDecimal;

/* Class for filling table using obj-s generated from DB*/
public class Recvizit {

    private int Id;
    private String name;
    private BigDecimal INN;
    private int KPP;
    private int BIC;

    public Recvizit(int Id, String name, BigDecimal INN, int KPP, int BIC){
        this.Id = Id;
        this.name=name;
        this.INN=INN;
        this.KPP=KPP;
        this.BIC=BIC;
    }

    public String getname() {
        return name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getINN() {
        return INN;
    }

    public void setINN(BigDecimal INN) {
        this.INN = INN;
    }

    public int getKPP() {
        return KPP;
    }

    public void setKPP(int KPP) {
        this.KPP = KPP;
    }

    public int getBIC() {
        return BIC;
    }

    public void setBIC(int BIC) {
        this.BIC = BIC;
    }
}
