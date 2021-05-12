package com.example.alwayswin.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.alwayswin.config.WebMvcConfig;
import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class FigureServiceImpl implements FigureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String BUCKET_NAME = "alwayswin-figures";
    private final String BUCKET_URL = "https://alwayswin-figures.s3.amazonaws.com/";
    private final Regions REGION = Regions.US_EAST_1;

    @Resource
    private FigureMapper figureMapper;

    private final String uploadPath = WebMvcConfig.imageToStorage;

    public FigureServiceImpl(FigureMapper figureMapper) {
        this.figureMapper = figureMapper;
    }

    @Override
    public Figure getFigureByFid(int fid) {
        return figureMapper.getByFid(fid);
    }

    @Override
    public List<Figure> getFiguresByPid(int pid) {
        List<Figure> figureList = figureMapper.getByPid(pid);
        // 只有一张图片，默认为封面
        if (figureList != null && figureList.size() == 1)
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

    @Override
    public int deleteFigure(int fid) {
        Figure figure = getFigureByFid(fid);
        if (figure == null)
            return 0;
        String url = figure.getUrl();
        // 先从bucket里删掉
        deleteFigureFromS3(url);

        // 再从数据库删掉
        return figureMapper.delete(fid);
    }


    private void changeThumbnail(Figure newThumbnail) {
        Figure oldThumbnail = getThumbnailByPid(newThumbnail.getPid());
        if (oldThumbnail != null) {
            oldThumbnail.setThumbnail(false);
            figureMapper.update(oldThumbnail);
        }
    }

    public String uploadFile(String localPath, String s3FolderName) {

        File f = new File(localPath);
        TransferManager transferManager = TransferManagerBuilder.standard().build();
        String s3Filename = null;
        try {
            s3Filename = getS3Filename(localPath, s3FolderName);
            transferManager.upload(BUCKET_NAME, s3Filename, f);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            return null;
        }
        finally {
            transferManager.shutdownNow();
        }
        return BUCKET_URL + s3Filename;
    }


    public List<String> uploadFileList(String[] localPaths, String s3FolderName) {
        // convert the file paths to a list of File objects (required by the
        // uploadFileList method)
        ArrayList<File> files = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        for (String path : localPaths) {
            files.add(new File(path));
            urlList.add(BUCKET_URL + getS3Filename(path, s3FolderName));
        }

        TransferManager transferManager = TransferManagerBuilder.standard().build();
        try {
            transferManager.uploadFileList(BUCKET_NAME,
                    s3FolderName,
                    new File("."),   // directory
                    files);

        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            return null;
        }
        finally {
            transferManager.shutdownNow();
        }
        return urlList;
    }


    private String getS3Filename(String localPath, String s3FolderName) {
        String[] strings = localPath.split("/");
        String originalFilename = strings[strings.length - 1];  // 最后一个是文件名

        String s3Filename = null;
        if (s3FolderName != null) {
            s3Filename = s3FolderName + '/' + originalFilename;
        } else {
            s3Filename = originalFilename;
        }
        return s3Filename;
    }


    private void deleteFigureFromS3(String url) {
//        url https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png
        String[] strings = url.substring("https://".length()).split("/", 2);
        String keyName = strings[1];
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(REGION).build();
        try {
            s3.deleteObject(BUCKET_NAME, keyName);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
        }
    }

}