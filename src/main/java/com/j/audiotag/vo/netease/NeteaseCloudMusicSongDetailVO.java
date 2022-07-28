package com.j.audiotag.vo.netease;

import lombok.Data;

import java.util.List;

/**
 * @author Jason
 * @since 2022-07-27 08:59:36
 */
@Data
public class NeteaseCloudMusicSongDetailVO {

    private Integer code;
    private List<Song> songs;

    @Data
    public static class Song {
        private String name;
        private Long id;
        private List<Artist> ar;
        private Album al;
        private Integer no;
        private Long publishTime;
    }

    @Data
    public static class Artist {
        private Long id;
        private String name;
    }

    @Data
    public static class Album {
        private Long id;
        private String name;
        private String picUrl;
    }
}
