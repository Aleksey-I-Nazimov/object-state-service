package org.cobulo.object.state.service.controller.api;

import org.cobulo.object.state.service.controller.dto.basic.StateDictionaryBatchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


public interface StateDictionaryApi {

    @PostMapping(value = "/insert-new-dictionaries")
    ResponseEntity<Void> insertDictionaryList(@RequestBody @Valid StateDictionaryBatchDto stateDictionaryBatchDto);

    @GetMapping(value = "/read-dictionaries")
    ResponseEntity<StateDictionaryBatchDto> getDictionaries();

}
