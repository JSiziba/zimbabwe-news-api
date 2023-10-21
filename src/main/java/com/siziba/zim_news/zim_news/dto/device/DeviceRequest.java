package com.siziba.zim_news.zim_news.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {
    private String macAddress;
    private String ipAddress;
    private String deviceInfo;

}
