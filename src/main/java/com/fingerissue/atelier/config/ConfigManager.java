package com.fingerissue.atelier.config;

import com.fingerissue.atelier.Sketchbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private final Properties properties = new Properties();
    private boolean isLoaded = false;

    /**
     * 기본 설정 파일("config.properties")에서 설정을 로드합니다.
     *
     * @return 로드 성공 여부
     */
    public boolean loadConfig() {
        return loadConfig("config.properties");
    }

    /**
     * 지정된 설정 파일에서 설정을 로드합니다.
     *
     * @param configFileName 설정 파일명
     * @return 로드 성공 여부
     */
    public boolean loadConfig(String configFileName) {
        try {
            // configFileName 매개변수를 사용하여 리소스 스트림을 얻습니다.
            InputStream input = Sketchbook.class.getClassLoader().getResourceAsStream(configFileName);
            if (input == null) {
                logger.error("{}를 읽을 수 없습니다.", configFileName);
                return false;
            }

            properties.load(input);
            input.close();
            isLoaded = true;
            logger.debug("설정 파일 {} 로드 완료", configFileName);
            return true;
        } catch (IOException e) {
            logger.error("설정 파일 {}을 읽는 중 오류가 발생했습니다.", configFileName, e);
            return false;
        }
    }

    /**
     * 외부 파일 경로에서 설정을 로드합니다.
     *
     * @param path 설정 파일 경로
     * @return 로드 성공 여부
     */
    public boolean loadConfigFromPath(Path path) {
        try {
            if (!Files.exists(path)) {
                logger.error("파일이 존재하지 않습니다: {}", path);
                return false;
            }

            try (InputStream input = Files.newInputStream(path)) {
                properties.load(input);
                isLoaded = true;
                logger.debug("외부 설정 파일 {} 로드 완료", path);
                return true;
            }
        } catch (IOException e) {
            logger.error("외부 설정 파일 {}을 읽는 중 오류가 발생했습니다.", path, e);
            return false;
        }
    }

    /**
     * 지정된 키에 해당하는 설정값을 가져옵니다.
     *
     * @param key 설정 키
     * @return 설정값 또는 null (설정이 없는 경우)
     */
    public String getProperty(String key) {
        if (!isLoaded) {
            logger.warn("설정이 로드되지 않았습니다. 설정값을 불러오기 전에 loadConfig()를 호출하세요.");
        }
        return properties.getProperty(key);
    }

    /**
     * 지정된 키에 해당하는 설정값을 가져옵니다. 설정이 없는 경우 기본값을 반환합니다.
     *
     * @param key 설정 키
     * @param defaultValue 기본값
     * @return 설정값 또는 기본값
     */
    public String getProperty(String key, String defaultValue) {
        if (!isLoaded) {
            logger.warn("설정이 로드되지 않았습니다. 설정값을 불러오기 전에 loadConfig()를 호출하세요.");
        }
        return properties.getProperty(key, defaultValue);
    }
}