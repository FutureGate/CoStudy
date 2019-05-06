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
import cst.command.board.BoardDeleteCommand;
import cst.command.board.BoardEditCommand;
import cst.command.board.BoardListCommand;
import cst.command.board.BoardViewCommand;
import cst.command.board.BoardWriteCommand;
import cst.command.chat.ChatListCommand;
import cst.command.chat.ChatSendCommand;

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
		// Set Encoding
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
		String viewPage = null;
		CstCommand cmd = null;
		int result = 0;
		boolean isFowarding = false;
		
		String uri = req.getRequestURI();
		String path = req.getContextPath();
		
		String command = uri.substring(path.length());
		
		/* =====================================
		 *  Command
		 * 
		 * 	Route
		 * 
		 =======================================*/
		
		
		// ====================================
		// User Route
		// ====================================
		
		// User Login
		if(command.equals("/userLoginAction.do")) {
			cmd = new UserLoginCommand();
			result = cmd.execute(req, res);
			
			if(result == 1) {
				viewPage = "/CoStudy/dashboard.do";
				isFowarding = false;
			} else {
				viewPage = "/CoStudy/login.jsp";
				isFowarding = false;
			}
			
		// User Register
		} else if(command.equals("/userRegisterAction.do")) {
			cmd = new UserRegisterCommand();
			cmd.execute(req, res);

			viewPage = "/CoStudy/index.jsp";
			isFowarding = false;
		
			
		// User Logout
		} else if(command.equals("/user/logout.do")) {
			req.getSession().invalidate();
			
			viewPage = "/CoStudy/index.jsp";
			isFowarding = false;	
		}
		
		
		
		
		// ====================================
		// Dashboard Route
		// ====================================
			
		// Dashboard Action
		if(command.equals("/dashboard.do")) {
			
			viewPage = "/dashboard.jsp";
			isFowarding = true;
		
		}
		
		
		
		
		// ====================================
		// Group Route
		// ====================================
		
		// View All Group
		if(command.equals("/group/viewAll")) {
			
		
		}
			
		
		
		
		
		
		// ====================================
		// Board Route
		// ====================================	
			
		// Get Board List Action
		if(command.equals("/bbs/list.do")) {
			cmd = new BoardListCommand();
			cmd.execute(req, res);
			
			viewPage = "/boardList.jsp";
			isFowarding = true;
		
		// View article
		} else if(command.equals("/bbs/view.do")) {
			String bbsType = req.getParameter("bbs");

			cmd = new BoardViewCommand();
			result = cmd.execute(req, res);

			// if article is deleted
			if(result < 0) {
				// go to list page
				viewPage = "/CoStudy/bbs/list.do?bbs=" + bbsType;
				isFowarding = false;
			} else {
				viewPage = "/boardView.jsp";
				isFowarding = true;
			}

		// Write article page
		} else if(command.equals("/bbs/write.do")) {
			viewPage = "/boardWrite.jsp";
			isFowarding = true;
		
		// Edit article page
		} else if(command.equals("/bbs/edit.do")) {
			cmd = new BoardViewCommand();
			cmd.execute(req, res);
			
			viewPage = "/boardEdit.jsp";
			isFowarding = true;
			
		// Edit article page
		} else if(command.equals("/bbs/editAction.do")) {
			String bbsType = req.getParameter("bbsType");
			
			cmd = new BoardEditCommand();
			cmd.execute(req, res);
			
			viewPage = "/CoStudy/bbs/list.do?bbs=" + bbsType;
			isFowarding = false;
			
		// Edit article page
		} else if(command.equals("/bbs/deleteAction.do")) {
			String bbsType = req.getParameter("bbs");
			
			cmd = new BoardDeleteCommand();
			cmd.execute(req, res);
			
			viewPage = "/CoStudy/bbs/list.do?bbs=" + bbsType;
			isFowarding = false;
			
		// Write article action
		} else if(command.equals("/bbs/writeAction.do")) {
			String bbsType = req.getParameter("bbsType");
			
			cmd = new BoardWriteCommand();
			cmd.execute(req, res);
			
			viewPage = "/CoStudy/bbs/list.do?bbs=" + bbsType;
			isFowarding = false;
		}
			
			
		
		
			
		// ====================================
		// User Settings Route
		// ====================================	
			
		// View profile page
		if(command.equals("/profile/viewProfile.do")) {
			viewPage = "/viewProfile.jsp";
			isFowarding = true;
			
			
		// User Setting page
		} else if(command.equals("/user/setting.do")) {
			viewPage = "/setting.jsp";
			isFowarding = true;
		}
		
		
		
	
		
		
		
		// ====================================
		// Chat Route
		// ====================================
		
		// Chat list page
		if(command.equals("/chatList.do")) {
			
			
		// Chatting page
		} else if(command.equals("/chat.do")) {
			viewPage = "/chatView.jsp";
			isFowarding = true;
			
		// Chat Send Action (Ajax)
		} else if(command.equals("/chatSendAction.do")) {
			
			cmd = new ChatSendCommand();
			cmd.execute(req, res);
		
		// Get Chat List Action (Ajax)
		}  else if(command.equals("/chatListAction.do")) {
			
			cmd = new ChatListCommand();
			cmd.execute(req, res);
		}
		
		
		
		// ====================================
		// Fowarding & Redirection
		// ====================================

		if(viewPage != null) {
			// check isFowarding
			if(isFowarding) {
				// do Fowarding
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
			} else {
				res.sendRedirect(viewPage);
			}
		}
	}
}
