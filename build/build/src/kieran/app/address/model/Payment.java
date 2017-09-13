package kieran.app.address.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment {
	
	
	 private final StringProperty startDate;
	 private final StringProperty lessonDuration;
	 private final StringProperty costOfLesson;
	 private final IntegerProperty attendedLessons;
	 private final IntegerProperty  payedLessons;
	 private final StringProperty  paymentDone;
	 
	 

	 public Payment() {
	        this(null, null);
	    }
	
	
	
	 public Payment(String startDate, String lessonDuration) {
	        this.startDate = new SimpleStringProperty(startDate);
	        this.lessonDuration = new SimpleStringProperty(lessonDuration);

	        // Some initial data for testing.
	        this.costOfLesson = new SimpleStringProperty("some €");
	        this.attendedLessons = new SimpleIntegerProperty(1234);
	        this.payedLessons = new SimpleIntegerProperty(1234);
	        this.paymentDone = new SimpleStringProperty("Please input No. of lessons payed");
	    }



	public String getStartDate() {
		return startDate.get();
	}
	
	
    public void setStartDate(String startDate) {
	        this.startDate.set(startDate);
	    }

	    public StringProperty startDateProperty() {
	        return startDate;
	    }
	


	public String getLessonDuration() {
		return lessonDuration.get();
	}
	
	public void setLessonDuration (String lessonDuration) {
		this.lessonDuration.set(lessonDuration);
	}
	
	public StringProperty LessonDurationProperty(){
		return lessonDuration;
	}
	
	

	public String getCostOfLesson() {
		return costOfLesson.get();
	}
	
	public void setCostOfLesson(String costOfLesson) {
		this.costOfLesson.set(costOfLesson);
	}
	
	public StringProperty costOfLessonProperty(){
		return costOfLesson;
	}



	public int getAttendedLessons() {
		return attendedLessons.get();
	}
	
	public void setAttendedLessons(int attendedLessons) {
		this.attendedLessons.set(attendedLessons);
	}
	public IntegerProperty attendedLessonsProperty(){
		return attendedLessons;
	}

	public int getPayedLessons() {
		return payedLessons.get();
	}
	
	public void setPayedLessons(int payedLessons) {
		this.payedLessons.set(payedLessons);
	}
	public IntegerProperty payedLessonsProperty(){
		return payedLessons;
	}

	public String getPayment() {
		return paymentDone.get();
	}
	public void setPayment(String paymentDone) {
		this.paymentDone.set(paymentDone);
		
	}
	public StringProperty paymentProperty(){
		return paymentDone;
	}
	
	 
	
	
}



