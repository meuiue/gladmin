package site.gladmin.homework.trains.enums;

/**
 * 操作类型枚举类
 */
public enum Operate {

    LESS("less", 1),EQUEL("equel", 2),LESSANDEQUEL("no more than", 3);

    private String name;
    private int value;

    Operate(String name, int value) {
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
