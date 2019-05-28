package cst.command.user;

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
import cst.dto.UserDTO;

public class UserPasswordSettingCommand implements CstCommand {

    public UserPasswordSettingCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String originPassword = req.getParameter("originPassword");
		String userPassword = req.getParameter("userPassword");
		String userPassword2 = req.getParameter("userPassword2");
		
		if(!userPassword.equals(userPassword2)) {
			return -1;
		}
		
		UserDAO dao = new UserDAO();
		
		return dao.modifyUserPassword(userID, originPassword, userPassword);
	}
}
