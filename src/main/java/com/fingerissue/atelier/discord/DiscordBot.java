package com.fingerissue.atelier.discord;

import com.fingerissue.atelier.discord.commands.Ping;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS
                    )
                    .build();

            jda.awaitReady();
            logger.info("{} 봇이 성공적으로 로그인되었습니다.", jda.getSelfUser().getName());

            addComands();
            jda.awaitReady();
            logger.info("명령어가 성공적으로 추가되었습니다.");

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
     * 디스코드 봇의 명령어를 추가합니다.
     */
    public void addComands() {
        jda = getJDA();

        jda.addEventListener(new Ping());
        jda.updateCommands().addCommands(Commands.slash("ping", "pong")).queue(
                commands -> logger.debug("ping 명령어가 추가되었습니다."),
                throwable -> logger.error("ping 명령어를 추가하지 못했습니다. ", throwable)
        );
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