package cst.command.group.registration;

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

public class GroupRegisterCommand implements CstCommand {

    public GroupRegisterCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		String userID = req.getParameter("userID");
		
		groupName = URLDecoder.decode(groupName, "UTF-8");
		
		GroupDAO dao = new GroupDAO();
		
		GroupDTO group = dao.getGroup(groupName);

		if(group != null) {
			if(group.getGroupPop() < 50) {
				dao.registerUser(groupName, userID);
			} else {
				// Group Pop Error
			}
		}
		
		return 1;
	}

}
