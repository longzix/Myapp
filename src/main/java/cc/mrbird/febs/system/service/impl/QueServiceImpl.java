package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Que;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.mapper.QueMapper;
import cc.mrbird.febs.system.service.IQueService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QueServiceImpl extends ServiceImpl<QueMapper, Que> implements IQueService {



    @Override
    public Que findByName(String quetitle) {
        return this.baseMapper.findByName(quetitle);
    }

    @Override
    public IPage<Que> findQueDetailList(Que que, QueryRequest request) {

        Page<Que> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        page.setTotal(baseMapper.countQueDetail(que));
        SortUtil.handlePageSort(request, page, "queId", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findQueDetailPage(page, que);
    }

    @Override
    public Que findQueDetailList(String quetitle) {
      Que param = new Que();
      param.setQueTitle( quetitle);
      List<Que> ques = this.baseMapper.findQueDetail(param);
      return  CollectionUtils.isNotEmpty(ques) ? ques.get(0) : null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createQue(Que que) {

        save(que);
        // 保存用户角色

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQue(String[] queIds) {
        List<String> list = Arrays.asList(queIds);
        // 删除试题
        this.removeByIds(list);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQue(Que que) {
        updateById(que);
    }

    private boolean isCurrentUser(Long id) {
        User currentUser = FebsUtil.getCurrentUser();
        return currentUser.getUserId().equals(id);
    }

    @Override
    public List<Que> findNQue(int number) {
        return this.baseMapper.findNQue(number);
    }
}
