package cst.command.group.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dto.BoardDTO;
import cst.dto.CommentDTO;

public class GroupBoardViewCommand implements CstCommand {

    public GroupBoardViewCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String bbsType = req.getParameter("bbs");
		String boardID = req.getParameter("bbsID");

		BoardDAO dao = new BoardDAO(bbsType);
		
		BoardDTO board = dao.getBoardByID(boardID);
		
		// ì¡´ì¬?•˜ì§? ?•Š?Š” ê²Œì‹œë¬¼ì¼ ê²½ìš°
		if(board  == null) {
			return -2;
			
		// ê²Œì‹œë¬¼ì´ ?‚­? œ?˜?—ˆ?„ ê²½ìš°
		} else if(board.getBoardDelete() == 1) {
			return -1;
		}
		
		req.setAttribute("board", board);
		
		return 1;

	}

}
