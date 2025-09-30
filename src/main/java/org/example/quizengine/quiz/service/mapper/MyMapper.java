package org.example.quizengine.quiz.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyMapper {

    private final ModelMapper modelMapper;

    public MyMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public <S, T> T turnIntoDTO(S Source, Class<T> targetClass){
        return modelMapper.map(Source, targetClass);
    }

}
