package com.j.audiotag.vo.netease;

import lombok.Data;

import java.util.List;

/**
 * @author Jason
 * @since 2022-07-11 16:55:34
 */
@Data
public class NeteaseCloudMusicSearchVO {

    private Result result;
    private Integer code;

    @Data
    public static class Result {
        private List<Song> songs;
    }

    @Data
    public static class Song {
        private Long id;
        private String name;
        private List<Artist> artists;
        private Album album;
        private Integer duration;
        private Long copyrightId;
        private Integer status;
        private List<Object> alias;
        private Integer rtype;
        private Integer ftype;
        private Long mvid;
        private Integer fee;
        private String rUrl;
        private Long mark;
    }

    @Data
    public static class Artist {
        private Long id;
        private String name;
        private String picUrl;
        private List<Object> alias;
        private Integer albumSize;
        private Integer picId;
        private String img1v1Url;
        private Integer img1v1;
    }

    @Data
    public static class Album {
        private Long id;
        private String name;
        private Artist artist;
        private Long publishTime;
        private Integer size;
        private Long copyrightId;
        private Integer status;
        private Long picId;
        private Integer mark;
    }
}
