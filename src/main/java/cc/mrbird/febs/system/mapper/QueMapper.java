package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Que;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author longzx
 */
public interface QueMapper extends BaseMapper<Que> {

    /**
     * 通过题干查找试题
     *
     * @param quetitle 题干
     * @return 用户
     */
    Que findByName(String quetitle);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    <T> IPage<Que> findQueDetailPage(Page<T> page, @Param("que") Que que);

    long countQueDetail(@Param("que") Que que);

    /**
     * 查找试题详细信息
     *
     * @param que 用户对象，用于传递查询条件
     * @return List<Que>
     */
    List<Que> findQueDetail(@Param("que") Que que);
    List<Que> findNQue(@Param("number") int n);

}
