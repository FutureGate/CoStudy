package cst.command.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dto.BoardDTO;

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
		
		// 존재하지 않는 게시물일 경우
		if(board  == null) {
			return -2;
			
		// 게시물이 삭제되었을 경우
		} else if(board.getBoardDelete() == 1) {
			return -1;
		}
		
		req.setAttribute("board", board);
		
		return 1;

	}

}
