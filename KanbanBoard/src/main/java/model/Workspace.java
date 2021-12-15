package model;

public class Workspace {
    private int id;
    private String name;
    private Favorite favorite;

    public Workspace() {
    }

    public Workspace(String name) {
        this.name = name;
        this.favorite = Favorite.NON;
    }



    public Workspace(int id, String name) {
        this.id = id;
        this.name = name;
        this.favorite = Favorite.NON;
    }

    public Workspace(int id, String name, Favorite favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }
}
