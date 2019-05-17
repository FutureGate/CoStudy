package cst.command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dto.BoardDTO;
import cst.dto.CommentDTO;

public class BoardViewCommand implements CstCommand {

    public BoardViewCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String bbsType = req.getParameter("bbs");
		String boardID = req.getParameter("bbsID");

		BoardDAO dao = new BoardDAO(bbsType);
		
		BoardDTO board = dao.getBoardByID(boardID);
		
		// board not found
		if(board  == null) {
			return -2;
			
		// article had deleted ;
		} else if(board.getBoardDelete() == 1) {
			return -1;
		}
		
		req.setAttribute("board", board);
		
		return 1;

	}

}
