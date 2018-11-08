package com.nei.ismp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author bofei
 * @date 2018/11/8 17:24
 */
@Mapper
public interface TaskMapper {

    @Select("SELECT status FROM task WHERE id = #{id}")
    String getTaskStatusById(String id);
}
