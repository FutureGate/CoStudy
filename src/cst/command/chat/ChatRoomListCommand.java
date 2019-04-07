package cst.command.chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cst.command.CstCommand;
import cst.dao.ChatDAO;
import cst.dto.ChatDTO;

public class ChatRoomListCommand implements CstCommand {
    
	public ChatRoomListCommand() {
        super();
    }

	@Override
	public int execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String fromID = req.getParameter("fromID");
		String toID = req.getParameter("toID");
		String lastID = req.getParameter("lastID");

		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || lastID == null || lastID.equals("")) {
			res.getWriter().write("");
			return 0;
		} else {
			try {
				fromID = URLDecoder.decode(fromID, "UTF-8");
				toID = URLDecoder.decode(toID, "UTF-8");
				
				res.getWriter().write(getChatRoomList(fromID, toID, lastID));
			} catch (Exception e) {
				res.getWriter().write("");
			}
			return 1;
		}
	}
	
	public String getChatRoomList(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		
		ChatDAO dao = new ChatDAO();
		ArrayList<ChatDTO> chatList = dao.getChatListByID(fromID, toID, chatID);
		
		if(chatList.size() == 0) return "";
		
		for(int i=0; i<chatList.size(); i++) {
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