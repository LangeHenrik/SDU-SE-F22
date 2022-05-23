package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;

public interface OneWayInterface extends Filterable {

    public void initializeTable();

    public void addItem(String name, String superItemName);

    public void changeSuperId(String itemName, String newSuperItemName);

    public void changeItemName(String newName, String oldName);

    public void deleteItem(String name);

    public void printCatalog();


}
