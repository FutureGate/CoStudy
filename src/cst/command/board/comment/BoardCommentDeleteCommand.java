package cst.command.board.comment;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;

public class BoardCommentDeleteCommand implements CstCommand {

    public BoardCommentDeleteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String commentID = req.getParameter("commentID");
		String boardID = req.getParameter("boardID");
		String bbsType = req.getParameter("bbsType");
		
		if(commentID == null || commentID.equals("") || boardID == null || boardID.equals("") || bbsType == null || bbsType.equals("")) {
			res.getWriter().write("0");
			return 0;
		} else {
			commentID = URLDecoder.decode(commentID, "UTF-8");
			boardID = URLDecoder.decode(boardID, "UTF-8");
			bbsType = URLDecoder.decode(bbsType, "UTF-8");
			
			res.getWriter().write(new BoardDAO(bbsType).deleteComment(boardID, Integer.parseInt(commentID)));
			return 1;
		}
	}

}
