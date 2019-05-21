package cst.command.group.board.comment;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.GroupDAO;

public class GroupBoardCommentDeleteCommand implements CstCommand {

    public GroupBoardCommentDeleteCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String commentID = req.getParameter("commentID");
		String boardID = req.getParameter("boardID");
		String groupName = req.getParameter("groupname");
		
		if(commentID == null || commentID.equals("") || boardID == null || boardID.equals("") || groupName == null || groupName.equals("")) {
			res.getWriter().write("0");
			return 0;
		} else {
			commentID = URLDecoder.decode(commentID, "UTF-8");
			boardID = URLDecoder.decode(boardID, "UTF-8");
			groupName = URLDecoder.decode(groupName, "UTF-8");
			
			res.getWriter().write(new GroupDAO().deleteComment(groupName, boardID, Integer.parseInt(commentID)));
			return 1;
		}
	}

}
