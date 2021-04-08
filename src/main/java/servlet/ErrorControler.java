package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/errorPage")
public class ErrorControler extends HttpServlet {

    final String EXC = "javax.servlet.error.exception";
    final String MSG = "javax.servlet.error.message";
    final String ST = "javax.servlet.error.status_code";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processWithError(req,resp);
        getServletContext().getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processWithError(req,resp);
        getServletContext().getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req,resp);
    }
    public void processWithError(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Throwable throwable=(Throwable)req.getAttribute(EXC);
        Integer status_code = (Integer)req.getAttribute(ST);

        String messageError="Error";req.setAttribute("messageError",messageError);
        switch (status_code){
            case 400:
                messageError="No correct request";
                req.setAttribute("messageError",messageError);
                break;
            case 404:
                messageError="Not found this resourse";
                req.setAttribute("messageError",messageError);
                break;
            case 500:
                messageError="Server has some error";
                req.setAttribute("messageError",messageError);
                break;
        }

    }
}
