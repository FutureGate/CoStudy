package cst.command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dto.BoardDTO;

public class BoardListCommand implements CstCommand {

    public BoardListCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userID = req.getParameter("userID");
		String bbsType = req.getParameter("bbsType");
		String boardTitle = req.getParameter("boardTitle");
		int pageNumber = 1;
		
		
		
		if(req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("boardContent"));
		}
		
		BoardDAO dao = new BoardDAO();
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		req.setAttribute("boardList", list);
		req.setAttribute("boardNext", dao.nextPage(pageNumber));
		
		return 1;

	}

}
