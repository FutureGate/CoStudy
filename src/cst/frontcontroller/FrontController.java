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
import cst.command.chat.ChatListCommand;
import cst.command.chat.ChatSendCommand;
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
		res.setContentType("text/html;charset=UTF-8");
		
		String viewPage = null;
		CstCommand cmd = null;
		int result = 0;
		boolean isFowarding = false;
		
		String uri = req.getRequestURI();
		String path = req.getContextPath();
		
		String command = uri.substring(path.length());
		
		// 유저 로그인 (Ajax)
		if(command.equals("/userLoginAction.do")) {
			cmd = new UserLoginCommand();
			result = cmd.execute(req, res);
			
			if(result == 1) {
				viewPage = "dashboard.jsp";
				isFowarding = false;
			} else {
				viewPage = "login.jsp";
				isFowarding = false;
			}
			
		// 유저 회원가입 (Ajax)
		} else if(command.equals("/userRegisterAction.do")) {
			cmd = new UserRegisterCommand();
			cmd.execute(req, res);

			viewPage = "index.jsp";
			isFowarding = false;
		
		// 대시보드 
		} else if(command.equals("/dashboard.do")) {
			
			viewPage = "dashboard.jsp";
			isFowarding = true;
			
		// 메시지 전송 (Ajax)
		} else if(command.equals("/chatSendAction.do")) {
			
			cmd = new ChatSendCommand();
			cmd.execute(req, res);
		
		// 메시지 목록 읽기 (Ajax)
		}  else if(command.equals("/chatListAction.do")) {
			
			cmd = new ChatListCommand();
			cmd.execute(req, res);
		}
		
		
		if(viewPage != null) {
			// 포워딩 여부 판단
			if(isFowarding) {
				// 페이지 포워딩
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
			} else {
				res.sendRedirect(viewPage);
			}
		}
	}
}
