package com.project.service.JeongService;

public interface EmailService {
    
    String sendSimpleMessage(String to)throws Exception;

    /**
     * 암호변경 이메일 전송
     * @param to 이메일 보낼곳
     * @throws Exception
     */
    void sendSimpleMessageforPassword(String to, String token)throws Exception;

    String createKey();
}
