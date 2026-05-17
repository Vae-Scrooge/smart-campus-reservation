package com.smartcampus.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationRequest {
    @NotNull(message = "资源ID不能为空")
    private Long resourceId;

    @NotNull(message = "预约日期不能为空")
    private LocalDate date;

    @NotNull(message = "开始时间不能为空")
    private LocalTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalTime endTime;
}
