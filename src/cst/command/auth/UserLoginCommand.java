package cst.command.auth;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.ChatDAO;
import cst.dao.UserDAO;

public class UserLoginCommand implements CstCommand {

    public UserLoginCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		
		UserDAO dao = new UserDAO();
		
		return dao.login(userID, userPassword, req);

	}

}
