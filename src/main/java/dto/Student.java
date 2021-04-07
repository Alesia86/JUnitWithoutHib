package dto;

import java.sql.Date;
import java.util.Objects;

public class Student {
    private int id;
    private String firstName;
    private String secondName;
    private Date birthDay;
    private int enterYear;

    public Student(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(int enterYear) {
        this.enterYear = enterYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return
                enterYear == student.enterYear &&
                        Objects.equals(firstName, student.firstName) &&
                        Objects.equals(secondName, student.secondName) &&
                        Objects.equals(birthDay, student.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, birthDay, enterYear);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthDay=" + birthDay +
                ", enterYear=" + enterYear +
                '}';
    }
}
