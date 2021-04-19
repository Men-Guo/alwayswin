package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Figure;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: FigureMapper
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Repository
public interface FigureMapper {
    @Select("SELECT * from figure where fid = #{fid}")
    Figure getByFid(int fid);

    @Select("SELECT * from figure where pid = #{pid}")
    Figure getByPid(int pid);

    @Select("SELECT * from figure where pid = #{pid} and is_thumbnail = 1")
    Figure getThumbnail(int pid);

    @Options(useGeneratedKeys = true,keyProperty = "fid")
    @Insert("insert into " +
            "figure(pid, url, description, is_thumbnail, updated_time) " +
            "values(#{pid}, #{url}, #{description}, #{isThumbnail}, #{updatedTime})")
    int add(Figure figure);

    @Update("update figure set " +
            "figure.pid = #{pid}," +
            "figure.url = #{url}," +
            "figure.description = #{description}," +
            "figure.is_thumbnail = #{isThumbnail}," +
            "figure.updated_time = #{updatedTime}" +
            "where figure.fid = #{fid}")
    int update(Figure figure);

    @Delete("delete from figure where fid=#{fid}")
    int delete(int fid);
}
