package moe.iacg.miraiboot.service;

import moe.iacg.miraiboot.base.BaseService;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.entity.BangumiStatusKey;

import java.util.List;

public interface BangumiStatusService extends BaseService<BangumiStatus, BangumiStatusKey> {
    List<BangumiStatus> findByBangumiFlag();

}
