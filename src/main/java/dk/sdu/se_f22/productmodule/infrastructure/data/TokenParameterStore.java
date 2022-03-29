package dk.sdu.se_f22.productmodule.infrastructure.data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class TokenParameterStore  {

	public static void saveTokenParameter(TokenParameter tp) {
		StringBuilder sb = new StringBuilder();
		for (String ignored : tp.getIgnoredChars()) {
			sb.append(ignored);
		}
		String ignoredChars = sb.toString();
		try {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement
					("INSERT INTO TokenParameters(delimiter, ignoredChars, type) VALUES (?,?,?)");
			stmt.setString(1, tp.getDelimiter());
			stmt.setString(2, ignoredChars);
			stmt.setString(3, "Product");

			stmt.execute();
			stmt.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static TokenParameter loadTokenParameter() {
		/*try{
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement("SELECT delimiter, ignoredChars FROM TokenParameters WHERE type = 'Product' ORDER BY id DESC LIMIT 1");
			ResultSet queryResultSet = stmt.executeQuery();
			if(queryResultSet.next()){
				return new TokenParameter(queryResultSet.getString("delimiter"),queryResultSet.getString("ignoredChars"));
			}
			queryResultSet.close();
			stmt.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
*/
		return new TokenParameter(" ", "('./?!')");
	}


}
