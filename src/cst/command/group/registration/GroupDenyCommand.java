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

public class GroupDenyCommand implements CstCommand {

    public GroupDenyCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		String userID = req.getParameter("userID");
		
		GroupDAO dao = new GroupDAO();
		
		int result =  dao.denyUser(groupName, userID);

		return result;
	}

}
