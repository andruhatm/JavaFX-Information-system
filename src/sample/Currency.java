package sample;

/* Class for filling table using obj-s generated from DB*/
public class Currency {

    private int Id;
    private String currencyName;
    private double value;

    public Currency(int Id,String currencyName, double value){
        this.Id=Id;
        this.currencyName=currencyName;
        this.value=value;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
