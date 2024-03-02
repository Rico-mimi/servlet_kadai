package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DeleteMutterLogic;
import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter>mutterList = getMutterListLogic.execute();
		request.setAttribute("mutterList", mutterList);
	
	//ログインしているか確認するため
	//セッションスコープからユーザー情報を取得
	HttpSession session = request.getSession();
	User loginUser = (User)session.getAttribute("loginUser");
	
	if(loginUser== null) {//ログインしていない場合
		//リダイレクト
		response.sendRedirect("index.jsp");
	}else {//ログイン済み
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
	
	}
	
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		
		//リクエストパラメーターの取得
		String text = request.getParameter("text");
		//削除用
		String mutterId = request.getParameter("mutterId");
		String name = request.getParameter("name");
		
		//入力値チェック
		if(text != null && text.length() != 0) {
			//セッションスコープに保存されたユーザー情報を取得
			HttpSession session =request.getSession();
			User loginUser = (User)session.getAttribute("loginUser");
			
			//つぶやきをつぶやきリストに追加
			Mutter mutter = new Mutter(loginUser.getName(),text);
			PostMutterLogic postMutterLogic = new PostMutterLogic();
			postMutterLogic.execute(mutter);
		
		}else if(mutterId != null && mutterId.length() != 0) {
			int mutterIdNumber = Integer.parseInt(mutterId);
			
			HttpSession session =request.getSession();
			User loginUser = (User)session.getAttribute("loginUser");
			
			//削除処理を実行
			DeleteMutterLogic deleteMutterLogic = new DeleteMutterLogic();
			deleteMutterLogic.execute(loginUser.getName(),mutterIdNumber);
			
			if(!loginUser.getName().equals(name)) {
				request.setAttribute("errorMsg", "他人のつぶやきは削除できません");
			}

		}else {
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "つぶやきが入力されていません。");
		}
		
		
		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter>mutterList = getMutterListLogic.execute();
		request.setAttribute("mutterList", mutterList);
		
		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
		

	}

}
