package org.mangonotes;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class Config {
    @Produces
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
