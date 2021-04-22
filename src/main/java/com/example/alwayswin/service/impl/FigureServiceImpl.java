package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.exception.FileException;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


// todo
@Service
public class FigureServiceImpl implements FigureService {
    // ~/service/images
    private final String uploadPath = new ApplicationHome(FigureServiceImpl.class).getSource()
                        .getParentFile().getParentFile().getPath() + "/images/";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private FigureMapper figureMapper;

    public FigureServiceImpl(FigureMapper figureMapper) {
        this.figureMapper = figureMapper;
    }

    public String upload(MultipartFile file) throws FileException {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName == null) {
                throw new FileException("file not uploaded successfully");
            }
            if (fileName.contains("..")) {
                throw new FileException("file has invalid filename");
            }
            File dir = new File(uploadPath);
            if (!dir.exists()){
                if (dir.mkdir()) {
                    logger.info(String.format("Create file uploading directory-%s", uploadPath));
                }else {
                    logger.warn(String.format("Fail to create file uploading directory-%s", uploadPath));
                }
            }
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("YYYYMMDD");

            // Requiring distinctive name -> random generator
            Date date = new Date();
            String str = simpleDateFormat.format(date);
            Random random = new Random();
            int imgRan = random.nextInt() * (99999 - 10000 + 1) + 10000;// 获取5位随机数

            String intervalName = str + imgRan;
            String displayName = intervalName + "_" + fileName;
            fileName = uploadPath + displayName;
            logger.info(String.format("Uploaded file-%s; Directory-%s",displayName , uploadPath));

            File dest = new File(fileName);
            file.transferTo(dest);
            return fileName;
        } catch (Exception e) {
            throw new FileException(String.format("[%s] file upload failed", fileName));
        }
    }

    @Override
    public Figure getByFid(int fid) {
        return null;
    }

    @Override
    public List<Figure> getByPid(int pid) {
        return null;
    }

    @Override
    public Figure getThumbnailByPid(int pid) {
        return null;
    }

    @Override
    public int editFigure(int fid, Map param) {
        return 0;
    }

    @Override
    public int addFigure(Integer uid, String url, String description, boolean isThumbnail, Timestamp updatedTime) {
        return 0;
    }

    @Override
    public int deleteFigure(int fid) {
        return 0;
    }
}