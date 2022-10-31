package org.cobulo.object.state.service.controller.dto.basic;


public final class StateDictionaryDto {

    private String code;
    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StateDictionaryDto{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
