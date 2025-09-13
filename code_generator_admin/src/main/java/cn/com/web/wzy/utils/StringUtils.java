package cn.com.web.wzy.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串操作
 */
public class StringUtils {
    /**
     * 转换为大驼峰
     * @param str 字符串
     * @return 大驼峰
     */
    public static String toPascalCase(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }

        // 转换为小写
        str = str.toLowerCase();

        // 分割单词（支持下划线和连字符作为分隔符）
        String[] words = str.split("[_\\-]");

        // 将每个单词首字母大写
        List<String> wordList = new ArrayList<>();
        for(String word : words) {
            wordList.add(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        }

        return String.join("", wordList);
    }

    /**
     * 转换为小驼峰
     * @param str 字符串吃
     * @return 小驼峰
     */
    public static String toCamelCase(String str) {
        String pascalCase = toPascalCase(str);
        return Character.toLowerCase(pascalCase.charAt(0)) + pascalCase.substring(1);
    }
}
