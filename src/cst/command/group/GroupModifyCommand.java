package cst.command.group;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.ChatDAO;
import cst.dao.GroupDAO;
import cst.dao.UserDAO;
import cst.dto.GroupDTO;
import cst.dto.UserDTO;

public class GroupModifyCommand implements CstCommand {

    public GroupModifyCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		String studyStart = req.getParameter("studyStart");
		String studyFinish = req.getParameter("studyFinish");
		String studyLocation = req.getParameter("studyLocation");

		GroupDAO dao = new GroupDAO();
		
		if(dao.modifyGroup(groupName, studyStart, studyFinish, studyLocation) == 1) {
			GroupDTO group = dao.getGroup(groupName);
			
			req.setAttribute("group", group);
			
			return 1;
		} else {
			return -1;
		}
	}

}
