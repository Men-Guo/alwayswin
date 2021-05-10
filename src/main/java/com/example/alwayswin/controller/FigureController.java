package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.service.FigureService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
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

    @PostMapping("/figure/upload")
    public CommonResult<String> uploadFigure(@RequestParam("figure") MultipartFile file) {
        String fileName = figureService.upload(file);
        if (fileName == null)
            return CommonResult.failure("Figure upload failed");
        else
            return CommonResult.success(fileName);
    }

    @PostMapping("/figure/multiple-uploads")
    public List<CommonResult<String>> uploadMultipleFigures(@RequestParam("figures") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFigure(file))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/product/figure/{fid}")
    CommonResult<Figure> getByFid(@PathVariable int fid){
        if (fid <= 0)
            return CommonResult.validateFailure();
        Figure figure = figureService.getFigureByFid(fid);
        if (figure == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(figure);
    }

    @ResponseBody
    @GetMapping("/product/{pid}/figures")
    CommonResult<List<Figure>> getByPid(@PathVariable int pid) {
        if (pid <= 0)
            return CommonResult.validateFailure();

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
        if (fid < 0)
            return CommonResult.validateFailure();
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
        if (fid < 0)
            return CommonResult.validateFailure();

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
