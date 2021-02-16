package moe.iacg.miraiboot.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.model.GithubWebHookModel;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Github")
@Slf4j
public class GithubController {

    @Autowired
    BotUtils botUtils;

    @RequestMapping("/webHook")
    public void webHook(@RequestBody String payload) {

        GithubWebHookModel githubWebHookModel = JSON.parseObject(payload, GithubWebHookModel.class);

        if (githubWebHookModel == null || githubWebHookModel.getHeadCommit() == null) {
            return;
        }

        GithubWebHookModel.HeadCommit headCommit = githubWebHookModel.getHeadCommit();

        Bot firstBot = botUtils.getFirstBot();
        Msg builder = Msg.builder();
        builder.text("本Bot更新内容：\n")
                .text(headCommit.getMessage()).text("\n")
                .text(headCommit.getUrl()).text("\n")
                .text("提交人：").text(headCommit.getCommitter().getUsername());

        OnebotApi.GetGroupListResp groupList = firstBot.getGroupList();
        for (OnebotApi.GetGroupListResp.Group group : groupList.getGroupList()) {
            botUtils.sendGroupMsg(group.getGroupId(), builder);
        }

    }
}
