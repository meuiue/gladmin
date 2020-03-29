package site.gladmin.homework.trains.enums;

/**
 * 比较对象枚举类
 */
public enum Type {
    STOPS("stops", 1), DISTANCE("distance", 2);
    private String name;
    private int value;

    Type(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
