package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.BCommand;
import command.BContentViewCommand;
import command.BDeleteCommand;
import command.BInsertCommand;
import command.BListCommand;
import command.BUpdateCommand;

/**
 * Servlet implementation class BController
 */
@WebServlet("*.do")
public class BController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() ..");
		
		actionDo(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost() ..");
		
		actionDo(request,response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String comm = uri.substring(contextPath.length());
		
		System.out.println("uri : " + uri);
		System.out.println("contextPath : " + contextPath);
		System.out.println("comm : " + comm);
		
		String viewPage = null;
		BCommand command = null;
		
		if(comm.equals("/list.do")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
		}else if(comm.equals("/write.do")) {
			viewPage = "write.jsp";
		}else if(comm.equals("/insert.do")) {
			command = new BInsertCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if(comm.equals("/content_view.do")) {
			command = new BContentViewCommand();
			command.execute(request, response);
			viewPage = "content.jsp";
		}else if(comm.equals("/modify.do")) {
			viewPage = "modify.jsp";
		}else if(comm.equals("/update.do")) {
			command = new BUpdateCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}else if(comm.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		
		if(viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
}
