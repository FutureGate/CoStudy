package cst.command.dashboard;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.ChatDAO;
import cst.dao.GroupDAO;
import cst.dao.UserDAO;
import cst.dto.GroupDTO;
import cst.dto.UserDTO;

public class DashboardCommand implements CstCommand {

    public DashboardCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		UserDTO user = (UserDTO) req.getSession().getAttribute("user");
		
		UserDAO dao = new UserDAO();
		
		ArrayList<String> registered = dao.getRegistered(user.getUserID());
		ArrayList<String> registerWaiting = dao.getRegisterWaiting(user.getUserID());
		
		req.setAttribute("registered", registered);
		req.setAttribute("registerWaiting", registerWaiting);
		
		return 1;
	}

}
