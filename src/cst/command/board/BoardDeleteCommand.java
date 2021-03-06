package cst.command.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;

public class BoardDeleteCommand implements CstCommand {

    public BoardDeleteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String boardID = req.getParameter("bbsID");
		String bbsType = req.getParameter("bbs");
		
		BoardDAO dao = new BoardDAO(bbsType);
		
		return dao.delete(boardID);

		
	}

}
