package moe.iacg.miraiboot.dao;


import moe.iacg.miraiboot.base.BaseDao;
import moe.iacg.miraiboot.entity.UserStatus;

import java.util.List;

/**
 * @author Ghost
 */
public interface UserStatusDao extends BaseDao<UserStatus, Long> {

    List<UserStatus> findByBangumiFlag(Integer bangumiFlag);
}
