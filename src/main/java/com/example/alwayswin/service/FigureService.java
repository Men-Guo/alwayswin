package com.example.alwayswin.service;

import com.example.alwayswin.entity.Figure;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public interface FigureService {

//    int upload(MultipartFile file) throws FileException;

    Figure getFigureByFid(int fid);

    List<Figure> getFiguresByPid(int pid);

    Figure getThumbnailByPid(int pid);

    int addFigure(Map param);

    int editFigure(int fid, Map param);

    int deleteFigure(int fid);
}
