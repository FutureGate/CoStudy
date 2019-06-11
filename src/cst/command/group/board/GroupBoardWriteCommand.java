package cst.command.group.board;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.GroupDAO;

public class GroupBoardWriteCommand implements CstCommand {

    public GroupBoardWriteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		
		String userID = req.getParameter("userID");
		String userNick = req.getParameter("userNick");
		String boardTitle = req.getParameter("boardTitle");
		String boardContent = req.getParameter("boardContent");
		
		GroupDAO dao = new GroupDAO();
		
		groupName = URLDecoder.decode(groupName, "UTF-8");
		
		return dao.write(groupName, userID, userNick, boardTitle, boardContent);

	}

}
