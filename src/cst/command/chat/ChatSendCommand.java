package cst.command.chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.ChatDAO;
import cst.dao.UserDAO;

public class ChatSendCommand implements CstCommand {
    
	public ChatSendCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String fromID = req.getParameter("fromID");
		String toID = req.getParameter("toID");
		String chatContent = req.getParameter("chatContent");
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals("")) {
			res.getWriter().write("0");
			return 0;
		} else {
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			
			res.getWriter().write(new ChatDAO().sendChat(fromID, toID, chatContent) + "");
			return 1;
		}
	}
}