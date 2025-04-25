
package com.fingerissue.atelier.discord;

import com.fingerissue.atelier.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordBotConfig {
    private static final Logger logger = LoggerFactory.getLogger(DiscordBotConfig.class);

    private String token;
    private String clientId;
    private String permissions;

    /**
     * 설정 관리자에서 디스코드 봇 설정을 로드합니다.
     *
     * @param configManager 설정 관리자 인스턴스
     * @return 설정 로드 성공 여부
     */
    public boolean loadFromConfig(ConfigManager configManager) {
        token = configManager.getProperty("discord.token");
        if (token == null || token.isEmpty() || "BOT_TOKEN".equals(token)) {
            logger.error("discord.token 값이 존재하지 않거나 제대로 설정되어 있지 않습니다.");
            return false;
        }

        clientId = configManager.getProperty("discord.clientID", "NONE");
        permissions = configManager.getProperty("discord.permission", "0");
        return true;
    }

    /**
     * 디스코드 봇 초대 URL을 생성합니다.
     *
     * @return 봇 초대 URL
     */
    public String generateInviteUrl() {
        if (clientId == null || clientId.isEmpty() || "NONE".equals(clientId)) {
            logger.warn("클라이언트 ID가 설정되지 않았습니다. 초대 URL을 생성할 수 없습니다.");
            return "";
        }

        return String.format(
                "https://discord.com/oauth2/authorize?client_id=%s&permissions=%s&scope=bot",
                clientId, permissions
        );
    }

    public String getToken() {
        return token;
    }

    public String getClientId() {
        return clientId;
    }

    public String getPermissions() {
        return permissions;
    }
}