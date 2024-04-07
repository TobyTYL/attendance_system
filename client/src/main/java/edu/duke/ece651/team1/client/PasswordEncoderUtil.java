package edu.duke.ece651.team1.client;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        // 创建BCryptPasswordEncoder实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 定义要加密的密码
        String rawPassword = "123";
        
        // 使用BCryptPasswordEncoder将密码加密
        String encodedPassword = encoder.encode(rawPassword);
        
        // 打印加密后的密码
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密后的密码: " + encodedPassword);
    }
}
