package com.j.audiotag.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Jason
 * @since 2022-07-27 08:50:29
 */
@Data
public class SearchVO {
    /**
     * 歌曲
     */
    private List<Song> songs;

    @Data
    public static class Song {
        /**
         * 网易云音乐歌曲 id
         */
        private Long id;

        /**
         * 歌名
         */
        private String title;

        /**
         * 歌手
         */
        private String artist;

        /**
         * 专辑
         */
        private String album;

        /**
         * 年份
         */
        private String year;
    }
}
