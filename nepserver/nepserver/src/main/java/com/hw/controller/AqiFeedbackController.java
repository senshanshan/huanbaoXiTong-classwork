package com.hw.controller;
import com.hw.dto.AfPageRequestDto;
import com.hw.dto.PageResponseDto;
import com.hw.po.AqiFeedback;
import com.hw.service.IAqiFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/aqiFeedback")
public class AqiFeedbackController {
    @Autowired
    private IAqiFeedbackService aqiFeedbackService;

    //新增反馈
    @RequestMapping("/saveAqiFeedback")
    public int saveAqiFeedback(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.saveAqiFeedback(aqiFeedback);
    }

    //手机号查询反馈
    @RequestMapping("/ListAqiFeedbackByTelId")
    public List<AqiFeedback> ListAqiFeedbackByTelId(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.listAqiFeedbackByTelId(aqiFeedback.getTelId());
    }

    @RequestMapping("/listAqiFeedbackByTelId")
    public List<AqiFeedback> listAqiFeedbackByTelId(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.listAqiFeedbackByTelId(aqiFeedback.getTelId());
    }

    //分页查询
    @RequestMapping("/ListAqiFeedbackPage")
    public PageResponseDto<AqiFeedback> ListAqiFeedbackPage(@RequestBody AfPageRequestDto dto){
        return aqiFeedbackService.listAqiFeedbackPage(dto);
    }

    @RequestMapping("/listAqiFeedbackPage")
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(@RequestBody AfPageRequestDto dto){
        return aqiFeedbackService.listAqiFeedbackPage(dto);
    }

    //ID查详情
    @RequestMapping("/getAqiFeedbackById")
    public AqiFeedback getAqiFeedbackById(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.getAqiFeedbackById(aqiFeedback.getAfId());
    }

    //工单分派
    @RequestMapping("/updateAqiFeedbackAssign")
    public int updateAqiFeedbackAssign(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.updateAqiFeedbackAssign(aqiFeedback);
    }

    //网格员查询自己工单
    @RequestMapping("/ListAqiFeedbackByGmId")
    public List<AqiFeedback> ListAqiFeedbackByGmId(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.listAqiFeedbackByGmId(aqiFeedback);
    }

    @RequestMapping("/listAqiFeedbackByGmId")
    public List<AqiFeedback> listAqiFeedbackByGmId(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.listAqiFeedbackByGmId(aqiFeedback);
    }

    //修改工单状态
    @RequestMapping("/updateAqiFeedbackState")
    public int updateAqiFeedbackState(@RequestBody AqiFeedback aqiFeedback){
        return aqiFeedbackService.updateAqiFeedbackState(aqiFeedback);
    }
}
