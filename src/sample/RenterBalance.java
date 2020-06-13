package sample;

/* Class for filling table using obj-s generated from DB*/
public class RenterBalance {

    private int Id;
    private String name;
    private double balance;
    private double saldo;

    public RenterBalance(int Id,String name,double balance,double saldo){
        this.Id=Id;
        this.name=name;
        this.balance=balance;
        this.saldo=saldo;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
