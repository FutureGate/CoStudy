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
import cst.command.board.BoardListCommand;
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
		
		/*
		 *  주의 사항
		 * 
		 * 	 포워딩이 아닐 시에는 viewPage를 절대경로로 지정해주어야 한다.
		 * 
		 */
		
		// 유저 로그인
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
			
		// 유저 회원가입
		} else if(command.equals("/userRegisterAction.do")) {
			cmd = new UserRegisterCommand();
			cmd.execute(req, res);

			viewPage = "/CoStudy/index.jsp";
			isFowarding = false;
		
			
		// 로그아웃
		} else if(command.equals("/user/logout.do")) {
			req.getSession().invalidate();
			
			viewPage = "/CoStudy/index.jsp";
			isFowarding = false;	
		
		// 대시보드 
		} else if(command.equals("/dashboard.do")) {
			
			viewPage = "/dashboard.jsp";
			isFowarding = true;
		
			
		// 스터디 그룹 전체 보기
		} else if(command.equals("/group/viewAll")) {
			
		
			
		// 게시판 목록 보기
		} else if(command.equals("/bbs/list.do")) {
			cmd = new BoardListCommand();
			cmd.execute(req, res);
			
			viewPage = "/boardList.jsp";
			isFowarding = true;
			
		// 게시물 작성 페이지
		} else if(command.equals("/bbs/write.do")) {
			viewPage = "/boardWrite.jsp";
			isFowarding = true;
		
		// 게시물 작성
		} else if(command.equals("/bbs/writeAction.do")) {
			String bbsType = req.getParameter("bbsType");
			
			cmd = new BoardWriteCommand();
			cmd.execute(req, res);
			
			viewPage = "/CoStudy/bbs/list.do?bbs=" + bbsType;
			isFowarding = false;
			
		// 유저 프로필 보기
		} else if(command.equals("/profile/viewProfile.do")) {
			viewPage = "/viewProfile.jsp";
			isFowarding = true;
			
			
		// 유저 계정 설정
		} else if(command.equals("/user/setting.do")) {
			viewPage = "/setting.jsp";
			isFowarding = true;
		
		// 주고받은 메시지 목록 페이지
		} else if(command.equals("/chatList.do")) {
			
			
		// 주고받은 메시지 목록 페이지
		} else if(command.equals("/chat.do")) {
			viewPage = "/chatView.jsp";
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
