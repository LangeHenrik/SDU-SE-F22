package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;

public interface OneWayInterface extends Filterable {

    public void createItem();

    public void changeItemPlacement();

    public void showCatalog();

    public void changeItemName();

    public void returnNameList();

}
