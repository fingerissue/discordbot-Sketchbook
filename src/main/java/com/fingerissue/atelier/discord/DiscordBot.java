package com.fingerissue.atelier.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscordBot {
    private static final Logger logger = LoggerFactory.getLogger(DiscordBot.class);

    private final DiscordBotConfig config;
    private JDA jda;

    /**
     * 디스코드 봇 인스턴스를 초기화합니다.
     *
     * @param config 디스코드 봇 설정
     */
    public DiscordBot(DiscordBotConfig config) {
        this.config = config;
    }

    /**
     * 디스코드 봇을 로그인합니다.
     *
     * @return 초기화 성공 여부
     */
    public boolean login() {
        try {
            logger.debug("디스코드 봇을 로그인합니다.");

            // JDA 객체를 생성하고 jda 멤버 변수에 할당합니다
            this.jda = JDABuilder.createDefault(config.getToken())
                    .enableIntents(
                            GatewayIntent.MESSAGE_CONTENT
                    )
                    .build();

            jda.awaitReady();
            logger.info("{} 봇이 성공적으로 로그인되었습니다.", jda.getSelfUser().getName());

            if (checkClientId()) {
                String inviteUrl = config.generateInviteUrl(config.getClientId(), config.getPermissions());
                logger.info("봇 초대하기: {}", inviteUrl);
            }

            return true;
        } catch (net.dv8tion.jda.api.exceptions.InvalidTokenException e) {
            logger.error("제공된 디스코드 토큰이 유효하지 않습니다.", e);
            return false;
        } catch (Exception e) {
            logger.error("디스코드 봇 초기화 중 오류가 발생했습니다.", e);
            return false;
        }
    }

    /**
     * 디스코드 봇의 초대링크를 만들 지 체크합니다.
     *
     * @return clientID 설정 여부
     */
    public boolean checkClientId() {
        String clientId = config.getClientId();
        if (clientId == null) {
            return false;
        }
        if (clientId.isEmpty()) {
            return false;
        }
        if ("CLIENT_ID".equals(clientId) || " ".equals(clientId)) {
            return false;
        }

        return true;
    }

    /**
     * 디스코드 봇 연결을 종료합니다.
     */
    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
            logger.info("디스코드 봇 연결이 종료되었습니다.");
        }
    }

    /**
     * JDA 인스턴스를 반환합니다.
     *
     * @return JDA 인스턴스
     */
    public JDA getJDA() {
        return jda;
    }
}