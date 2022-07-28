package com.j.audiotag.domain;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-13 15:57:10
 */
@Data
public class AudioTag {
    /**
     * 歌手
     */
    private String artist;

    /**
     * 歌名
     */
    private String title;

    /**
     * 专辑
     */
    private String album;

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

    /**
     * 封面
     */
    private byte[] artwork;

    /**
     * 封面类型
     */
    private String artworkMimeType;
}
