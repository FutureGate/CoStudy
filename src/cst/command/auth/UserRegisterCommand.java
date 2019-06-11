package cst.command.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.UserDAO;

public class UserRegisterCommand implements CstCommand {

    public UserRegisterCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		String userNick = req.getParameter("userNick");
		String userEmail = req.getParameter("userEmail");
		String userBorn = req.getParameter("userBorn");
		String userGender = req.getParameter("userGender");
		
		UserDAO dao = new UserDAO();

		return dao.register(userID, userPassword, userNick, userEmail, userBorn, userGender);
	}

}
