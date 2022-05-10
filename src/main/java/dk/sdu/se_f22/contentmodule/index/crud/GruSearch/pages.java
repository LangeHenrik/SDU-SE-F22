package dk.sdu.se_f22.contentmodule.index.crud.GruSearch;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;

public class pages {
    int PageId;
    int TagId;
    int Usage;
    int ForeignId;
    String PageName;

    pages(tagsTable tagsTable, int PageId, String PageName, int ForeignId) {
        this.TagId = tagsTable.getId();
        this.Usage = tagsTable.getUsage();
        this.PageId = PageId;
        this.PageName = PageName;
        this.ForeignId = ForeignId;
    }

    static void finish(ArrayList<tagsTable> sorted, Database db) {
        String SQL;
        ArrayList<pages> pages = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        for (tagsTable i : sorted) {
            SQL = "SELECT * FROM pages WHERE page_id = '" + i.getId() + "'";
            temp = db.executeQueryResultset(SQL);
            System.out.println(temp.toString() + " matches: " + i.getUsage());
        }
    }
}
