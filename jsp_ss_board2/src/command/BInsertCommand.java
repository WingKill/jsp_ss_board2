package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BDao;
import dto.BDto;

public class BInsertCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bname = request.getParameter("bname");
		String btitle = request.getParameter("btitle");
		String bcontent = request.getParameter("bcontent");
		
		BDto dto = new BDto();
		dto.setBname(bname);
		dto.setBtitle(btitle);
		dto.setBcontent(bcontent);
		
		BDao dao = new BDao();
		
		dao.insert(dto);
	}
}
