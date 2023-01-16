package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nullable;

/**
 * 定位处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LocateHandler extends Handler {

    /**
     * 对指定的 IP 地址进行定位。
     *
     * <p>
     * 注意：入口参数可能为 <code>null</code>。
     *
     * @param ipAddress IP 地址。
     * @return 定位结果。
     * @throws HandlerException 处理器异常。
     */
    LocateResult locate(@Nullable String ipAddress) throws HandlerException;

    /**
     * 定位结果。
     *
     * @since 1.6.0
     */
    class LocateResult {

        private final String location;
        private final Double latitude;
        private final Double longitude;

        public LocateResult(String location, Double latitude, Double longitude) {
            this.location = location;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getLocation() {
            return location;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "LocateResult{" +
                    "location='" + location + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
