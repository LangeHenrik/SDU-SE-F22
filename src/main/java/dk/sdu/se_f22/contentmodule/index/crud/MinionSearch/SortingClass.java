package crud.MinionSearch;

class SortingClass implements Comparable<SortingClass>{
// id = page_id
    private int id ;
    private int usage;

    SortingClass(int id) {
        this.id = id;
        this.usage = 1;
    }

    int getId() {
        return id;
    }

    int getUsage() {
        return usage;
    }

    void addUsage(){
        usage++;
    }

    //compareTo:for at sortere usage
    @Override
    public int compareTo(SortingClass o){
        if(this.usage > o.getUsage()) {
            return -1;
        } else if (this.usage < o.getUsage()){
            return 1;
        } else {
            return 0;
        }
    }

}
