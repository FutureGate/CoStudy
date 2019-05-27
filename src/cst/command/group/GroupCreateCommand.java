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

public class GroupCreateCommand implements CstCommand {

    public GroupCreateCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupName");
		String groupMaster = req.getParameter("groupMaster");
		String studyStart = req.getParameter("studyStart");
		String studyFinish = req.getParameter("studyFinish");
		String studyLocation = req.getParameter("studyLocation");
		
		GroupDAO dao = new GroupDAO();
		
		return dao.createGroup(groupName, groupMaster, studyStart, studyFinish, studyLocation);
	}

}
