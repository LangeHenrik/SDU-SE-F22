package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;

public interface OneWayInterface extends Filterable {

    public void createItem(String name, String superItemName);

    public void changeItemPlacement();

    public void showCatalog();

    public void changeItemName();

    public void returnNameList();

}
