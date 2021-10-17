package com.tulingxueyuan.pre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author
 * @date 2021年10月17日16:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class swAlarmControl {
    @PostMapping("/receive")
    public void receive(@RequestBody List<swalarmDTO> alarmlist){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom("1713695034@qq.com");
        msg.setTo("417652977@qq.com");
        msg.setSubject("测试");
        msg.setText("测试2");

        String context = getConnect(alarmlist);
        log.info("邮件已经发送……"+context);

    }
    private String getConnect(List<swalarmDTO> swalarmDTOS){
        StringBuffer sb=new StringBuffer();
        for (swalarmDTO dto:swalarmDTOS){
         sb.append("scopeId:").append(dto.getScopeId())
         .append("\nscop:").append(dto.getScope())
         .append("\n目标scop的实体名称:").append(dto.getName())
         .append("\nscop的实体ID:").append(dto.getId0())
         .append("\nid1:").append(dto.getId1())
         .append("\n告警规则名称:").append(dto.getRuleName())
         .append("\n告警消息内rong:").append(dto.getAlarmMessage())
         .append("\n告警时间:").append(dto.getStartTime())
         .append("\n标签:").append(dto.getTags())
         .append("\n\n____________________:");
        }
        return sb.toString();
    }
}
