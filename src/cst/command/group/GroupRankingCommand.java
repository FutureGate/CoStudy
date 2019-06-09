package cst.command.group;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.BoardDAO;
import cst.dao.ChatDAO;
import cst.dao.GroupDAO;
import cst.dto.GroupDTO;
import cst.dto.UserDTO;

public class GroupRankingCommand implements CstCommand {

    public GroupRankingCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		GroupDAO dao = new GroupDAO();
		
		ArrayList<GroupDTO> groupList = dao.getGroupListByRank();
		
		req.setAttribute("groupList", groupList);
		
		return 1;
	}

}
