package com.j.audiotag.vo.netease;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-27 08:59:36
 */
@Data
public class NeteaseCloudMusicLyricVO {

    private Integer code;
    private LyricUser lyricUser;
    private Lrc lrc;
    private Lrc klyric;
    private Lrc tlyric;
    private Lrc romalrc;

    @Data
    public static class LyricUser {
        private Long id;
        private Integer status;
        private Integer demand;
        private Long userid;
        private String nickname;
        private Long uptime;
    }

    @Data
    public static class Lrc {
        private Integer version;
        private String lyric;
    }
}
