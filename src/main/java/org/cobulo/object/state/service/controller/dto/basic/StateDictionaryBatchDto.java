package org.cobulo.object.state.service.controller.dto.basic;

import java.util.ArrayList;
import java.util.List;

public final class StateDictionaryBatchDto {

    private List<StateDictionaryDto> dictionaryList = new ArrayList<>();

    public List<StateDictionaryDto> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<StateDictionaryDto> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    @Override
    public String toString() {
        return "StateDictionaryBatchDto{" +
                "dictionaryList=" + dictionaryList +
                '}';
    }
}
