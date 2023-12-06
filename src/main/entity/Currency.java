package main.entity;

public class Currency {
    private int id;
    private String name;
    private String code;

    public Currency(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.code = symbol;
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

    public String getCode() {
        return code;
    }

    public void setCode(String symbol) {
        this.code = symbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + code + '\'' +
                '}';
    }
}
