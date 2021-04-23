package com.example.alwayswin.service.impl;

import com.example.alwayswin.config.WebMvcConfig;
import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import com.example.alwayswin.service.FigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class FigureServiceImpl implements FigureService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FigureMapper figureMapper;

    // ~/service/images
    private final String uploadPath = WebMvcConfig.imageToStorage;

    public FigureServiceImpl(FigureMapper figureMapper) {
        this.figureMapper = figureMapper;
    }


    // todo
//    public ResponseMsg upload(MultipartFile file) throws FileException {
//        ResponseMsg msg = new ResponseMsg(HttpServletResponse.SC_BAD_REQUEST, "File upload failed");
//        String fileName = file.getOriginalFilename();
//
//        try {
//            if (fileName == null) {
//                throw new FileException("File not uploaded successfully");
//            }
//            if (fileName.contains("..")) {
//                throw new FileException("File has invalid filename");
//            }
//            File dir = new File(uploadPath);
//            if (!dir.exists()){
//                if (dir.mkdir()) {
//                    logger.info(String.format("Create file uploading directory-%s", uploadPath));
//                }else {
//                    logger.warn(String.format("Fail to create file uploading directory-%s", uploadPath));
//                    throw new FileException("Fail to create file uploading directory");
//                }
//            }
//            SimpleDateFormat simpleDateFormat;
//            simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
//
//            // Requiring distinctive name -> random generator
//            Date date = new Date();
//            String str = simpleDateFormat.format(date);
//            Random random = new Random();
//            int imgRan = random.nextInt() * (99999 - 10000 + 1) + 10000;// 获取5位随机数
//
//            String intervalName = str + imgRan;
//            String displayName = intervalName + "_" + fileName;
//            fileName = uploadPath + displayName;
//            logger.info(String.format("Uploaded file-%s; Directory-%s",displayName , uploadPath));
//
//            File dest = new File(fileName);
//            file.transferTo(dest);
//            msg.setStatusAndMessage(HttpServletResponse.SC_OK, "File uploaded!");
//            return msg;
//        } catch (Exception e) {
//            throw new FileException(String.format("[%s] file upload failed", fileName));
//        }
//    }

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
    public int editFigure(int fid, Map param) {
        Figure figure = new Figure();
        BeanUtils.copyProperties(param, figure);
        figure.setFid(fid);
        return figureMapper.update(figure);
    }

    @Override
    public int addFigure(Map param) {
        Figure figure = new Figure();
        BeanUtils.copyProperties(param, figure);
        return figureMapper.add(figure);
    }

    // todo: delete from server as well
    @Override
    public int deleteFigure(int fid) {
        return figureMapper.delete(fid);
    }
}