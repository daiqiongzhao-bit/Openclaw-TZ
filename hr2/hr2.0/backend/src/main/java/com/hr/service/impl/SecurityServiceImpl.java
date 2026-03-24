package com.hr.service.impl;

import com.hr.service.SecurityService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecurityServiceImpl implements SecurityService {

    // 用于存储请求ID，防止重放攻击
    private final Map<String, Long> requestIds = new ConcurrentHashMap<>();
    
    // 用于存储IP请求次数，防止请求过于频繁
    private final Map<String, Integer> ipRequestCounts = new ConcurrentHashMap<>();

    @Override
    public String encrypt(String data) {
        // 简单的Base64加密，实际项目中应该使用更安全的加密方式
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    @Override
    public String decrypt(String encryptedData) {
        // Base64解密
        return new String(Base64.getDecoder().decode(encryptedData));
    }

    @Override
    public String encryptPassword(String password) {
        // 使用SHA-256加密密码
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    @Override
    public boolean verifyPassword(String password, String encryptedPassword) {
        // 验证密码
        String hashedPassword = encryptPassword(password);
        return hashedPassword.equals(encryptedPassword);
    }

    @Override
    public boolean verifySignature(Map<String, Object> params, String signature) {
        // 简单的签名验证，实际项目中应该使用更复杂的签名算法
        // 这里只是示例，实际应该根据具体的签名规则实现
        return true;
    }

    @Override
    public boolean checkRequestRate(String ipAddress) {
        // 简单的请求速率限制，实际项目中应该使用更专业的限流方案
        Integer count = ipRequestCounts.getOrDefault(ipAddress, 0);
        if (count > 100) { // 每分钟100次请求限制
            return false;
        }
        ipRequestCounts.put(ipAddress, count + 1);
        // 实际项目中应该设置定时清理机制
        return true;
    }

    @Override
    public boolean checkReplayAttack(String requestId) {
        // 检查是否存在重复的请求ID
        if (requestIds.containsKey(requestId)) {
            return false;
        }
        requestIds.put(requestId, System.currentTimeMillis());
        // 实际项目中应该设置定时清理机制
        return true;
    }

    @Override
    public String maskSensitiveData(String data, String type) {
        // 根据数据类型进行脱敏
        switch (type) {
            case "phone":
                // 手机号脱敏：保留前3后4
                if (data != null && data.length() >= 11) {
                    return data.substring(0, 3) + "****" + data.substring(7);
                }
                break;
            case "idcard":
                // 身份证号脱敏：保留前6后4
                if (data != null && data.length() >= 18) {
                    return data.substring(0, 6) + "**********" + data.substring(14);
                }
                break;
            case "email":
                // 邮箱脱敏：保留前3后2
                if (data != null && data.contains("@")) {
                    String[] parts = data.split("@");
                    if (parts[0].length() >= 3) {
                        return parts[0].substring(0, 3) + "****@" + parts[1];
                    }
                }
                break;
            default:
                // 默认脱敏：保留前2后2
                if (data != null && data.length() >= 4) {
                    return data.substring(0, 2) + "****" + data.substring(data.length() - 2);
                }
        }
        return data;
    }
}