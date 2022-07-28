package com.j.audiotag.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.j.audiotag.domain.AudioTag;
import com.j.audiotag.dto.SongDTO;
import com.j.audiotag.vo.AudioTagVO;
import com.j.audiotag.vo.SearchVO;
import com.j.audiotag.vo.SongVO;
import com.j.audiotag.vo.netease.NeteaseCloudMusicLyricVO;
import com.j.audiotag.vo.netease.NeteaseCloudMusicSearchVO;
import com.j.audiotag.vo.netease.NeteaseCloudMusicSongDetailVO;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagTextField;
import org.jaudiotagger.tag.id3.valuepair.ImageFormats;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.StandardArtwork;
import org.jaudiotagger.tag.reference.PictureTypes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jason
 * @since 2022-07-13 15:56:21
 */
@Service
public class AudioTagService {

    @Resource
    private NeteaseCloudMusicService neteaseCloudMusicService;

    /**
     * 读取音乐标签
     * @param file
     * @return
     * @throws Exception
     */
    public AudioTag readAudioTag(File file) throws Exception {
        AudioTag audioTag = new AudioTag();
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        Artwork artwork = tag.getFirstArtwork();
        if(ObjectUtil.isNotNull(artwork)) {
            audioTag.setArtwork(artwork.getBinaryData());
            audioTag.setArtworkMimeType(artwork.getMimeType());
        }
        TagTextField albumTagField = (TagTextField)tag.getFirstField(FieldKey.ALBUM);
        audioTag.setAlbum(albumTagField.getContent());
        TagTextField yearTagField = (TagTextField) tag.getFirstField(FieldKey.YEAR);
        audioTag.setYear(yearTagField.getContent());
        TagTextField trackTagField = (TagTextField) tag.getFirstField(FieldKey.TRACK);
        audioTag.setTrack(trackTagField.getContent());
        TagTextField titleTagField = (TagTextField) tag.getFirstField(FieldKey.TITLE);
        audioTag.setTitle(titleTagField.getContent());
        TagTextField artistTagField = (TagTextField) tag.getFirstField(FieldKey.ARTIST);
        audioTag.setArtist(artistTagField.getContent());
        TagTextField lyricsTagField = (TagTextField) tag.getFirstField(FieldKey.LYRICS);
        audioTag.setLyric(lyricsTagField.getContent());
        return audioTag;
    }

    /**
     * 写入音乐标签
     * @param dto
     * @throws Exception
     */
    public void writeAudioTag(SongDTO dto) throws Exception {
        File file = FileUtil.file(dto.getAbsolutePath());
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        tag.deleteArtworkField();
        StandardArtwork artwork = new StandardArtwork();
        byte[] albumBytes = HttpUtil.downloadBytes(dto.getAlbumPicUrl());
        artwork.setBinaryData(albumBytes);
        artwork.setMimeType(ImageFormats.getMimeTypeForBinarySignature(albumBytes));
        artwork.setDescription("");
        artwork.setPictureType(PictureTypes.DEFAULT_ID);
        tag.setField(artwork);
        tag.setField(FieldKey.ALBUM, dto.getAlbum());
        tag.setField(FieldKey.YEAR, dto.getYear());
        tag.setField(FieldKey.TRACK, dto.getTrack());
        tag.setField(FieldKey.TITLE, dto.getTitle());
        tag.setField(FieldKey.ARTIST, dto.getArtist());
        tag.setField(FieldKey.LYRICS, dto.getLyric());
        AudioFileIO.write(audioFile);
    }

    /**
     * 扫描音乐标签
     * @param filePath
     * @return
     * @throws Exception
     */
    public List<AudioTagVO> scan(String filePath) throws Exception {
        List<AudioTagVO> audioTagVOS = ListUtil.toList();
        if(!FileUtil.exist(filePath)) {
            return audioTagVOS;
        }

        List<File> files;
        if(FileUtil.isDirectory(filePath)) {
            files = FileUtil.loopFiles(FileUtil.file(filePath), 1, f -> StrUtil.equalsAnyIgnoreCase(FileNameUtil.getSuffix(f), "mp3", "flac"));
        } else {
            files = ListUtil.toList(FileUtil.file(filePath));
        }
        for (File file : files) {
            AudioTag audioTag = readAudioTag(file);
            AudioTagVO audioTagVO = BeanUtil.copyProperties(audioTag, AudioTagVO.class);
            audioTagVO.setFileName(FileNameUtil.getName(file));
            audioTagVO.setAbsolutePath(file.getAbsolutePath());
            if(ObjectUtil.isNotNull(audioTag.getArtwork())) {
                audioTagVO.setAlbumPicBase64(StrUtil.format("data:{};base64,{}", audioTag.getArtworkMimeType(), Base64.encode(audioTag.getArtwork())));
            }
            audioTagVOS.add(audioTagVO);
        }
        return audioTagVOS;
    }

    /**
     * 网易云搜索
     * @param keyWord
     * @return
     * @throws Exception
     */
    public SearchVO search(String keyWord) throws Exception {
        SearchVO searchVO = new SearchVO();
        NeteaseCloudMusicSearchVO neteaseCloudMusicSearchVO = neteaseCloudMusicService.search(keyWord, 20, 0, 1);
        List<SearchVO.Song> songs = neteaseCloudMusicSearchVO.getResult().getSongs()
                .stream()
                .map(song -> {
                    SearchVO.Song s = new SearchVO.Song();
                    s.setId(song.getId());
                    s.setTitle(song.getName());
                    String artist = song.getArtists()
                            .stream()
                            .map(NeteaseCloudMusicSearchVO.Artist::getName)
                            .collect(Collectors.joining("/"));
                    s.setArtist(artist);
                    s.setAlbum(song.getAlbum().getName());
                    s.setYear(LocalDateTimeUtil.format(LocalDateTimeUtil.of(song.getAlbum().getPublishTime()), DatePattern.NORM_YEAR_PATTERN));
                    return s;
                }).collect(Collectors.toList());
        searchVO.setSongs(songs);
        return searchVO;
    }

    /**
     * 歌曲信息
     * @param songId
     * @return
     * @throws Exception
     */
    public SongVO song(Long songId) throws Exception {
        SongVO songVO = new SongVO();
        NeteaseCloudMusicSongDetailVO neteaseCloudMusicSongDetailVO = neteaseCloudMusicService.songDetail(songId);
        NeteaseCloudMusicLyricVO neteaseCloudMusicLyricVO = neteaseCloudMusicService.lyric(songId);
        NeteaseCloudMusicSongDetailVO.Song song = neteaseCloudMusicSongDetailVO.getSongs().get(0);
        songVO.setId(song.getId());
        songVO.setTitle(song.getName());
        songVO.setArtist(song.getAr().stream().map(NeteaseCloudMusicSongDetailVO.Artist::getName).collect(Collectors.joining("/")));
        songVO.setAlbum(song.getAl().getName());
        songVO.setAlbumPicUrl(song.getAl().getPicUrl());
        songVO.setYear(LocalDateTimeUtil.format(LocalDateTimeUtil.of(song.getPublishTime()), DatePattern.NORM_YEAR_PATTERN));
        songVO.setTrack(StrUtil.toString(song.getNo()));
        songVO.setLyric(neteaseCloudMusicLyricVO.getLrc().getLyric());
        return songVO;
    }

}
