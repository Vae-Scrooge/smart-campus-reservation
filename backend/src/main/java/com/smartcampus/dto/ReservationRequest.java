package com.smartcampus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {
    @NotNull(message = "资源ID不能为空")
    private Long resourceId;

    @NotBlank(message = "预约日期不能为空")
    private String date;

    @NotBlank(message = "开始时间不能为空")
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    private String endTime;
}
