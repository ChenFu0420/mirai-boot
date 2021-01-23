package moe.iacg.miraiboot.dao;


import moe.iacg.miraiboot.base.BaseDao;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.entity.BangumiStatusKey;

import java.util.List;

/**
 * @author Ghost
 */
public interface BangumiStatusDao extends BaseDao<BangumiStatus, BangumiStatusKey> {

    List<BangumiStatus> findByBangumiFlag(Integer bangumiFlag);
}
