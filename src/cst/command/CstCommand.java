package cst.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CstCommand {
	void execute(HttpServletRequest req, HttpServletResponse res);
}