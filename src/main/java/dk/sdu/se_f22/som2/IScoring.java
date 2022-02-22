package dk.sdu.se_f22.som2;

public interface IScoring {

    /**
    <p> Returns weight value sum for multiple categories </p>
    */
    int sumScore();

    /**
    <p> Returns weight value for price </p>
    */
    int price();

    /**
    <p> Returns weight value for review </p>
    */
    int review();

    /**
    <p> Returns weight value for stock </p>
    */
    int stock();

    /**
    <p> Returns weight value for release date </p>
    */
    int releaseDate();

    /**
    <p> ??? </p>
    */
    void update();
}
