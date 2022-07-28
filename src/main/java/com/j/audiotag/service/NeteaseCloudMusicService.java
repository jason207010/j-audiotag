package com.j.audiotag.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j.audiotag.dto.netease.NeteaseCloudMusicLyricDTO;
import com.j.audiotag.dto.netease.NeteaseCloudMusicSearchDTO;
import com.j.audiotag.dto.netease.NeteaseCloudMusicSongDetailDTO;
import com.j.audiotag.utils.NeteaseCloudMusicUtils;
import com.j.audiotag.vo.netease.NeteaseCloudMusicLyricVO;
import com.j.audiotag.vo.netease.NeteaseCloudMusicSearchVO;
import com.j.audiotag.vo.netease.NeteaseCloudMusicSongDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 网易云音乐接口
 * @author Jason
 * @since 2022-07-13 15:13:32
 */
@Service
public class NeteaseCloudMusicService {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 搜索
     * @param keyWord
     * @param limit
     * @param offset
     * @param typee
     * @return
     */
    public NeteaseCloudMusicSearchVO search(String keyWord, Integer limit, Integer offset, Integer typee) throws Exception {
        NeteaseCloudMusicSearchDTO dto = new NeteaseCloudMusicSearchDTO();
        dto.setS(keyWord);
        dto.setLimit(limit);
        dto.setOffset(offset);
        dto.setType(typee);
        Map<String, Object> param = NeteaseCloudMusicUtils.encrypt(JSONUtil.toJsonStr(dto))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String response = HttpUtil.post("https://music.163.com/weapi/search/get", param);
        NeteaseCloudMusicSearchVO neteaseCloudMusicSearchVO = objectMapper.readValue(response, new TypeReference<NeteaseCloudMusicSearchVO>() {});
        return neteaseCloudMusicSearchVO;
    }

    /**
     * 获取歌词
     * @param songId
     * @return
     */
    public NeteaseCloudMusicLyricVO lyric(Long songId) throws Exception {
        NeteaseCloudMusicLyricDTO dto = new NeteaseCloudMusicLyricDTO();
        dto.setId(songId);
        String json = JSONUtil.toJsonStr(dto);
        Map<String, Object> param = NeteaseCloudMusicUtils.encrypt(json)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String response = HttpUtil.post("https://music.163.com/weapi/song/lyric?_nmclfl=1", param);
        NeteaseCloudMusicLyricVO lyricVO = objectMapper.readValue(response, new TypeReference<NeteaseCloudMusicLyricVO>() {});
        StaticLog.info(response);
        return lyricVO;
    }

    /**
     * 歌曲详情
     * @param id
     * @return
     */
    public NeteaseCloudMusicSongDetailVO songDetail(Long id) throws Exception {
        NeteaseCloudMusicSongDetailDTO dto = new NeteaseCloudMusicSongDetailDTO();
        String c = StrUtil.format("[{\"id\":{}}]", id);
        dto.setC(c);
        String json = JSONUtil.toJsonStr(dto);
        StaticLog.info(json);
        Map<String, Object> param = NeteaseCloudMusicUtils.encrypt(json)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String response = HttpUtil.post("https://music.163.com/weapi/v3/song/detail", param);
        NeteaseCloudMusicSongDetailVO neteaseCloudMusicSongDetailVO = objectMapper.readValue(response, new TypeReference<NeteaseCloudMusicSongDetailVO>() {});
        StaticLog.info(response);
        return neteaseCloudMusicSongDetailVO;
    }
}
