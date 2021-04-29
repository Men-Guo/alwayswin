package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FigureServiceImpl implements FigureService {

    @Autowired
    FigureMapper figureMapper;

    @Override
    public int addFigure(Figure figure) {
        return figureMapper.add(figure);
    }

    @Override
    public int deleteFigure(int fid)
    {
        return figureMapper.delete(fid);
    }

    @Override
    public List<Figure> displayFigureByPid(int pid) {
        return figureMapper.getByPid(pid);
    }

    //未完成 需要讨论
    @Override
    public int updateFigure(Figure figure) {
        return 0;
    }

    @Override
    public Figure getFigure(int fid) {
        return figureMapper.getByFid(fid);
    }
}
