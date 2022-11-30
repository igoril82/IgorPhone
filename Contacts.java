package IgorPhone;

public class Contacts {

    protected String name;
    protected String phoneNum;

    public Contacts(String newName, String newPhoneNum) {

        this.name = newName;
        this.phoneNum = newPhoneNum;

    }

    public void setName(String newName) {

        this.name = newName;

    }

    public void setPhoneNum(String newPhoneNum) {
        this.phoneNum = newPhoneNum;
    }

    public String getName() {

        return name;

    }

    public String getPhoneNum() {

        return phoneNum;

    }
}






