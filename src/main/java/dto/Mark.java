package dto;

import java.util.Objects;

public class Mark {
    private int id;
    private int studentId;
    private String secondNameOfStudent;
    private int subjectId;
    private String nameOfSubject;
    private int mark;

    public Mark(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getSecondNameOfStudent() {return secondNameOfStudent;}

    public String getNameOfSubject() {return nameOfSubject; }

    public void setSecondNameOfStudent(String secondNameOfStudent) {this.secondNameOfStudent = secondNameOfStudent;}

    public void setNameOfSubject(String nameOfSubject) {this.nameOfSubject = nameOfSubject;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark1 = (Mark) o;
        return
                studentId == mark1.studentId &&
                        subjectId == mark1.subjectId &&
                        mark == mark1.mark &&
                        Objects.equals(secondNameOfStudent, mark1.secondNameOfStudent) &&
                        Objects.equals(nameOfSubject, mark1.nameOfSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, secondNameOfStudent, subjectId, nameOfSubject, mark);
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", secondNameOfStudent='" + secondNameOfStudent + '\'' +
                ", subjectId=" + subjectId +
                ", nameOfSubject='" + nameOfSubject + '\'' +
                ", mark=" + mark +
                '}';
    }
}
