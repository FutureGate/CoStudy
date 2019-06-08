package cst.command.group.registration;

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
import cst.dto.GroupDTO;

public class GroupAcceptPageCommand implements CstCommand {

    public GroupAcceptPageCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		
		GroupDAO dao = new GroupDAO();
		
		ArrayList<String> registerWaiting = dao.getRegisterWaiting(groupName);

		req.setAttribute("registerWaiting", registerWaiting);
		
		return 1;
	}

}
