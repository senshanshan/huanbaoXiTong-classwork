package com.hw.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.dto.AfPageRequestDto;
import com.hw.po.AqiFeedback;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AqiFeedbackMapper extends BaseMapper<AqiFeedback> {
    //根据手机号查反馈列表
    List<AqiFeedback> ListAqiFeedbackByTelId(String telId);
    //分页总条数
    int getAqiFeedbackCount(AfPageRequestDto afPageRequestDto);
    //分页数据
    List<AqiFeedback> ListAqiFeedbackPage(AfPageRequestDto afPageRequestDto);
    //主键查询详情
    AqiFeedback getAqiFeedbackById(Integer afId);
    //根据网格员ID+状态查询
    List<AqiFeedback> ListAqiFeedbackByGmId(AqiFeedback aqiFeedback);
}