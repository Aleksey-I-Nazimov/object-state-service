package org.cobulo.object.state.service.controller.dto.basic;

public final class StatefulObjectDto {

    private String systemId;
    private String systemCode;
    private String info;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StatefulObjectDto{" +
                "systemId='" + systemId + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
