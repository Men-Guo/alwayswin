package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Bidding;
import com.example.alwayswin.entity.Figure;
import org.apache.ibatis.annotations.Select;
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
    Figure getByPidAndIsThumbnail(int pid);
}
