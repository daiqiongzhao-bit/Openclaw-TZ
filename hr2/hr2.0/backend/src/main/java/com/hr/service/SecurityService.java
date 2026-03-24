package com.hr.service;

import java.util.Map;

public interface SecurityService {
    // 加密
    String encrypt(String data);
    String decrypt(String encryptedData);
    
    // 密码加密
    String encryptPassword(String password);
    boolean verifyPassword(String password, String encryptedPassword);
    
    // 接口安全
    boolean verifySignature(Map<String, Object> params, String signature);
    boolean checkRequestRate(String ipAddress);
    boolean checkReplayAttack(String requestId);
    
    // 数据脱敏
    String maskSensitiveData(String data, String type);
}