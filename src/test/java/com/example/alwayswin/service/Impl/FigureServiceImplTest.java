package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.service.impl.FigureServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FigureServiceImplTest {
    private static FigureService figureService;
    @Mock
    private FigureMapper figureMapper;

    @BeforeEach
    public void init() {
        figureMapper = mock(FigureMapper.class);
        figureService = new FigureServiceImpl(figureMapper);
    }

    //////////      upload        ///////////////
    //todo
    @Test
    public void happyPathWithUpload() {

    }


    //////////      getByFid        ///////////////
    @Test
    public void happyPathWithGetFigureByFid() {
        // Integer fid, Integer pid, String url, String description, boolean isThumbnail, Timestamp updatedTime
        Figure figure = new Figure(1, 1 , "www.baidu.com", "baidu", false, new Timestamp(0));
        when(figureMapper.getByFid(anyInt())).thenReturn(figure);

        Figure figure1 = figureService.getFigureByFid(1);
        assertNotNull(figure1);
        assertEquals("baidu", figure1.getDescription());
    }

    @Test
    public void InvalidFidExceptionWithGetFigureByFid() {
        when(figureMapper.getByFid(anyInt())).thenReturn(null);
        Figure figure1 = figureService.getFigureByFid(0);
        assertNull(figure1);
    }


    //////////      getByPid        /////////////////
    @Test
    public void happyPathWithGetFigureByPid() {
        List<Figure> figureList = new ArrayList<>();
        Figure figure = new Figure(1, 1 , "www.baidu.com", "baidu", false, new Timestamp(0));
        figureList.add(figure);

        when(figureMapper.getByPid(anyInt())).thenReturn(figureList);

        List<Figure> figureList1 = figureService.getFiguresByPid(1);
        assertNotNull(figureList1);
        assertEquals(1, figureList1.size());
    }

    @Test
    public void InvalidPidExceptionWithGetFigureByPid() {
        when(figureMapper.getByPid(anyInt())).thenReturn(null);
        List<Figure> figureList1 = figureService.getFiguresByPid(0);

        assertNull(figureList1);
    }

    //////////      getThumbNailByPid        //////////////////////
    @Test
    public void happyPathWithGetThumbNailByPid() {
        // Integer fid, Integer pid, String url, String description, boolean isThumbnail, Timestamp updatedTime
        Figure figure = new Figure(1, 1 , "www.baidu.com", "baidu", true, new Timestamp(0));
        when(figureMapper.getThumbnailByPid(anyInt())).thenReturn(figure);

        Figure figure1 = figureService.getThumbnailByPid(1);
        assertNotNull(figure1);
        assertTrue(figure1.isThumbnail());
    }

    @Test
    public void InvalidPidExceptionWithGetThumbNailByPid() {
        when(figureMapper.getByFid(anyInt())).thenReturn(null);
        Figure figure1 = figureService.getThumbnailByPid(0);
        assertNull(figure1);
    }

    //////////      addFigure        /////////////////
    @Test
    public void happyPathWithAddFigure() {
        when(figureMapper.add(any())).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("fid", "1");
        param.put("pid", "1");
        param.put("url", "www.baidu.com");
        assertEquals(1, figureService.addFigure(param));
    }

    //////////      updateFigure        /////////////////
    @Test
    public void happyPathWithUpdateFigure() {
        when(figureMapper.update(any(Figure.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("fid", "1");
        param.put("pid", "1");
        param.put("url", "www.baidu.com");
        param.put("isThumbnail", "false");
        figureService.updateFigure(1, param);
    }

    @Test
    public void happyPathWithChangeThumbnail() {
        when(figureMapper.update(any(Figure.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("fid", "2");
        param.put("pid", "1");
        param.put("url", "www.baidu.com");
        param.put("isThumbnail", "true");

        Figure oldThumbnail = new Figure(1, 1 , "www.google.com", "google", true, new Timestamp(0));
        when(figureMapper.getThumbnailByPid(1)).thenReturn(oldThumbnail);
        assertTrue(oldThumbnail.isThumbnail());

        assertEquals(1, figureService.updateFigure(2, param));
        assertFalse(oldThumbnail.isThumbnail());
    }

    @Test
    public void happyPathWithAssignThumbnail() {
        when(figureMapper.update(any(Figure.class))).thenReturn(1);

        Map<String, String> param = new HashMap<>();
        param.put("fid", "2");
        param.put("pid", "1");
        param.put("url", "www.baidu.com");
        param.put("isThumbnail", "true");

        when(figureMapper.getThumbnailByPid(1)).thenReturn(null);
        assertEquals(1, figureService.updateFigure(2, param));
    }


    //////////      deleteFigure        /////////////////
    @Test
    public void happyPathWithDeleteFigure() {
        when(figureMapper.delete(anyInt())).thenReturn(1);
        assertEquals(1, figureService.deleteFigure(1));
    }

    @Test
    public void InvalidFidExceptionWithDeleteFigure() {
        when(figureMapper.delete(anyInt())).thenReturn(0);
        assertEquals(0, figureService.deleteFigure(0));
    }
}