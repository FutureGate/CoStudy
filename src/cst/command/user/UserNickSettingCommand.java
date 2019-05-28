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

public class UserNickSettingCommand implements CstCommand {

    public UserNickSettingCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String userNick = req.getParameter("userNick");
		
		UserDAO dao = new UserDAO();
		
		if(dao.modifyUserNick(userID, userNick) == 1) {
			UserDTO user = (UserDTO) req.getSession().getAttribute("user");
			
			user.setUserNick(userNick);
			
			req.getSession().setAttribute("user", user);
			
			return 1;
		} else {
			return -1;
		}
	}

}
