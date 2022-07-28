package com.j.audiotag.vo;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-25 09:42:07
 */
@Data
public class AudioTagVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 绝对路径
     */
    private String absolutePath;
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
     * 专辑封面
     */
    private String albumPicBase64;

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
