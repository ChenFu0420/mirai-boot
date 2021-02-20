package moe.iacg.miraiboot.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.base.BaseDao;
import moe.iacg.miraiboot.dao.BangumiStatusDao;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.service.BangumiStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BangumiStatusImpl implements BangumiStatusService {

    @Autowired
    private BangumiStatusDao bangumiStatusDao;

    @Override
    public BaseDao<BangumiStatus, Long> getRepository() {
        return bangumiStatusDao;
    }

    @Override
    public List<BangumiStatus> findByBangumiFlag() {
        return bangumiStatusDao.findByBangumiFlag(1);
    }
}
