package com.tech_nova.tech_nova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.assemblers.DireccionModelAssembler;
import com.tech_nova.tech_nova.service.DireccionService;

@RestController
@RequestMapping("/api/v2/direcciones ")
public class DireccionControllerV2 {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    DireccionModelAssembler assembler;

    
}
