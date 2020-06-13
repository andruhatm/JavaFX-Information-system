package sample;

/* Class for filling table using obj-s generated from DB*/
public class Renters {

    private int Id;
    private String name;
    private String phone;
    private String email;
    private int recvizit;
    private String renter_type;

    public Renters(int Id, String name, String phone, String email, int recvizit, String renter_type){
        this.Id=Id;
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.recvizit=recvizit;
        this.renter_type=renter_type;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRecvizit() {
        return recvizit;
    }

    public void setRecvizit(int recvizit) {
        recvizit = recvizit;
    }

    public String getRenter_type() {
        return renter_type;
    }

    public void setRenter_type(String renter_type) {
        this.renter_type = renter_type;
    }
}
