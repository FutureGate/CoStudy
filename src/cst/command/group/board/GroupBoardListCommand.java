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

public class GroupBoardListCommand implements CstCommand {

    public GroupBoardListCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupName = req.getParameter("groupname");
		int pageNumber = 1;
		
		if(req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}

		GroupDAO dao = new GroupDAO();
		
		ArrayList<BoardDTO> list = dao.getBoardListByPage(groupName, pageNumber);
		
		//boolean isNext = dao.getIsNext(pageNumber);
		
		//req.setAttribute("isNext", isNext);
		req.setAttribute("boardList", list);
		
		return 1;

	}

}
