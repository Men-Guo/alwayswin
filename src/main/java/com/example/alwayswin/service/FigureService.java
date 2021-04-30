package com.example.alwayswin.service;

import com.example.alwayswin.entity.Figure;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface FigureService {

    String upload(MultipartFile file);

    Figure getFigureByFid(int fid);

    List<Figure> getFiguresByPid(int pid);

    Figure getThumbnailByPid(int pid);

    int addFigure(Map param);

    int updateFigure(int fid, Map param);

    int deleteFigure(int fid);
}
