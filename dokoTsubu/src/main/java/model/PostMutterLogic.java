package model;

/*import java.util.List;*/
import dao.MuttersDAO;

public class PostMutterLogic {
	public void execute(Mutter mutter) {
		MuttersDAO dao = new MuttersDAO();
		dao.create(mutter);
	}

}
