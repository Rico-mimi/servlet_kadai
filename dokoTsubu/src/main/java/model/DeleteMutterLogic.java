package model;

import dao.MuttersDAO;

public class DeleteMutterLogic {
	public void execute(String name,int mutterId) {
		MuttersDAO dao = new MuttersDAO();
		dao.delete(name,mutterId);
	}

}
