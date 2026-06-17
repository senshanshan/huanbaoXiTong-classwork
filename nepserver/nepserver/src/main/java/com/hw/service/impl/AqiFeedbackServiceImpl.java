package com.hw.service.impl;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hw.dto.AfPageRequestDto;
import com.hw.dto.PageResponseDto;
import com.hw.mapper.AqiFeedbackMapper;
import com.hw.po.AqiFeedback;
import com.hw.service.IAqiFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AqiFeedbackServiceImpl implements IAqiFeedbackService {
    @Autowired
    private AqiFeedbackMapper aqiFeedbackMapper;

    @Override
    public int saveAqiFeedback(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.insert(aqiFeedback);
    }

    @Override
    public List<AqiFeedback> listAqiFeedbackByTelId(String telId) {
        return aqiFeedbackMapper.ListAqiFeedbackByTelId(telId);
    }

    @Override
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto request) {
        PageResponseDto<AqiFeedback> response=new PageResponseDto<>();
        //总条数
        int totalRow = aqiFeedbackMapper.getAqiFeedbackCount(request);
        response.setTotalRow(totalRow);
        if(totalRow==0){
            return response;
        }
        //总页数
        int totalPageNum=0;
        if(totalRow%request.getMaxPageNum()==0){
            totalPageNum=totalRow/request.getMaxPageNum();
        }else{
            totalPageNum=totalRow/request.getMaxPageNum()+1;
        }
        response.setTotalPageNum(totalPageNum);
        //上下页
        int pageNum=request.getPageNum();
        response.setPreNum(pageNum);
        response.setNextNum(pageNum);
        if(pageNum>1){
            response.setPreNum(pageNum-1);
        }
        if(pageNum<totalPageNum){
            response.setNextNum(pageNum+1);
        }
        //分页起始下标
        request.setBeginNum((pageNum-1)*request.getMaxPageNum());
        List<AqiFeedback> list=aqiFeedbackMapper.ListAqiFeedbackPage(request);
        response.setPageNum(pageNum);
        response.setMaxPageNum(request.getMaxPageNum());
        response.setList(list);
        return response;
    }

    @Override
    public AqiFeedback getAqiFeedbackById(Integer afId) {
        return aqiFeedbackMapper.getAqiFeedbackById(afId);
    }

    @Override
    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        UpdateWrapper<AqiFeedback> wrapper=new UpdateWrapper<>();
        wrapper.set("gm_id",aqiFeedback.getGmId());
        wrapper.set("assign_date",aqiFeedback.getAssignDate());
        wrapper.set("assign_time",aqiFeedback.getAssignTime());
        wrapper.set("state",aqiFeedback.getState());
        wrapper.eq("af_id",aqiFeedback.getAfId());
        return aqiFeedbackMapper.update(null,wrapper);
    }

    @Override
    public List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.ListAqiFeedbackByGmId(aqiFeedback);
    }

    @Override
    public int updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        UpdateWrapper<AqiFeedback> wrapper=new UpdateWrapper<>();
        wrapper.set("state",aqiFeedback.getState());
        wrapper.eq("af_id",aqiFeedback.getAfId());
        return aqiFeedbackMapper.update(null,wrapper);
    }
}