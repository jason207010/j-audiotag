package com.j.audiotag.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.j.audiotag.domain.Response;
import com.j.audiotag.dto.SongDTO;
import com.j.audiotag.service.AudioTagService;
import com.j.audiotag.vo.AudioTagVO;
import com.j.audiotag.vo.SearchVO;
import com.j.audiotag.vo.SongVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jason
 * @since 2022-07-25 09:30:58
 */
@Controller
public class AudioTagController {

    @Resource
    private AudioTagService audioTagService;

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 扫描音乐标签
     * @param filePath
     * @return
     * @throws Exception
     */
    @GetMapping("/scan")
    @ResponseBody
    public Response<List<AudioTagVO>> scan(@RequestParam("filePath") String filePath) throws Exception {
        return Response.success(audioTagService.scan(filePath));
    }

    /**
     * 网易云搜索
     * @param fileName
     * @return
     * @throws Exception
     */
    @GetMapping("/search")
    @ResponseBody
    public Response<SearchVO> search(@RequestParam("fileName") String fileName) throws Exception {
        String keyWord = FileNameUtil.getPrefix(fileName);
        keyWord = StrUtil.replace(keyWord, "-", " ");
        return Response.success(audioTagService.search(keyWord));
    }

    /**
     * 歌曲详情
     * @param songId
     * @return
     * @throws Exception
     */
    @GetMapping("/song")
    @ResponseBody
    public Response<SongVO> song(@RequestParam("songId") Long songId) throws Exception {
        return Response.success(audioTagService.song(songId));
    }

    /**
     * 写入音乐标签
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping("/writeTag")
    @ResponseBody
    public Response<Object> writeTag(@RequestBody SongDTO dto) throws Exception {
        audioTagService.writeAudioTag(dto);
        return Response.success();
    }
}
