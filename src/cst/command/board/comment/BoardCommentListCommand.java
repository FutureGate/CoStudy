package cst.command.board.comment;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.ChatDAO;
import cst.dto.BoardDTO;
import cst.dto.ChatDTO;
import cst.dto.CommentDTO;

public class BoardCommentListCommand implements CstCommand {

    public BoardCommentListCommand() {
        super();
    }
    
    @Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String boardID = req.getParameter("boardID");
		String bbsType = req.getParameter("bbsType");
		String lastID = req.getParameter("lastID");
		
		if(boardID == null || boardID.equals("") || bbsType == null || bbsType.equals("") || lastID == null || lastID.equals("")) {
			res.getWriter().write("");
			return 0;
		} else {
			try {
				boardID = URLDecoder.decode(boardID, "UTF-8");
				bbsType = URLDecoder.decode(bbsType, "UTF-8");
				
				res.getWriter().write(getCommentList(boardID, bbsType, Integer.parseInt(lastID)));
			} catch (Exception e) {
				res.getWriter().write("");
			}
			return 1;
		}
    }
    
    public String getCommentList(String boardID, String bbsType, int lastID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		BoardDAO dao = new BoardDAO(bbsType);
		ArrayList<CommentDTO> commentList = dao.getCommentListByID(boardID, lastID);
		
		if(commentList.size() == 0) return "";
		
		for(int i=0; i<commentList.size(); i++) {
			result.append("[{\"value\": \"" + commentList.get(i).getCommentID() + "\"},");
			result.append("{\"value\": \"" + commentList.get(i).getUserID() + "\"},");
			result.append("{\"value\": \"" + commentList.get(i).getUserNick() + "\"},");
			result.append("{\"value\": \"" + commentList.get(i).getCommentContent() + "\"},");
			result.append("{\"value\": \"" + commentList.get(i).getCommentDate() + "\"}]");
			
			if(i != commentList.size() -1) result.append(",");
		}
		
		result.append("], \"last\":\"" + Integer.toString(commentList.get(commentList.size()-1).getCommentID()) + "\"}");
		
		return result.toString();
	}
}
