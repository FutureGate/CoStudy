package cst.command.group;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.ChatDAO;
import cst.dao.GroupDAO;
import cst.dto.GroupDTO;

public class GroupViewCommand implements CstCommand {

    public GroupViewCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		
		GroupDAO dao = new GroupDAO();
		
		GroupDTO group = dao.getGroup(groupName);
		
		if(group != null) {
			req.setAttribute("group", group);
		}
		
		return 1;
	}

}
