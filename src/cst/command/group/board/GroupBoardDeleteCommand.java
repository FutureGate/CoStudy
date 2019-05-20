package cst.command.group.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.GroupDAO;

public class GroupBoardDeleteCommand implements CstCommand {

    public GroupBoardDeleteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String boardID = req.getParameter("bbsID");
		String groupName = req.getParameter("groupname");
		
		GroupDAO dao = new GroupDAO();
		
		return dao.delete(groupName, boardID);

		
	}

}
