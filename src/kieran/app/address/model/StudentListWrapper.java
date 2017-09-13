package kieran.app.address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
  Helper class to wrap a list of students. This is used for saving the
  list of students to XML.
 */
@XmlRootElement(name = "students")
public class StudentListWrapper {

    private List<Student> students;

    @XmlElement(name = "student")
    public List<Student> getStudent() {
        return students;
    }

    public void setStudent(List<Student> students) {
        this.students = students;
    }
}