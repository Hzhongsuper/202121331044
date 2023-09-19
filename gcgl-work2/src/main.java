import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class main {

    public static void main(String[] args) throws IOException {
        // 获取命令行参数
        String origPath = args[0]; // 原文文件路径
        String copyPath = args[1]; // 抄袭版论文文件路径
        String ansPath = args[2]; // 答案文件路径

        // 读取文件内容
        String origText = Files.readString(Path.of(origPath));
        String copyText = Files.readString(Path.of(copyPath));

        // 计算相似度
        double similarity = getSimilarity(origText, copyText);

        // 转换为百分比形式，并保留两位小数
        String result = String.format("%.2f%%", similarity * 100);

        // 写入答案文件
        Files.writeString(Path.of(ansPath), result);
    }

    // 定义一个方法，用于计算两个字符串之间的相似度
    public static double getSimilarity(String s1, String s2) {
        // 使用编辑距离算法
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1]; // 动态规划表

        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充表格
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // 相同则不变
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1; // 不同则取最小值加一
                }
            }
        }

        // 计算相似度
        int editDistance = dp[m][n]; // 编辑距离
        int maxLength = Math.max(m, n); // 最大长度
        double similarity = 1 - (double) editDistance / maxLength; // 相似度
        return similarity;
    }
}
