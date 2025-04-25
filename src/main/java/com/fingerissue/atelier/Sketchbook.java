package com.fingerissue.atelier;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Sketchbook {
    private static final Logger logger = LoggerFactory.getLogger(Sketchbook.class);

    public static void main(String[] args) {
        discordbotLogin();
    }

    public static void discordbotLogin() {
        logger.debug("디스코드 봇 로그인을 시작합니다.");

        Properties properties = new Properties();
        String token;

        try {
            InputStream input = Sketchbook.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                logger.error("config.properties를 읽을 수 없습니다.");
                return;
            }

            properties.load(input);

            token = properties.getProperty("discord.token");
            if (token == null || token.isEmpty() || token.equals("BOT_TOKEN")) {
                logger.error("discord.token 값이 config.properties 파일에 존재하지 않거나 제대로 세팅되어 있지 않습니다.");
                return;
            }

            JDA api = JDABuilder.createDefault(token)
                    .enableIntents(
                            GatewayIntent.MESSAGE_CONTENT
                    )
                    .build();

            logger.info("{} 봇이 로그인 되었습니다.", api.getSelfUser().getName());

            final String clientID = "1364870723146874881";
            final int permissions = 0;
            logger.info("봇 초대하기: https://discord.com/oauth2/authorize?client_id={}&permissions={}&scope=bot", clientID, permissions);

        } catch (IOException e) {
            logger.error("config.properties 파일을 읽는 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            logger.error("예외가 발생했습니다.", e);
        }
    }
}
