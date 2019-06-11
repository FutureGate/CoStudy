package cst.command.group.board;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.GroupDAO;

public class GroupBoardEditCommand implements CstCommand {

    public GroupBoardEditCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String boardID = req.getParameter("boardID");
		String userID = req.getParameter("userID");
		String userNick = req.getParameter("userNick");
		String groupName = req.getParameter("groupname");
		String boardTitle = req.getParameter("boardTitle");
		String boardContent = req.getParameter("boardContent");
		
		groupName = URLDecoder.decode(groupName, "UTF-8");
		
		GroupDAO dao = new GroupDAO();
		
		return dao.modify(groupName, boardID, userNick, boardTitle, boardContent);

		
	}

}
