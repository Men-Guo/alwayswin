package com.example.alwayswin.service.impl;

import com.example.alwayswin.config.WebMvcConfig;
import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.utils.RandomStringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class FigureServiceImpl implements FigureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FigureMapper figureMapper;

    private final String uploadPath = WebMvcConfig.imageToStorage;

    public FigureServiceImpl(FigureMapper figureMapper) {
        this.figureMapper = figureMapper;
    }


    //todo: 还没写test
    public String upload(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName == null) {
                logger.debug("File not uploaded successfully");
                return null;
            }
            if (fileName.contains("..")) {
                logger.debug("File has invalid filename");
                return null;
            }
            File dir = new File(uploadPath);
            if (!dir.exists()){
                if (dir.mkdir()) {
                    logger.info(String.format("Create file uploading directory-%s", uploadPath));
                }else {
                    logger.warn(String.format("Fail to create file uploading directory-%s", uploadPath));
                    return null;
                }
            }

            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
            // Requiring distinctive name -> random generator
            Date date = new Date();
            String str = simpleDateFormat.format(date);
            String imgRan = RandomStringUtil.createRandomString(5);
            String intervalName = str + imgRan;
            String displayName = intervalName + "_" + fileName;
            fileName = uploadPath + displayName;
            logger.info(String.format("Uploaded file-%s; Directory-%s",displayName , uploadPath));

            File dest = new File(fileName);
            file.transferTo(dest);
            logger.info("File " + fileName + " uploaded!");

            return fileName;
        } catch (Exception e) {
            logger.warn(e.getMessage(), String.format("[%s] file upload failed", fileName));
        }
        return null;
    }

    @Override
    public Figure getFigureByFid(int fid) {
        return figureMapper.getByFid(fid);
    }

    @Override
    public List<Figure> getFiguresByPid(int pid) {
        List<Figure> figureList = figureMapper.getByPid(pid);
        // 只有一张图片，默认为封面
        if (figureList.size() == 1)
            figureList.get(0).setThumbnail(true);
        return figureList;
    }

    @Override
    public Figure getThumbnailByPid(int pid) {
        return figureMapper.getThumbnailByPid(pid);
    }

    @Override
    public int updateFigure(int fid, Map param) {
        Figure figure = new Figure();
        try {
            BeanUtils.populate(figure, param);
            figure.setFid(fid);
            figure.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
            // 更改封面
            if (figure.isThumbnail())
                changeThumbnail(figure);
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return figureMapper.update(figure);
    }

    @Override
    public int addFigure(Map param) {
        Figure figure = new Figure();
        try {
            BeanUtils.populate(figure, param);
            figure.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
            // 更改封面
            if (figure.isThumbnail()) {
               changeThumbnail(figure);
            }

        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return figureMapper.add(figure);
    }

    // todo: delete from server as well
    @Override
    public int deleteFigure(int fid) {
        return figureMapper.delete(fid);
    }


    private void changeThumbnail(Figure newThumbnail) {
        Figure oldThumbnail = getThumbnailByPid(newThumbnail.getPid());
        if (oldThumbnail != null) {
            oldThumbnail.setThumbnail(false);
            figureMapper.update(oldThumbnail);
        }
    }
}