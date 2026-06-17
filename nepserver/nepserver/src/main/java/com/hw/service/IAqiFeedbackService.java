package com.hw.service;
import com.hw.dto.AfPageRequestDto;
import com.hw.dto.PageResponseDto;
import com.hw.po.AqiFeedback;
import java.util.List;

public interface IAqiFeedbackService {
    //新增反馈
    int saveAqiFeedback(AqiFeedback aqiFeedback);
    //手机号查反馈
    List<AqiFeedback> listAqiFeedbackByTelId(String telId);
    //分页查询
    PageResponseDto<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto afPageRequestDto);
    //id查详情
    AqiFeedback getAqiFeedbackById(Integer afId);
    //分派工单（修改网格员、分派时间、状态）
    int updateAqiFeedbackAssign(AqiFeedback aqiFeedback);
    //网格员id+状态查询
    List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback);
    //修改工单状态
    int updateAqiFeedbackState(AqiFeedback aqiFeedback);
}