package cn.mrray.rocksdbkeeper.enums;

import java.util.ArrayList;
import java.util.List;

public enum TableEnums {
    DID_TABLE("did_table", 0, "did数据表"),

    DID_STATE_TABLE("did_state_table",1, "did状态数据表"),

    DID_KEY_TABLE("did_key_table", 2, "did秘钥数据库"),
    ;
    /**
     * 列族
     */
    private String table;


    private int index;

    /**
     * 描述
     */
    private String desc;

    TableEnums(String table, int index, String desc) {
        this.table = table;
        this.index = index;
        this.desc = desc;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static List<String> getTableList() {
        TableEnums[] tableEnums = values();

        List<String> tableList = new ArrayList<>();
        for (TableEnums tableEnum : tableEnums) {
            tableList.add(tableEnum.getTable());
        }
        return tableList;
    }


    public static String getTable(String table) {

        TableEnums[] tableEnums = values();
        for (TableEnums tableEnum : tableEnums) {
            if (tableEnum.getTable().equals(table)) {
                return tableEnum.getTable();
            }

        }
        return null;
    }

    public static int getTableIndex(String table) {

        TableEnums[] tableEnums = values();
        for (TableEnums tableEnum : tableEnums) {
            if (tableEnum.getTable().equals(table)) {
                return tableEnum.getIndex();
            }

        }
        return 0;
    }


}
