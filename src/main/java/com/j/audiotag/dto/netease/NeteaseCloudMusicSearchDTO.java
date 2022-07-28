package com.j.audiotag.dto.netease;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-11 16:50:04
 */
@Data
public class NeteaseCloudMusicSearchDTO {
    /**
     * 关键词
     */
    private String s;

    /**
     * 1: 单曲, 10: 专辑, 100: 歌手, 1000: 歌单, 1002: 用户, 1004: MV, 1006: 歌词, 1009: 电台, 1014: 视频
     */
    private Integer type;

    /**
     * 分页大小
     */
    private Integer limit;

    /**
     * 分页位置
     */
    private Integer offset;
}
