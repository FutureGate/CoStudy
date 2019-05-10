package cst.command.board.comment;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.ChatDAO;

public class CommentWriteCommand implements CstCommand {

    public CommentWriteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String userNick = req.getParameter("userNick");
		String commentContent = req.getParameter("commentContent");
		String boardID = req.getParameter("boardID");
		String bbsType = req.getParameter("bbsType");
		
		if(userID == null || userID.equals("") || userNick == null || userNick.equals("") || commentContent == null || commentContent.equals("")) {
			res.getWriter().write("0");
			return 0;
		} else {
			userID = URLDecoder.decode(userID, "UTF-8");
			userNick = URLDecoder.decode(userNick, "UTF-8");
			commentContent = URLDecoder.decode(commentContent, "UTF-8");
			boardID = URLDecoder.decode(boardID, "UTF-8");
			bbsType = URLDecoder.decode(bbsType, "UTF-8");
			
			res.getWriter().write(new BoardDAO(bbsType).writeComment(boardID, userID, userNick, commentContent));
			return 1;
		}
	}

}
