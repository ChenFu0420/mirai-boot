package moe.iacg.miraiboot.utils;

public class BotUtils {

    //忽略
    private static final int MESSAGE_IGNORE = 0;
    //中断
    private static final int MESSAGE_BLOCK = 1;

    public static boolean verifyCommand(String command, String content) {
        return false;
    }

    public static String removeCommandPrefix(String command, String content) {

        return content.replace("/" + command, "").trim();
    }
}
