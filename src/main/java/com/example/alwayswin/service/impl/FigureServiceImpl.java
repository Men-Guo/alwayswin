package com.example.alwayswin.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.utils.enumUtil.ImageType;
import com.example.alwayswin.utils.RandomStringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class FigureServiceImpl implements FigureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String BUCKET_NAME = "alwayswin-figures";

    private String BUCKET_URL = "https://alwayswin-figures.s3.amazonaws.com/";

    private final Regions REGION = Regions.US_EAST_1;

    private AmazonS3 s3client = null;

    @Resource
    private FigureMapper figureMapper;

    public FigureServiceImpl(FigureMapper figureMapper) {
        this.figureMapper = figureMapper;
    }

    @Override
    public Figure getFigureByFid(int fid) {
        return figureMapper.getByFid(fid);
    }

    @Override
    public List<Figure> getFiguresByPid(int pid) {
        return figureMapper.getByPid(pid);
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
        if (deleteFigureFromS3(url) == -1)
            return 0;

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

//    public String uploadFile1(String localPath, String s3FolderName) {
//
//        File f = new File(localPath);
//        TransferManager transferManager = TransferManagerBuilder.standard().build();
//        String s3Filename = null;
//        try {
//            s3Filename = getS3Filename(localPath, s3FolderName);
//            transferManager.upload(BUCKET_NAME, s3Filename, f);
//        } catch (AmazonServiceException e) {
//            logger.error(e.getErrorMessage());
//            return null;
//        }
//        finally {
//            transferManager.shutdownNow();
//        }
//        return BUCKET_URL + s3Filename;
//    }

    public String uploadFile(MultipartFile file, String s3FolderName) {
        String filename = file.getOriginalFilename();

        if(StringUtils.isEmpty(filename)) {
            logger.error("Filename is empty");
            return null;
        }

        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if(!ImageType.contains(suffix)) {
            logger.error("Not a supported image file");
            return null;
        }

        String s3Filename = null;
        try {
            s3Filename = s3FolderName + '/' + RandomStringUtil.createRandomString(5) + “-” + filename;
            File tempFile = new File(filename);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);  // 传到server作为中转

            // upload to s3
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, s3Filename, tempFile);
            initAWSClient();
            s3client.putObject(request);

            // delete from server
            if(tempFile.exists()) {
                tempFile.delete();
            }

        } catch (AmazonServiceException | IOException e) {
            logger.info(e.getMessage());
            return null;
        }
        logger.info("Uploaded file: {}",filename);
        return BUCKET_URL + s3Filename;
    }
    //    public List<String> uploadFileList(String[] localPaths, String s3FolderName) {
//        // convert the file paths to a list of File objects (required by the
//        // uploadFileList method)
//        ArrayList<File> files = new ArrayList<>();
//        List<String> urlList = new ArrayList<>();
//        for (String path : localPaths) {
//            files.add(new File(path));
//            urlList.add(BUCKET_URL + getS3Filename(path, s3FolderName));
//        }
//
//        TransferManager transferManager = TransferManagerBuilder.standard().build();
//        try {
//            transferManager.uploadFileList(BUCKET_NAME,
//                    s3FolderName,
//                    new File("."),   // directory
//                    files);
//
//        } catch (AmazonServiceException e) {
//            logger.error(e.getErrorMessage());
//            return null;
//        }
//        finally {
//            transferManager.shutdownNow();
//        }
//        return urlList;
//    }



    private int deleteFigureFromS3(String url) {
//        url https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png
        String[] strings = url.substring("https://".length()).split("/", 2);
        String keyName = strings[1];
        try {
            initAWSClient();
            s3client.deleteObject(BUCKET_NAME, keyName);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            return -1;
        }
        return 1;
    }

    private void initAWSClient() {
        if (this.s3client == null) {
            this.s3client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(this.REGION)
                    .build();
        }
    }

}
