package dk.sdu.se_f22.productmodule.infrastructure.data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class TokenParameterStore  {
	public static String DELIMITER_DEFAULT = " ";
	public static String IGNORED_CHARS_DEFAULT = " ";

	public static void saveTokenParameter(TokenParameter tp) {
		StringBuilder sb = new StringBuilder();
		for (String ignored : tp.getIgnoredChars()) {
			sb.append(ignored);
		}
		String ignoredChars = sb.toString();
		try {
			PreparedStatement stmt = DBConnection.getPooledConnection().prepareStatement
					("INSERT INTO token_parameters(delimiter, ignored_chars, type) VALUES (?,?,?)");
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
		try{
			PreparedStatement stmt = DBConnection.getPooledConnection().prepareStatement("SELECT delimiter, ignored_chars FROM token_parameters WHERE type = 'Product' ORDER BY id DESC LIMIT 1;");
			ResultSet queryResultSet = stmt.executeQuery();
			if(queryResultSet.next()){
				return new TokenParameter(queryResultSet.getString("delimiter"),queryResultSet.getString("ignored_chars"));
			}
			queryResultSet.close();
			stmt.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}

		return new TokenParameter(DELIMITER_DEFAULT, IGNORED_CHARS_DEFAULT);
	}


}
