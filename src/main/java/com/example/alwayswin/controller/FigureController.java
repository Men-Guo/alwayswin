package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: FigureController
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-22
 */

@RestController
public class FigureController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FigureService figureService;

    public FigureController(FigureService figureService) {
        this.figureService = figureService;
    }

    // 传用户头像
    @ResponseBody
    @PostMapping("/user/my-info/upload-icon")
    public CommonResult<String> uploadIcon(@RequestParam("icon") MultipartFile icon) {
        String url = figureService.uploadFile(icon, "icon");
        if (url == null)
            return CommonResult.failure("Icon upload failed");
        return CommonResult.success(url);
    }

    // 传单张的product figure
    @ResponseBody
    @PostMapping("/product/figure/upload")
    public CommonResult<String> uploadFigure(@RequestParam("figure") MultipartFile figure) {
        String url = figureService.uploadFile(figure, "product-figure");
        if (url == null)
            return CommonResult.failure("Figure upload failed");
        return CommonResult.success(url);
    }

    // 传多张product figure
//    @PostMapping("/product/figure/uploads")
//    public CommonResult<List<String>> uploadMultipleFigures(@RequestBody Map param) {
//        String[] localPaths = (String[])param.get("localPaths");
//        if (localPaths == null)
//            return CommonResult.failure("You need to provide local paths for figures");
//
//         List<String> urlList = figureService.uploadFileList(localPaths, "product-figure");
//        if (urlList == null)
//            return CommonResult.failure("Figures upload failed");
//        return CommonResult.success(urlList);
//    }

    @ResponseBody
    @GetMapping("/product/figure/{fid}")
    CommonResult<Figure> getByFid(@PathVariable int fid){
        Figure figure = figureService.getFigureByFid(fid);
        if (figure == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(figure);
    }

    @ResponseBody
    @GetMapping("/product/{pid}/figures")
    CommonResult<List<Figure>> getByPid(@PathVariable int pid) {
        List<Figure> figureList = figureService.getFiguresByPid(pid);
        if (figureList == null) {
            return CommonResult.failure();
        } else {
            return CommonResult.success(figureList);
        }
    }

    @ResponseBody
    @PostMapping("/product/figure/create")
    CommonResult<Integer> addFigure(@RequestBody Map param) {
        int res = figureService.addFigure(param);
        if (res == 1) {
            logger.info("Add figure successfully");
            return CommonResult.success(res);
        } else {
            logger.debug("Add figure failed");
            return CommonResult.failure();
        }

    }

    @ResponseBody
    @PutMapping("/product/figure/update/{fid}")
    CommonResult<Integer> updateFigure(@RequestBody Map param, @PathVariable int fid){
        int res = figureService.updateFigure(fid, param);
        if (res == 1) {
            logger.info("Edit figure successfully");
            return CommonResult.success(res);
        }
        else {
            logger.debug("Edit figure failed");
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @DeleteMapping("/product/figure/delete/{fid}")
    CommonResult<Integer> deleteFigure(@PathVariable int fid){
        int res = figureService.deleteFigure(fid);
        if (res == 1) {
            logger.info("Delete figure successfully");
            return CommonResult.success(res);
        }
        else {
            logger.debug("Delete figure failed");
            return CommonResult.failure();
        }
    }
}
