package cst.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.UserDAO;

public class UserRegisterCommand implements CstCommand {

    public UserRegisterCommand() {
        super();
    }

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		String userNick = req.getParameter("userNick");
		String userEmail = req.getParameter("userEmail");
		String userBorn = req.getParameter("userYear") + "-" + req.getParameter("userMonth") + "-" + req.getParameter("userDay");
		String userGender = req.getParameter("userGender");
		
		UserDAO dao = new UserDAO();
		dao.register(userID, userPassword, userNick, userEmail, userBorn, userGender);
	}

}
