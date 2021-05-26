package moe.iacg.miraiboot.service;

import moe.iacg.miraiboot.base.BaseService;
import moe.iacg.miraiboot.entity.UserStatus;

import java.util.List;

public interface UserStatusService extends BaseService<UserStatus, Long> {
    List<UserStatus> findByBangumiFlag();

}
