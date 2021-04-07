package dto;

import java.util.Objects;

public class Subject {
    private int id;
    private String name_subject;
    private String name_teacher;
    private String kafedra;

    public Subject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_subject() {
        return name_subject;
    }

    public void setName_subject(String name_subject) {
        this.name_subject = name_subject;
    }
    public String getName_teacher() {
        return name_teacher;
    }

    public void setName_teacher(String name_teacher) {
        this.name_teacher = name_teacher;
    }

    public String getKafedra() {
        return kafedra;
    }

    public void setKafedra(String kafedra) {
        this.kafedra = kafedra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return
                Objects.equals(name_subject, subject.name_subject) &&
                        Objects.equals(name_teacher, subject.name_teacher) &&
                        Objects.equals(kafedra, subject.kafedra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_subject, name_teacher, kafedra);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name_subject='" + name_subject + '\'' +
                ", name_teacher='" + name_teacher + '\'' +
                ", kafedra='" + kafedra + '\'' +
                '}';
    }
}
