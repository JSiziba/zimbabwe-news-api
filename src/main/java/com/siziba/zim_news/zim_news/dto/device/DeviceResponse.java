package com.siziba.zim_news.zim_news.dto.device;

import com.siziba.zim_news.zim_news.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponse {
    private String macAddress;
    private String ipAddress;
    private String deviceInfo;
    private String ipAddressApproxLocation;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public static DeviceResponse from(Device device) {
        return DeviceResponse.builder()
                .macAddress(device.getMacAddress())
                .ipAddress(device.getIpAddress())
                .deviceInfo(device.getDeviceInfo())
                .ipAddressApproxLocation(device.getIpAddressApproxLocation())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }

    public static List<DeviceResponse> fromList(List<Device> devices) {
        return devices.stream()
                .map(DeviceResponse::from)
                .toList();
    }
}
