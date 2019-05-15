package cst.command.group.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;

public class GroupBoardEditCommand implements CstCommand {

    public GroupBoardEditCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String boardID = req.getParameter("boardID");
		String userID = req.getParameter("userID");
		String userNick = req.getParameter("userNick");
		String bbsType = req.getParameter("bbsType");
		String boardTitle = req.getParameter("boardTitle");
		String boardContent = req.getParameter("boardContent");
		
		BoardDAO dao = new BoardDAO(bbsType);
		
		return dao.modify(boardID, userNick, boardTitle, boardContent);

		
	}

}
