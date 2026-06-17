package com.hw.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hw.dto.AfPageRequestDto;
import com.hw.dto.PageResponseDto;
import com.hw.mapper.AqiFeedbackMapper;
import com.hw.po.AqiFeedback;
import com.hw.service.IAqiFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AqiFeedbackServiceImpl implements IAqiFeedbackService {
    @Autowired
    private AqiFeedbackMapper aqiFeedbackMapper;

    @Override
    public int saveAqiFeedback(AqiFeedback aqiFeedback) {
        if (aqiFeedback == null
                || isBlank(aqiFeedback.getTelId())
                || !isPositive(aqiFeedback.getProvinceId())
                || !isPositive(aqiFeedback.getCityId())
                || isBlank(aqiFeedback.getAddress())
                || isBlank(aqiFeedback.getInformation())
                || !isAqiGrade(aqiFeedback.getEstimatedGrade())
                || isBlank(aqiFeedback.getAfDate())
                || isBlank(aqiFeedback.getAfTime())) {
            return 0;
        }
        if (aqiFeedback.getAfId() != null
                || aqiFeedback.getGmId() != null
                || !isBlank(aqiFeedback.getAssignDate())
                || !isBlank(aqiFeedback.getAssignTime())) {
            return 0;
        }
        if (aqiFeedback.getState() == null) {
            aqiFeedback.setState(0);
        }
        if (aqiFeedback.getState() != 0) {
            return 0;
        }
        return aqiFeedbackMapper.insert(aqiFeedback);
    }

    @Override
    public List<AqiFeedback> listAqiFeedbackByTelId(String telId) {
        return aqiFeedbackMapper.ListAqiFeedbackByTelId(telId);
    }

    @Override
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto request) {
        PageResponseDto<AqiFeedback> response = new PageResponseDto<>();
        int pageNum = request.getPageNum() == null || request.getPageNum() < 1 ? 1 : request.getPageNum();
        int maxPageNum = request.getMaxPageNum() == null || request.getMaxPageNum() < 1 ? 8 : request.getMaxPageNum();
        request.setPageNum(pageNum);
        request.setMaxPageNum(maxPageNum);

        int totalRow = aqiFeedbackMapper.getAqiFeedbackCount(request);
        response.setTotalRow(totalRow);
        response.setPageNum(pageNum);
        response.setMaxPageNum(maxPageNum);
        if (totalRow == 0) {
            response.setTotalPageNum(0);
            response.setPreNum(pageNum);
            response.setNextNum(pageNum);
            response.setBeginNum(0);
            response.setList(Collections.emptyList());
            return response;
        }

        int totalPageNum = totalRow % maxPageNum == 0 ? totalRow / maxPageNum : totalRow / maxPageNum + 1;
        response.setTotalPageNum(totalPageNum);
        response.setPreNum(pageNum > 1 ? pageNum - 1 : pageNum);
        response.setNextNum(pageNum < totalPageNum ? pageNum + 1 : pageNum);
        request.setBeginNum((pageNum - 1) * maxPageNum);
        response.setBeginNum(request.getBeginNum());
        response.setList(aqiFeedbackMapper.ListAqiFeedbackPage(request));
        return response;
    }

    @Override
    public AqiFeedback getAqiFeedbackById(Integer afId) {
        return aqiFeedbackMapper.getAqiFeedbackById(afId);
    }

    @Override
    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        if (aqiFeedback == null
                || !isPositive(aqiFeedback.getAfId())
                || !isPositive(aqiFeedback.getGmId())
                || isBlank(aqiFeedback.getAssignDate())
                || isBlank(aqiFeedback.getAssignTime())
                || aqiFeedback.getState() == null
                || aqiFeedback.getState() != 1) {
            return 0;
        }
        UpdateWrapper<AqiFeedback> wrapper = new UpdateWrapper<>();
        wrapper.set("gm_id", aqiFeedback.getGmId());
        wrapper.set("assign_date", aqiFeedback.getAssignDate());
        wrapper.set("assign_time", aqiFeedback.getAssignTime());
        wrapper.set("state", aqiFeedback.getState());
        wrapper.eq("af_id", aqiFeedback.getAfId());
        return aqiFeedbackMapper.update(null, wrapper);
    }

    @Override
    public List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.ListAqiFeedbackByGmId(aqiFeedback);
    }

    @Override
    public int updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        if (aqiFeedback == null
                || !isPositive(aqiFeedback.getAfId())
                || aqiFeedback.getState() == null
                || aqiFeedback.getState() != 2) {
            return 0;
        }
        UpdateWrapper<AqiFeedback> wrapper = new UpdateWrapper<>();
        wrapper.set("state", aqiFeedback.getState());
        wrapper.eq("af_id", aqiFeedback.getAfId());
        return aqiFeedbackMapper.update(null, wrapper);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isPositive(Integer value) {
        return value != null && value > 0;
    }

    private boolean isAqiGrade(Integer value) {
        return value != null && value >= 1 && value <= 6;
    }
}
