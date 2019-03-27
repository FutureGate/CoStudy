package cst.command.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.UserDAO;

public class UserLoginCommand implements CstCommand {

    public UserLoginCommand() {
        super();
    }

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		
		UserDAO dao = new UserDAO();
		dao.login(userID, userPassword);
	}

}
