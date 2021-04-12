package Models;

public class Otziv
{
    private String message;
    private int id_user;
    private String name;

    public Otziv(String message, int id_user,String name) {
        this.message = message;
        this.id_user = id_user;
        this.name=name;
    }

    public String getMessage() {
        return message;
    }

    public int getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }
}
