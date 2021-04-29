package com.example.alwayswin.service;

import com.example.alwayswin.entity.Figure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FigureService {

    int addFigure(Figure figure);
    int deleteFigure(int fid);
    List<Figure> displayFigureByPid(int pid);
    int updateFigure(Figure figure);
    Figure getFigure(int fid);
}
