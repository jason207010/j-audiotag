package com.j.audiotag.dto.netease;

import lombok.Data;

/**
 * @author Jason
 * @since 2022-07-13 15:38:42
 */
@Data
public class NeteaseCloudMusicLyricDTO {
    /**
     * 歌曲 id
     */
    private Long id;
    private Integer tv = -1;
    private Integer lv = -1;
    private Integer rv = -1;
    private Integer kv = -1;
}
