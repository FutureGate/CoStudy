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

public class CommentViewCommand implements CstCommand {

    public CommentViewCommand() {
        super();
    }
    
    @Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String boardID = req.getParameter("boardID");
		String bbsType = req.getParameter("bbs");
		
		try {
				res.getWriter().write(getCommentList(boardID, bbsType));
		} catch (Exception e) {
			res.getWriter().write("");
		}
		
		return 1;
	}
	
	public String getCommentList(String boardID, String bbsType) {
		BoardDAO dao = new BoardDAO(bbsType);
		ArrayList<CommentDTO> commentList = dao.getCommentList(boardID);
		
		if(commentList.size() == 0)
			return "";
		
		for(int i=0; i<commentList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			
			if(i != chatList.size() -1) result.append(",");
		}
		
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
		
		return result.toString();
	}

}
