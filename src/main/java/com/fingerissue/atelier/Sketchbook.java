package com.fingerissue.atelier;

import com.fingerissue.atelier.config.ConfigManager;
import com.fingerissue.atelier.discord.DiscordBot;
import com.fingerissue.atelier.discord.DiscordBotConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Sketchbook {
    private static final Logger logger = LoggerFactory.getLogger(Sketchbook.class);
    private static DiscordBot discordbot;

    public static void main(String[] args) {
        logger.info("애플리케이션을 시작합니다.");
        discordbotLogin();
    }

    public static void discordbotLogin() {
        logger.debug("디스코드 봇 로그인을 시작합니다.");

        ConfigManager configManager = new ConfigManager();
        DiscordBotConfig discordBotConfig = new DiscordBotConfig();

        if (!configManager.loadConfig()) {
            logger.error("설정을 로드하지 못했습니다. 애플리케이션을 종료합니다.");
            System.exit(1);
        }

        if (!discordBotConfig.loadFromConfig(configManager)) {
            logger.error("디스코드 봇 설정을 로드하지 못했습니다.");
            System.exit(1);
        }

        discordbot = new DiscordBot(discordBotConfig);

        if(!discordbot.login()) {
            logger.error("디스코드 봇 로그인에 실패했습니다.");
            System.exit(1);
        }

        logger.info("디스코드 봇 로그인에 성공했습니다.");
    }

    /**
     * 애플리케이션 종료 시 실행할 작업을 등록합니다.
     */
    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("애플리케이션을 종료합니다...");

            // 디스코드 봇 종료
            if (discordbot != null) {
                discordbot.shutdown();
            }

            logger.info("애플리케이션이 정상적으로 종료되었습니다.");
        }));
    }

}
