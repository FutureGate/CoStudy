package cst.command.group.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.GroupDAO;
import cst.dto.BoardDTO;
import cst.dto.CommentDTO;

public class GroupBoardViewCommand implements CstCommand {

    public GroupBoardViewCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		String boardID = req.getParameter("bbsID");

		GroupDAO dao = new GroupDAO();
		
		BoardDTO board = dao.getBoardByID(groupName, boardID);
		
		// board not found
		if(board  == null) {
			return -2;
		}	
		// article had deleted ;
//		} else if(board.getBoardDelete() == 1) {
//			return -1;
//		}
		
		req.setAttribute("board", board);
		
		return 1;


	}

}
