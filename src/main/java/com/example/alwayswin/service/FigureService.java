package com.example.alwayswin.service;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface FigureService {

    String upload(MultipartFile file) throws FileException;

    Figure getByFid(int fid);

    List<Figure> getByPid(int pid);

    Figure getThumbnailByPid(int pid);

    int addFigure(Integer uid, String url, String description, boolean isThumbnail, Timestamp updatedTime);

    int editFigure(int fid, Map param);

    // also delete from storage
    int deleteFigure(int fid);
}
