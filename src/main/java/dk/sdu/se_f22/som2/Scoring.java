package dk.sdu.se_f22.som2;

public class Scoring implements IScoring{


    @Override
    public int sumScore() {
        int sum = price() + review() + stock() + releaseDate();
        return sum;
    }

    @Override
    public int price() {
        return 0;
    }

    @Override
    public int review() {
        return 0;
    }

    @Override
    public int stock() {
        return 0;
    }

    @Override
    public int releaseDate() {
        return 0;
    }

    @Override
    public void update() {

    }
}
