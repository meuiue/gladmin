package site.gladmin.homework.trains.entity;

import lombok.Data;
import site.gladmin.homework.trains.enums.Operate;
import site.gladmin.homework.trains.enums.Type;

/**
 * 操作参数，路由搜索中的条件
 */
@Data
public class Param {
    private Operate op;
    private Type type;
    private int value;

}
