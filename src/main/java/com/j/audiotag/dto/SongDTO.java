package com.j.audiotag.dto;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-27 10:40:13
 */
@Data
public class SongDTO {
    /**
     * 音频文件绝对路径
     */
    private String absolutePath;

    /**
     * 网易云歌曲 id
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
     * 专辑封面 url
     */
    private String albumPicUrl;

    /**
     * 年份
     */
    private String year;

    /**
     * Track
     */
    private String track;

    /**
     * 歌词
     */
    private String lyric;
}
