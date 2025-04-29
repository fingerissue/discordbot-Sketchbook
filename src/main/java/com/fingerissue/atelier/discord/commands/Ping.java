package com.fingerissue.atelier.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ping extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Ping.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String user = event.getUser().getName();

        try {
            if (event.getName().equals("ping")) {
                long gatewayPing = event.getJDA().getGatewayPing();
                event.reply(String.format("Pong! %dms", gatewayPing)).queue();

                if (logger.isDebugEnabled()) {
                    logger.debug("사용자 {}가 사용한 명령어 /ping 이 정상적으로 작동되었습니다.", user);
                }
            }
        } catch (Exception e) {
            logger.error("사용자 {}가 사용한 명령어 /ping 이 작동에 실패했습니다.", user);
            logger.error(e.getMessage(), e);
        }

    }
}
