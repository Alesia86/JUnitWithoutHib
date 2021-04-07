package servlet;

import dop.DaoException;
import dop.GeneralConnectionPool;
import dto.Mark;
import dto.Student;
import dto.Subject;
import service.MarkService;
import service.StudentService;
import service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Integer.parseInt;

@WebServlet("/")
public class ActionServlet extends HttpServlet {
    public static GeneralConnectionPool poolSql;
    static {
        try {
            if(poolSql==null)
            poolSql = new GeneralConnectionPool();
            poolSql.init("db.properties");
        } catch (DaoException e) {
            System.out.println("Ошибка загрузки пула");
            System.out.println(poolSql.toString());

        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*ServletContext cx=getServletContext();
        GeneralConnectionPool pool=(GeneralConnectionPool) cx.getAttribute("pool");
        if(pool==null){
            try {
                pool=GeneralConnectionPool.getInstance();
                cx.setAttribute("pool",pool);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }*/
        runServlet(req,resp);
        /*try {
            throw new ServletException();
        }catch(ServletException e){

        }*/
     // throw new ServletException();

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*ServletContext cx=getServletContext();
        GeneralConnectionPool pool=(GeneralConnectionPool) cx.getAttribute("pool");
        try {
            GeneralConnectionPool newPool=GeneralConnectionPool.getInstance();
            System.out.println( pool.equals(newPool));
        } catch (DaoException e) {
            e.printStackTrace();
        }*/



       runServlet(req,resp);
      //  throw new ServletException();
    }
    private void runServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("ACTION");
        if(action==null){
            createGeneralPage(req, resp);
        }else{
            switch (action) {

                case "ON GENERAL":
                    createGeneralPage(req,resp);
                    break;

                case "LIST OF SUBJECT":
                    createListOfSubject(req, resp);
                    break;
                case "UpdateSubjectFinish":
                    updateSubjectFinish(req, resp);
                    break;
                case "UpdateStudentFinish":
                    updateStudentFinish(req, resp);
                    break;
                case "AgreeToDeleteStudent":
                    agreeToDeleteStudent(req, resp);
                    break;
                case "AgreeToDeleteSubject":
                    agreeToDeleteSubject(req,resp);
                    break;
                case "DeleteStudentFinish":
                    deleteStudentFinish(req, resp);
                    break;
                case "DeleteSubjectFinish":
                    deleteSubjectFinish(req, resp);
                    break;
                case "CreateListOfMarkForStudent":
                    createListOfMarkForStudent(req,resp);
                    break;
                case "PrepareToAddMark":
                    prepareToAddMark(req, resp);
                    break;
                case "AddMarkFinish":
                    addMarkFinish(req,resp);
                    break;
                case "UpdateMarkFinish":
                    updateMarkFinish(req,resp);
                    break;
                case "AgreeToDeleteMark":
                    agreeToDeleteMark(req,resp);
                    break;
                case "DeleteMarkFinish":
                    deleteMarkFinish(req,resp);
                    break;
                case "FindStudent":
                    findStudent(req,resp);
                    break;
                case "AddStudent":
                    addStudent(req,resp);
                    break;
                case "AddSubject":
                    addSubject(req,resp);
                    break;
                case "FindSubject":
                    findSubject(req,resp);
                    break;
            }
        }
    }
    private int countPage(int quantityAllStudents, int quantityStudentsOnOnePage ){
      int page=0;
       if (quantityAllStudents%quantityStudentsOnOnePage!=0){
           page=quantityAllStudents/quantityStudentsOnOnePage+1;
       }else{
            page=quantityAllStudents/quantityStudentsOnOnePage;
       }
       return page;
    }


    private void createGeneralPage(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

        StudentService serviceStudent=null;
        Collection<Student> listTenStudents =null;
        int numberRow=10;
        boolean colorFirstName=false;
        boolean colorSecondName=false;
        boolean colorEnterYear=false;
        boolean colorBirthDay=false;

        String findFirstName=req.getParameter("findFirstName");
        String findSecondName=req.getParameter("findSecondName");
        String findEnterYear=req.getParameter("findEnterYear");
        String findBirthDay=req.getParameter("findBirthDay");
        int count=0;int page=0;int pages;
        boolean messageLongInquiry=false;
        int messageListEmpty=0;
        try {
             if(req.getParameter("findFirstName")==null){
                 findFirstName="";
            }
            else {
                 if (req.getParameter("findFirstName").length() > 50) {
                     colorFirstName=true;
                     count++;
                 }
             }
            if(req.getParameter("findSecondName")==null){
                findSecondName="";
            }
            else {
                if (req.getParameter("findSecondName").length() > 50) {
                    colorSecondName=true;
                    count++;
                }
            }
            if(req.getParameter("findBirthDay")==null){
                findBirthDay="";
            }
            else {
                if (req.getParameter("findBirthDay").length() > 50) {
                    colorBirthDay=true;
                    count++;
                }
            }
            if(req.getParameter("findEnterYear")==null){
                findEnterYear="";
            }
            else {
                if (req.getParameter("findEnterYear").length() > 50) {
                    colorEnterYear=true;
                    count++;
                }
            }
            req.setAttribute("colorFirstName",colorFirstName);
            req.setAttribute("colorSecondName",colorSecondName);
            req.setAttribute("colorEnterYear",colorEnterYear);
            req.setAttribute("colorBirthDay",colorBirthDay);
            req.setAttribute("findFirstName",findFirstName);
            req.setAttribute("findSecondName",findSecondName);
            req.setAttribute("findEnterYear",findEnterYear);
            req.setAttribute("findBirthDay",findBirthDay);
            //serviceStudent = new StudentService(poolSql);
            if (req.getParameter("page") == null) {
                page = 1;
            } else {
                if(isPageWrong(page, req.getParameter("page"))) {
                    getServletContext().getRequestDispatcher("/").forward(req, resp);
                }else{
                    page=Integer.parseInt(req.getParameter("page"));
                }
            }
            req.setAttribute("page",page);
            System.out.println("1_page="+page);
            serviceStudent = new StudentService(poolSql.getConnection());

if(count>0) {
    messageLongInquiry=true;
    pages=countPage(serviceStudent.countLines(poolSql.getConnection(),"","","",""),10);System.out.println("1_pages="+pages);
    listTenStudents = serviceStudent.selectGroupOfStudents(poolSql.getConnection(),"","","","",page,numberRow);
}else {
    System.out.println("Создание студента "+serviceStudent);
    pages= countPage(serviceStudent.countLines(poolSql.getConnection(),findFirstName, findSecondName, findBirthDay, findEnterYear),10);System.out.println("2_pages="+pages);
    listTenStudents = serviceStudent.selectGroupOfStudents(poolSql.getConnection(),findFirstName, findSecondName, findBirthDay, findEnterYear,page,numberRow);
    System.out.println("Список студента длина"+listTenStudents.size());

}
            if (listTenStudents.size() != 0) {
                messageListEmpty=1;
            }
        req.setAttribute("messageListEmpty",messageListEmpty);
            req.setAttribute("messageLongInquiry",messageLongInquiry);
        req.setAttribute("pages",pages);
            req.setAttribute("listTenStudents",listTenStudents);
            getServletContext().getRequestDispatcher("/WEB-INF/view/CreateGeneralPage.jsp").forward(req,resp);

        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                serviceStudent.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }

    private void createListOfSubject( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectService serviceSubject=null;
        boolean messageListEmpty=false;
        try {
            serviceSubject = new SubjectService();
            Collection<Subject> list = serviceSubject.readAllSubject();
            req.setAttribute("list",list);
            if (list.size() != 0) {
                messageListEmpty=true;
            }
            req.setAttribute("messageListEmpty",messageListEmpty);
            getServletContext().getRequestDispatcher("/WEB-INF/view/CreateListOfSubject.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        } finally {
            try {
                serviceSubject.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }




    private void updateStudentFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent=Integer.parseInt(req.getParameter("id"));
        StudentService serviceStudent=null;
        int countField=0;

        try {
            serviceStudent = new StudentService(poolSql.getConnection());
            Student student = serviceStudent.findStudent(poolSql.getConnection(),idStudent);
            Student studentNew = new Student();

            boolean colorFirstName =false;
            boolean colorSecondName =false;
            boolean colorEnterYear =false;
            boolean colorBirthDay =false;

            String firstName=student.getFirstName();
            String secondName=student.getSecondName();
            String birthDay=""+student.getBirthDay();
            String enterYear=""+student.getEnterYear();
            if ( req.getParameter("firstName") != null && req.getParameter("secondName")!=null && req.getParameter("birthDay")!=null && req.getParameter("enterYear")!= null ) {
                firstName = req.getParameter("firstName");
                if (isNameWrong(firstName)) {
                    colorFirstName = true;
                }else{
                    countField++;
                }
                secondName = req.getParameter("secondName");
                if (isNameWrong(secondName)) {
                    colorSecondName =  true;
                }else {
                    countField++;
                }
                enterYear = req.getParameter("enterYear");
                if (isNumberWrong(enterYear)) {
                    colorEnterYear =  true;
                }else {
                    countField++;
                }
                birthDay = req.getParameter("birthDay");
                if (isDateWrong(birthDay)) {
                    colorBirthDay =  true;
                }else {
                    countField++;
                }
            }
            req.setAttribute("countField",countField);
            req.setAttribute("id",idStudent);
            req.setAttribute("colorFirstName",colorFirstName);
            req.setAttribute("colorSecondName",colorSecondName);
            req.setAttribute("colorEnterYear",colorEnterYear);
            req.setAttribute("colorBirthDay",colorBirthDay);
            req.setAttribute("firstName",firstName);
            req.setAttribute("secondName",secondName);
            req.setAttribute("enterYear",enterYear);
            req.setAttribute("birthDay",birthDay);
            if(countField==4){
                studentNew.setId(idStudent);
                studentNew.setFirstName(firstName);
                studentNew.setEnterYear(parseInt(enterYear));
                studentNew.setBirthDay(Date.valueOf(birthDay));
                studentNew.setSecondName(secondName);
                serviceStudent.updateStudent(poolSql.getConnection(),studentNew);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/view/UpdateStudentFinish.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }finally {
                    try {
                        serviceStudent.close();
                    } catch (DaoException e) {
                        req.setAttribute("errorMessage","closeDaoException");
                        getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                    }
                }
            }

    private void updateSubjectFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idSubject=Integer.parseInt(req.getParameter("id"));
        SubjectService serviceSubject=null;
        int countField=0;
        try {
            serviceSubject = new SubjectService();
            Subject subject = serviceSubject.findSubject(idSubject);

            boolean colorNameSubject =false;
            boolean colorNameTeacher =false;
            boolean colorKafedra =false;

            String nameSubject=subject.getName_subject();
            String nameTeacher=subject.getName_teacher();
            String kafedra=subject.getKafedra();
            System.out.println(kafedra);


if(req.getParameter("nameSubject")!=null && req.getParameter("nameTeacher")!=null &&req.getParameter("kafedra")!=null) {
    nameSubject = req.getParameter("nameSubject");
    if (nameSubject.length() == 0 || nameSubject.length() > 100) {
        colorNameSubject = true;

    } else {
        countField++;
    }
    nameTeacher = req.getParameter("nameTeacher");
    if (isNameWrong(nameTeacher)) {
        colorNameTeacher = true;
    } else {
        countField++;
    }
    kafedra = req.getParameter("kafedra");
    if (isNameWrong(kafedra)) {
        colorKafedra = true;
    } else {
        countField++;
    }
}
    req.setAttribute("countField",countField);
    req.setAttribute("id",idSubject);
    req.setAttribute("colorNameSubject",colorNameSubject);
    req.setAttribute("colorNameTeacher",colorNameTeacher);
    req.setAttribute("colorKafedra",colorKafedra);
    req.setAttribute("nameSubject",nameSubject);
    req.setAttribute("nameTeacher",nameTeacher);
    req.setAttribute("kafedra",kafedra);
    if(countField==3){
            Subject subjectNew = new Subject();
            subjectNew.setId(idSubject);
            subjectNew.setName_subject(nameSubject);
            subjectNew.setName_teacher(nameTeacher);
            subjectNew.setKafedra(kafedra);
                serviceSubject.updateSubject(subjectNew);

    }
            getServletContext().getRequestDispatcher("/WEB-INF/view/UpdateSubjectFinish.jsp").forward(req,resp);
        }catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
             finally {
                try {
                    serviceSubject.close();
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","closeDaoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                }
            }
    }
    private void agreeToDeleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent=parseInt(req.getParameter("id"));
        StudentService serviceStudent=null;
        try{
            serviceStudent=new StudentService(poolSql.getConnection());
            Student student=serviceStudent.findStudent(poolSql.getConnection(),idStudent);
          req.setAttribute("messageAboutDelete",1);
            req.setAttribute("student",student);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteStudent.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                serviceStudent.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }
    private void agreeToDeleteSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idSubject=parseInt(req.getParameter("id"));
        SubjectService serviceSubject=null;
        try{
            serviceSubject=new SubjectService();
            Subject  subject=serviceSubject.findSubject(idSubject);
            req.setAttribute("messageAboutDelete",1);
            req.setAttribute("subject",subject);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteSubject.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                serviceSubject.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }
    private void deleteStudentFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentService serviceStudent=null;
        int idStudent = parseInt(req.getParameter("id"));
        try {
            serviceStudent = new StudentService(poolSql.getConnection());
            serviceStudent.removeStudent(poolSql.getConnection(),idStudent);
            req.setAttribute("messageAboutDelete",2);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteStudent.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        } finally {
            try {
                serviceStudent.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }


    private void deleteSubjectFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectService serviceSubject=null;
        int idSubject = parseInt(req.getParameter("id"));
        try {
            serviceSubject = new SubjectService();
            serviceSubject.removeSubject(idSubject);
            req.setAttribute("messageAboutDelete",2);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteSubject.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        } finally {
            try {
                serviceSubject.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }

    private void createListOfMarkForStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MarkService serviceMark=null;
        StudentService serviceStudent=null;
        boolean colorNameSubject=false;
        int idStudent = parseInt(req.getParameter("idStudent"));
        req.setAttribute("idStudent",idStudent);
        String findSubject=req.getParameter("findSubject");
        req.setAttribute("findSubject",findSubject);
        if(findSubject==null){
            findSubject="";
        }else{
            if (findSubject.length() > 50) {
               colorNameSubject = true;
            }
        }
        try{
            serviceStudent=new StudentService(poolSql.getConnection());
            Student student=serviceStudent.findStudent(poolSql.getConnection(),idStudent);
            System.out.println(student);
            serviceMark = new MarkService();
            Collection<Mark> listMark = serviceMark.findMarkStudent(idStudent);
            req.setAttribute("listMark",listMark);
            req.setAttribute("student",student);
            if(listMark.size()!=0 && findSubject.length()!=0 ) {
                        Collection<Mark> listNew = new ArrayList<>();
                        for (Mark mark : listMark) {
                            if (mark.getNameOfSubject().equalsIgnoreCase(findSubject)) {
                                listNew.add(mark);
                            }
                        }
                        req.setAttribute("listMark", listNew);
                    }
            req.setAttribute("findSubject",findSubject);
            req.setAttribute("colorNameSubject", colorNameSubject);
            getServletContext().getRequestDispatcher("/WEB-INF/view/CreateListOfMark.jsp").forward(req, resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        } finally {
            try {
                serviceMark.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            } finally {
                try {
                    serviceStudent.close();
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","closeDaoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                }
            }
        }
                }



    private void prepareToAddMark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String findSubject=req.getParameter("findSubject");
        req.setAttribute("findSubject",findSubject);
        boolean colorNameSubject=false;
        if(findSubject==null){
            findSubject = "";
            req.setAttribute("findSubject",findSubject);
        }else{
            if(findSubject.length()>50){
               colorNameSubject=true;
            }
        }
        SubjectService serviceSubject=null;
        int idStudent=parseInt(req.getParameter("idStudent"));
        req.setAttribute("idStudent",idStudent);
        try {
            serviceSubject=new SubjectService();
            Collection<Subject> listSubject=serviceSubject.readAllSubject();
            req.setAttribute("listSubject",listSubject);
            if(listSubject.size()!=0 && findSubject.length()!=0){
                Collection<Subject> listNew=new ArrayList<>();
                for (Subject subject : listSubject) {
                    if (subject.getName_subject().equalsIgnoreCase(findSubject)) {
                            listNew.add(subject);
                        }
                    System.out.println("listNew="+listNew.size());
                    }
                    req.setAttribute("listSubject",listNew);
                }
            req.setAttribute("findSubject",findSubject);
            req.setAttribute("colorNameSubject", colorNameSubject);
            getServletContext().getRequestDispatcher("/WEB-INF/view/PrepareToAddMark.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally{
            try {
                serviceSubject.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }



    private void addMarkFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mark mark ;
        MarkService serviceMark=null;
        int markStudent;
        StudentService serviceStudent=null;
        SubjectService serviceSubject=null;
        boolean colorMarkStudent=false;
        boolean messageEnter=false;

        int idStudent=parseInt(req.getParameter("idStudent"));
        int idSubject=parseInt(req.getParameter("idSubject"));
        req.setAttribute("idStudent",idStudent);
        req.setAttribute("idSubject",idSubject);
        try {
            serviceStudent = new StudentService(poolSql.getConnection());
            Student student = serviceStudent.findStudent(poolSql.getConnection(),idStudent);
            req.setAttribute("student",student);
            serviceSubject = new SubjectService();
            Subject subject = serviceSubject.findSubject(idSubject);
            req.setAttribute("subject",subject);
            if (req.getParameter("mark")!= null) {
                if (isMarkWrong(req.getParameter("mark"))) {
                     colorMarkStudent=true;
                } else {
                    markStudent = parseInt(req.getParameter("mark"));
                    req.setAttribute("markStudent",req.getParameter("mark"));
                    mark = new Mark();
                    mark.setStudentId(idStudent);
                    mark.setSecondNameOfStudent(student.getSecondName());
                    mark.setSubjectId(idSubject);
                    mark.setNameOfSubject(subject.getName_subject());
                    mark.setMark(markStudent);
                    serviceMark = new MarkService();
                    serviceMark.addMark(mark);
                    messageEnter=true;
                }
            }else{
                req.setAttribute("markStudent","");
            }
            req.setAttribute("messageEnter",messageEnter);
            req.setAttribute("colorMarkStudent",colorMarkStudent);
            getServletContext().getRequestDispatcher("/WEB-INF/view/AddMarkFinish.jsp").forward(req,resp);
        }catch (NumberFormatException e) {
             colorMarkStudent=true;
            req.setAttribute("colorMarkStudent",colorMarkStudent);
            getServletContext().getRequestDispatcher("/WEB-INF/view/AddMarkFinish.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                if(serviceMark!=null) {
                    serviceMark.close();
                }
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
            finally {
                try {
                    serviceStudent.close();
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","closeDaoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                }
                finally {
                    try {
                        serviceSubject.close();
                    } catch (DaoException e) {
                        req.setAttribute("errorMessage","closeDaoException");
                        getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                    }
                }
            }
        }
    }



    private void updateMarkFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent=parseInt(req.getParameter("idStudent"));
        int idMark=parseInt(req.getParameter("idMark"));
        req.setAttribute("idStudent",idStudent);
        req.setAttribute("idMark",idMark);

        StudentService serviceStudent=null;
        MarkService serviceMark=null;

        int  messageErrorMark =0;
        boolean messageEnter=false;

        try {
            serviceStudent=new StudentService(poolSql.getConnection());
            Student student=serviceStudent.findStudent(poolSql.getConnection(),idStudent);
            serviceMark=new MarkService();
            Mark mark=serviceMark.findMark(idMark);
            req.setAttribute("mark",mark);
            req.setAttribute("student",student);

if(req.getParameter("mark")!=null ) {
    if (req.getParameter("mark") == "") {
        messageErrorMark = 1;
        req.setAttribute("markStudent", "");
    } else {
        req.setAttribute("markStudent", req.getParameter("mark"));
        if (isMarkWrong(req.getParameter("mark"))) {
            messageErrorMark = 2;//"Неверный формат оценки";
        } else {
            Mark newMark = new Mark();
            newMark.setId(idMark);
            newMark.setStudentId(idStudent);
            newMark.setSecondNameOfStudent(mark.getSecondNameOfStudent());
            newMark.setSubjectId(mark.getSubjectId());
            newMark.setNameOfSubject(mark.getNameOfSubject());
            newMark.setMark(Integer.parseInt(req.getParameter("mark")));
            serviceMark.updateMark(newMark);
            messageEnter = true;//"Оценка для студента обновлена";
        }
    }
}
            req.setAttribute("messageEnter",messageEnter);
            req.setAttribute("messageErrorMark",messageErrorMark);
            getServletContext().getRequestDispatcher("/WEB-INF/view/UpdateMarkFinish.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                serviceMark.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
            finally {
                try {
                    serviceStudent.close();
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","closeDaoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                }
            }
        }
    }

    private void agreeToDeleteMark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent=parseInt(req.getParameter("idStudent"));
        int idMark=parseInt(req.getParameter("idMark"));
        req.setAttribute("idStudent",idStudent);
        req.setAttribute("messageAboutDelete",1);
        StudentService serviceStudent=null;
        MarkService serviceMark=null;
        try {
            serviceStudent=new StudentService(poolSql.getConnection());
            Student student=serviceStudent.findStudent(poolSql.getConnection(),idStudent);
            serviceMark=new MarkService();
            Mark mark=serviceMark.findMark(idMark);
            req.setAttribute("mark",mark);
            req.setAttribute("student",student);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteMark.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        }
        finally {
            try {
                serviceStudent.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
            finally {
                try {
                    serviceMark.close();
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","closeDaoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                }
            }
        }
    }
    private void deleteMarkFinish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MarkService serviceMark=null;
        int idMark = parseInt(req.getParameter("idMark"));
        int idStudent = parseInt(req.getParameter("idStudent"));
        req.setAttribute("idStudent",idStudent);
        req.setAttribute("messageAboutDelete",2);
        try {
            serviceMark = new MarkService();
            serviceMark.removeMark(idMark);
            getServletContext().getRequestDispatcher("/WEB-INF/view/DeleteMark.jsp").forward(req,resp);
        } catch (DaoException e) {
            req.setAttribute("errorMessage","daoException");
            getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
        } finally {
            try {
                serviceMark.close();
            } catch (DaoException e) {
                req.setAttribute("errorMessage","closeDaoException");
                getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
            }
        }
    }

    private void findStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count = 0;
        req.setAttribute("count", count);
        Collection<Student> list=new ArrayList<>();
        boolean colorSecondName = false;
        int enterMessage = 0;
        req.setAttribute("enterMessage", enterMessage);
        req.setAttribute("colorSecondName", colorSecondName);
        req.setAttribute("list", list);
        String secondName = req.getParameter("secondName");
        req.setAttribute("secondName", secondName);
        if (secondName == null||secondName == "") {
            req.setAttribute("secondName", "");
            count=1;
        }else{
            if (secondName.length() > 50) {
                colorSecondName = true;
                req.setAttribute("colorSecondName", colorSecondName);
                count = 1;
                req.setAttribute("count", count);
            }
        }
            if (count == 0) {
                StudentService serviceStudent = null;
                try {
                    serviceStudent=new StudentService(poolSql.getConnection());
                    Collection<Student> listStudent = serviceStudent.findStudentMore(poolSql.getConnection(),secondName);
                    req.setAttribute("list", listStudent);
                    if (listStudent.size() != 0) {
                        enterMessage = 1;
                    } else {
                        enterMessage = 2;
                    }
                    req.setAttribute("enterMessage", enterMessage);
                } catch (DaoException e) {
                    req.setAttribute("errorMessage","daoException");
                    getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                } finally {
                    try {
                        if (serviceStudent != null) {
                            serviceStudent.close();
                        }
                    } catch (DaoException e) {
                        req.setAttribute("errorMessage","closeDaoException");
                        getServletContext().getRequestDispatcher("/WEB-INF/view/PageForError.jsp").forward(req,resp);
                    }
                }
            }
        getServletContext().getRequestDispatcher("/WEB-INF/view/FindStudent.jsp").forward(req,resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        StudentService serviceStudent = null;
        String colorFirstName = "";
        String colorSecondName = "";
        String colorEnterYear = "";
        String colorBirthDay = "";

        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String enterYear = req.getParameter("enterYear");
        String birthDay = req.getParameter("birthDay");
        String errorMessage = "Проверьте правильность ввода ";
        String errorMessageFirstName = "";
        String errorMessageSecondName = "";
        String errorMessageBirthDay = "";
        String errorMessageEnterYear = "";
        String enterMessage = "";
        int count = 0;
        try {
            if (req.getParameter("firstName") == null) {
                firstName = "";
                count++;
            } else {
                if (isNameWrong(req.getParameter("firstName"))) {
                    colorFirstName = "class=\"colortext\"";
                    errorMessageFirstName = errorMessage;
                    count++;
                }
            }
            if (req.getParameter("secondName") == null) {
                secondName = "";
                count++;
            } else {
                if (isNameWrong(req.getParameter("secondName"))) {
                    colorSecondName = "class=\"colortext\"";
                    errorMessageSecondName = errorMessage;
                    count++;
                }
            }
            if (req.getParameter("birthDay") == null) {
                birthDay = "";
                count++;
            } else {
                if (isDateWrong(req.getParameter("birthDay"))) {
                    colorBirthDay = "class=\"colortext\"";
                    errorMessageBirthDay = errorMessage;
                    count++;
                }
            }
            if (req.getParameter("enterYear") == null) {
                enterYear = "";
                count++;
            } else {
                if (isNumberWrong(req.getParameter("enterYear"))) {
                    colorEnterYear = "class=\"colortext\"";
                    errorMessageEnterYear = errorMessage;
                    count++;
                }
            }
            session.setAttribute("colorFirstName", colorFirstName);
            session.setAttribute("colorSecondName", colorSecondName);
            session.setAttribute("colorEnterYear", colorEnterYear);
            session.setAttribute("colorBirthDay", colorBirthDay);
            session.setAttribute("firstName", firstName);
            session.setAttribute("secondName", secondName);
            session.setAttribute("enterYear", enterYear);
            session.setAttribute("birthDay", birthDay);
            session.setAttribute("errorMessageFirstName", errorMessageFirstName);
            session.setAttribute("errorMessageSecondName", errorMessageSecondName);
            session.setAttribute("errorMessageEnterYear", errorMessageEnterYear);
            session.setAttribute("errorMessageBirthDay", errorMessageBirthDay);
            session.setAttribute("count", errorMessageBirthDay);
            serviceStudent = new StudentService(poolSql.getConnection());
            if (req.getParameter("firstName") == null && req.getParameter("secondName") == null && req.getParameter("birthDay") == null && req.getParameter("enterYear") == null) {
                enterMessage = "Введите  данные студента";
                session.setAttribute("enterMessage", enterMessage);
            } else {
                if (count == 0) {
                    try {
                        Student student = new Student();
                        student.setFirstName(req.getParameter("firstName"));
                        student.setSecondName(req.getParameter("secondName"));
                        student.setBirthDay(Date.valueOf(req.getParameter("birthDay")));
                        student.setEnterYear(parseInt(req.getParameter("enterYear")));
                        serviceStudent = new StudentService(poolSql.getConnection());
                        Collection<Student> list = serviceStudent.findStudentMore(poolSql.getConnection(),req.getParameter("enterYear"));
                        if (list.size() != 0) {
                            boolean findStudent = false;
                            for (Student person : list) {
                                if (person.equals(student)) {
                                    findStudent = true;
                                }
                            }
                            if (findStudent) {
                                enterMessage = "Такой студент есть в списке";
                                session.setAttribute("enterMessage", enterMessage);

                                colorFirstName = "class=\"colortext\" ";
                                colorSecondName = "class=\"colortext\" ";
                                colorEnterYear = "class=\"colortext\" ";
                                colorBirthDay = "class=\"colortext\" ";
                            } else {
                                serviceStudent.add(poolSql.getConnection(),student);
                                enterMessage = "Студент добавлен";
                                session.setAttribute("enterMessage", enterMessage);
                            }
                        } else {
                            serviceStudent.add(poolSql.getConnection(),student);
                            enterMessage = "Студент добавлен";
                            session.setAttribute("enterMessage", enterMessage);
                        }
                        getServletContext().getRequestDispatcher("/WEB-INF/view/AddStudent.jsp").forward(req, resp);
                    } catch (DaoException daoException) {
                        getServletContext().getRequestDispatcher("/university/option?ACTION=AddStudent").forward(req, resp);
                    } finally {
                        try {
                            serviceStudent.close();
                        } catch (DaoException e) {
                            getServletContext().getRequestDispatcher("/university/option?ACTION=AddStudent").forward(req, resp);
                        }
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private void addSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String colorTextSubjectName = "";
        String colorTextTeacherName = "";
        String colorTextKafedraName = "";
        int count = 0;
        String errorMessage = "Поверьте правильность введенных данных";
        String startPage = "</head>" +
                "<title>Предметы</title>" +
                "<style>" +
                " .colortext {background-color: #ffe; } " +
                ".layer1 {padding: 5px; float: left; width: 200px;} " +
                ".layer2 {padding: 5px; width: 300px; float: left;}" +
                ".clear {clear: left; } </style>" +
                "</head>" +
                "<body>" +
                "<h1 align=\"center\" style=\"background-color: MediumSeaGreen; \">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>" +
                "<h2 align=\"center\" style=\"background-color: LightGray; \">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>" +
                "<div class=\"layer1\">" +
                "<a href=\"/university/option?ACTION=FindSubject\">FIND SUBJECT</a><br><br>" +
                "<a href=\"/university/option?ACTION=LIST OF SUBJECT\">LIST OF SUBJECT</a><br><br>" +
                "<a href=\"/university/option?ACTION=ON GENERAL\">LIST OF STUDENT</a><br><br>" +
                "</div>";
        String startLine = "<div class=\"layer2\">" +
                "<H1>Введите  данные предмета</H1>" +
                "<form action=\"/university/option\" method=\"POST\">";
        String rowOne = "NameSubject <input type=\"text\" %1s name=\"nameSubject\" value= %2s  ><br> ";
        String rowTwo = "NameTeacher <input type=\"text\" %1s name=\"nameTeacher\" value= %2s  ><br> ";
        String rowThree = "Kafedra <input type=\"text\" %1s name=\"kafedra\" value= %2s  ><br> " +
                "<br><br><input type=\"hidden\" name=\"ACTION\" value=\"AddSubject\">" +
                "<input type=\"submit\"  value=\"ADD\">" +
                "</form>" +
                "<a href=\"/university/option?ACTION=LIST OF SUBJECT\">RETURN</a><br><br>" +
                "</div>" +
                "<div class=\"clear\"></div>" +
                "</body>";
        SubjectService serviceSubject = null;
        String lineSubjectName = String.format(rowOne, colorTextSubjectName, "");
        String lineTeacherName = String.format(rowTwo, colorTextTeacherName, "");
        String lineKafedraName = String.format(rowThree, colorTextKafedraName, "");
        if (req.getParameter("nameSubject") == null && req.getParameter("nameTeacher") == null && req.getParameter("kafedra") == null) {
            out.print(startPage + startLine + lineSubjectName + lineTeacherName + lineKafedraName);
        } else {
            lineSubjectName =  String.format(rowOne, colorTextSubjectName, req.getParameter("nameSubject"));
            lineTeacherName =   String.format(rowTwo, colorTextTeacherName, req.getParameter("nameTeacher"));
            lineKafedraName =  String.format(rowThree, colorTextKafedraName, req.getParameter("kafedra"));
            if ( req.getParameter("nameSubject").length() ==0||req.getParameter("nameSubject").length() > 100) {
                colorTextSubjectName = "class=\"colortext\" ";
                lineSubjectName = errorMessage + String.format(rowOne, colorTextSubjectName, req.getParameter("nameSubject"));
                count++;
            }
            if (isNameWrong(req.getParameter("nameTeacher"))) {
                colorTextTeacherName = "class=\"colortext\" ";
                lineTeacherName = errorMessage + String.format(rowTwo, colorTextTeacherName, req.getParameter("nameTeacher"));
                count++;
            }
            if (isNameWrong(req.getParameter("kafedra"))) {
                colorTextKafedraName = "class=\"colortext\" ";
                lineKafedraName = errorMessage + String.format(rowThree, colorTextKafedraName, req.getParameter("kafedra"));
                count++;
            }
            if (count > 0) {
                out.print(startPage + startLine + lineSubjectName + lineTeacherName + lineKafedraName);
            } else {
                out.print(startPage);
                try {
                    Subject subjectNew = new Subject();
                    subjectNew.setName_subject(req.getParameter("nameSubject"));
                    subjectNew.setName_teacher(req.getParameter("nameTeacher"));
                    subjectNew.setKafedra(req.getParameter("kafedra"));
                    serviceSubject = new SubjectService();
                    Collection<Subject> list = serviceSubject.findSubjectName(req.getParameter("nameSubject"));
                    if (list.size() != 0) {
                        boolean findSubject = false;
                        for (Subject foundSubject : list) {
                            if (foundSubject.equals(subjectNew)) {
                                findSubject = true;
                            }
                        }
                        if (findSubject) {
                            colorTextSubjectName = "class=\"colortext\" ";
                            lineSubjectName = errorMessage + String.format(rowOne, colorTextSubjectName, req.getParameter("nameSubject"));
                            colorTextTeacherName = "class=\"colortext\" ";
                            lineTeacherName = errorMessage + String.format(rowTwo, colorTextTeacherName, req.getParameter("nameTeacher"));
                            colorTextKafedraName = "class=\"colortext\" ";
                            lineKafedraName = errorMessage + String.format(rowThree, colorTextKafedraName, req.getParameter("kafedra"));
                            out.print("<div class=\"layer2\">" +
                                    "<H2>Такой предмет есть в списке</H2>"+
                                    "<form action=\"/university/option\" method=\"POST\">");
                            out.print(lineSubjectName + lineTeacherName + lineKafedraName);
                        } else {
                            serviceSubject.addSubject(subjectNew);
                            out.print("<div class=\"layer2\">" +
                                    "<H2>Предмет добавлен</H2>" +
                                    "</div>" +
                                    "<div class=\"clear\"></div>" +
                                    "</body>");
                        }
                    } else {
                        serviceSubject.addSubject(subjectNew);
                        out.print("<div class=\"layer2\">" +
                                "<H2>Предмет добавлен</H2>" +
                                "</div>" +
                                "<div class=\"clear\"></div>" +
                                "</body>");
                    }
                } catch (DaoException daoException) {
                    getServletContext().getRequestDispatcher("/university/option?ACTION=LIST OF SUBJECT").forward(req,resp);
                } finally {
                    try {
                        serviceSubject.close();
                    } catch (DaoException e) {
                        getServletContext().getRequestDispatcher("/university/option?ACTION=LIST OF SUBJECT").forward(req,resp);
                    }
                }
            }
        }
    }

    private void findSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        SubjectService serviceSubject = null;
        String startPage = "</head>" +
                "<title>Предметы</title>" +
                "<style>" +
                " .colortext {background-color: #ffe; } " +
                ".layer1 {padding: 5px; float: left; width: 200px;} " +
                ".layer2 {padding: 5px; width: 300px; float: left;}" +
                ".clear {clear: left; } </style>" +
                "</head>" +
                "<body>" +
                "<h1 align=\"center\" style=\"background-color: MediumSeaGreen; \">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>" +
                "<h2 align=\"center\" style=\"background-color: LightGray; \">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>" +
                "<div class=\"layer1\">" +
                "<a href=\"/university/option?ACTION=AddSubject\">ADD SUBJECT</a><br><br>" +
                "<a href=\"/university/option?ACTION=LIST OF SUBJECT\">LIST OF SUBJECT</a><br><br>" +
                "<a href=\"/university/option?ACTION=ON GENERAL\">LIST OF STUDENT</a><br><br>" +
                "</div>";
        String startLine = "<div class=\"layer2\">" +
                "<br><form action=\"/university/option\" method=\"POST\" >";
        String message1 = "<H1>Введите название предмета</H1>";
        String rowOne = "Name Subject <input type=\"text\" %1s name=\"nameSubject\" value= %2s >" +
                "<br><br><input type=\"hidden\" name=\"ACTION\" value=\"FindSubject\">" +
                "<input type=\"submit\"  value=\"FIND\">" +
                "</form>" +
                "</div>" +
                "<div class=\"clear\"></div>" +
                "</body>";
        String errorMessage1 = "<H1>Проверьте правильность введенного предмета</H1>";
        if (req.getParameter("nameSubject")== null) {
            out.print(startPage + startLine + message1 + String.format(rowOne, "", ""));
        } else {
            if (req.getParameter("nameSubject").length() > 50) {
                out.print(startPage + startLine + errorMessage1 + String.format(rowOne, " class=\"colortext\"", req.getParameter("nameSubject")));
            } else {
                try {
                    serviceSubject = new SubjectService();
                    Collection<Subject> list = serviceSubject.findSubjectName(req.getParameter("nameSubject"));
                    if (list.size() != 0) {
                        out.print(startPage + startLine + "<H1>Список найденных предметов</H1><br>");
                        out.print("<table cellspacing=5 cellpadding=7 border=1>");
                        out.print("<tr><th>Name Subject</th> <th>Name Teacher</th> <th>Kafedra</th> <th>UPDATE</th> <th>DELETE</th> </tr>");
                        for (Subject foundSubject : list) {
                            out.print("<tr><td>" + foundSubject.getName_subject() + "</td>" +
                                    "<td>" + foundSubject.getName_teacher() + "</td>" +
                                    "<td>" + foundSubject.getKafedra() + "</td>" +
                                    "<td><a href=\"/university/option?id=" + foundSubject.getId() + "&ACTION=UpdateSubjectFinish\">UPDATE</a></td>" +
                                    "<td><a href=\"/university/option?id=" + foundSubject.getId() + "&ACTION=AgreeToDeleteSubject\">DELETE</a></td><tr>");
                        }
                        out.print("</table>" +
                                "<a href=\"/university/option?ACTION=FindSubject\">RETURN</a><br><br>" +
                                "</div>" +
                                "<div class=\"clear\"></div>" +
                                "</body>");
                    } else {
                        out.print(startPage + startLine + "<H1>Такой предмет не найден</H1>" + String.format(rowOne, " class=\"colortext\"", req.getParameter("nameSubject")));
                    }
                } catch (DaoException e) {
                    getServletContext().getRequestDispatcher("/university/option?ACTION=FindSubject").forward(req,resp);
                } finally {
                    try {
                        serviceSubject.close();
                    } catch (DaoException e) {
                        getServletContext().getRequestDispatcher("/university/option?ACTION=FindSubject").forward(req,resp);
                    }
                }
            }
        }
    }
    private boolean isPageWrong(int page,String number){
        boolean answer=false;
        try {
            page = parseInt(number);
        } catch (NumberFormatException e) {
            answer = true;
        }
        return answer;
    }
    private boolean isNameWrong(String name){
        boolean answer=false;
        if (name.length() == 0 || name.length() > 50) {
            answer=true;
        }
        return answer;
    }
    private boolean isNumberWrong(String number){
        boolean answer=false;
        try {
            int enterYear = parseInt(number);
            if (enterYear < 0 || enterYear > 5) {
                answer = true;
            }
        } catch (NumberFormatException e) {
            answer = true;
        }
        return answer;
    }
    private boolean isDateWrong(String birthday) {
        boolean answer = false;
        try {
            Date.valueOf(birthday);
        } catch (IllegalArgumentException e) {
            answer = true;
        }
        return answer;
    }

    private boolean isMarkWrong(String mark) {
        boolean answer = false;
        try {
            int markStudent = parseInt(mark);
            if (markStudent < 0 || markStudent > 10) {
                answer = true;
            }
        } catch (NumberFormatException e) {
            answer = true;
        }
        return answer;
    }
}
