package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BDao;
import dto.BDto;

public class BContentViewCommand implements BCommand {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bId = request.getParameter("bid");
		BDao dao = new BDao();
		BDto board = dao.contentView(bId);
		request.setAttribute("board", board);
	}

}
