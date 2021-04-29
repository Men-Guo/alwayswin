package com.example.alwayswin.service.Impl;

import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.service.impl.FigureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FigureServiceImplTest {
    private static FigureService figureService;
    @Mock
    private FigureMapper figureMapper;

    @BeforeEach
    public void init() {
        figureMapper = mock(FigureMapper.class);
        figureService = new FigureServiceImpl(figureMapper);
    }

}