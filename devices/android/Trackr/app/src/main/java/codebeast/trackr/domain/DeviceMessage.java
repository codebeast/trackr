package codebeast.trackr.domain;


public class DeviceMessage {

    private String deviceId;
    private String imei;

    private long deviceTimestamp;
    private long systemTimestamp;

    private double lat;
    private double lng;
    private double speed;
    private double accuracy;

    public DeviceMessage() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceMessage setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public DeviceMessage setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public long getDeviceTimestamp() {
        return deviceTimestamp;
    }

    public DeviceMessage setDeviceTimestamp(long deviceTimestamp) {
        this.deviceTimestamp = deviceTimestamp;
        return this;
    }

    public double getLat() {
        return lat;

    }

    public DeviceMessage setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public DeviceMessage setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public DeviceMessage setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public DeviceMessage setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public long getSystemTimestamp() {
        return systemTimestamp;
    }

    public DeviceMessage setSystemTimestamp(long systemTimestamp) {
        this.systemTimestamp = systemTimestamp;
        return this;
    }
}
