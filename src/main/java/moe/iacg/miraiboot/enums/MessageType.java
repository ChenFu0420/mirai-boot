package moe.iacg.miraiboot.enums;

public enum MessageType {

    /**
     * 组消息
     */
    GROUP(0,"group"),

    /**
     * 私聊消息
     */
    PRIVATE(1,"private");

    MessageType(int key,String val) {
        this.key = key;
        this.val = val;
    }

    public int getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    private int key;
    private String val;
}
