package cst.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.command.auth.UserLoginCommand;
import cst.command.auth.UserRegisterCommand;
import cst.dao.UserDAO;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {

    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		actionDo(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		actionDo(req, res);
	}

	private void actionDo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 페이지 인코딩 UTF-8로 정의
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		CstCommand cmd = null;
		
		String uri = req.getRequestURI();
		String path = req.getContextPath();
		
		String command = uri.substring(path.length());
		
		if(command.equals("/loginAction.do")) {
			cmd = new UserLoginCommand();
			cmd.execute(req, res);
			viewPage = "dashboard.jsp";
			
		} else if(command.equals("/registerAction.do")) {
			cmd = new UserRegisterCommand();
			cmd.execute(req, res);
			viewPage="index.jsp";
		}
		
		// 페이지 포워딩
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}
}
