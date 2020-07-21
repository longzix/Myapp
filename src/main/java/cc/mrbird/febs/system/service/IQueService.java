package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Que;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author longzx
 */
public interface IQueService extends IService<Que> {

    /**
     * 通过题干名查找试题
     *
     * @param quetitle 题干
     * @return 用户
     */
    Que findByName(String quetitle);

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param que   用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<Que> findQueDetailList(Que que, QueryRequest request);

    /**
     * 通过题干查找试题信息
     *
     * @param quetitle 题干
     * @return 用户信息
     */
    Que findQueDetailList(String quetitle);


    /**
     * 新增试题
     *
     * @param Que que
     */
    void createQue(Que que);

    /**
     * 删除试题
     *
     * @param  queIds 试题 id数组
     */
    void deleteQue(String[] queIds);

    /**
     * 修改试题
     *
     * @param Que que
     */
        void updateQue(Que que);

    /**
     * 寻找指定number数量的随机试题
     *
     * @param int number
     */
    List<Que> findNQue(int number);
}
