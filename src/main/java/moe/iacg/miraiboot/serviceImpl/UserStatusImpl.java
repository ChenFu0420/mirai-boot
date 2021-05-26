package moe.iacg.miraiboot.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.base.BaseDao;
import moe.iacg.miraiboot.dao.UserStatusDao;
import moe.iacg.miraiboot.entity.UserStatus;
import moe.iacg.miraiboot.service.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserStatusImpl implements UserStatusService {

    @Autowired
    private UserStatusDao userStatusDao;

    @Override
    public BaseDao<UserStatus, Long> getRepository() {
        return userStatusDao;
    }

    @Override
    public List<UserStatus> findByBangumiFlag() {
        return userStatusDao.findByBangumiFlag(1);
    }
}
