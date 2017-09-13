package kieran.app.address.model;

import java.time.LocalDate;


import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kieran.app.address.util.LocalDateAdapter;


public class Person {

    private final StringProperty studentName;
    private final StringProperty parent;
    private final StringProperty address;
    private final IntegerProperty phoneNum;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> birthday;
    private final StringProperty comments;
    
    private Payment paymentObj = new Payment();

    
    public Person() {
        this(null, null);
    }

   //Constructor
    public Person(String studentName, String parent) {
        this.studentName = new SimpleStringProperty(studentName);
        this.parent = new SimpleStringProperty(parent);

        // Some initial data for testing.
        this.address = new SimpleStringProperty("some street");
        this.phoneNum = new SimpleIntegerProperty(1234);
        this.email = new SimpleStringProperty("some email");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.comments = new SimpleStringProperty("Comments");
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getParent() {
        return parent.get();
    }

    public void setParent(String parent) {
        this.parent.set(parent);
    }

    public StringProperty parentProperty() {
        return parent;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public int getPhoneNum() {
        return phoneNum.get();
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum.set(phoneNum);
    }

    public IntegerProperty phoneNumProperty() {
        return phoneNum;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

	public String getComments() {
		return comments.get();
	}
	
	public void setComments(String comments) {
		this.comments.set(comments);
		
	}
	
	public StringProperty commentsProperty(){
		return comments;
	}
	public Payment getPaymentObj(){
		return paymentObj;
	}
	public void setPaymentObj(Payment paymentObj) {
		this.paymentObj = paymentObj;
		
	}
	
}