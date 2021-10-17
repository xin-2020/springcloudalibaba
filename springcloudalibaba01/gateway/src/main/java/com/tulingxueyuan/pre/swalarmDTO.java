package com.tulingxueyuan.pre;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @date 2021年10月17日16:29
 */
@Getter
@Setter
public class swalarmDTO {

    private int scopeId;
    private String scope;
    private String name;
    private String id0;
    private String id1;
    private String ruleName;
    private String AlarmMessage;
    private List<Tag> tags;
    private long startTime;
    private transient int period;
    private transient boolean onlyAsCondition;

    private List<Tag> events = new ArrayList<>(2);


    @Data
    public static class Tag{
    private String key;
    private String value;
    }
}
