package com.privateAPI.DUSTestGenerator.petri_nets.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.DefinitionPTIOM0;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;
import com.privateAPI.DUSTestGenerator.petri_nets.service.PetriNetService;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("petri-net")
public class PetriNetController
{
    @Autowired
    private PetriNetService petriNetService;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator")
    public ResponseEntity generateRandomPetriNet(@RequestBody PetriNetGeneratorRequest petriNetGeneratorRequest)
    {
        try
        {
            PetriNetDto petriNetDto = this.petriNetService.getRandomPetriNet(petriNetGeneratorRequest);
            return new ResponseEntity<>(petriNetDto, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator-with-prescription-pn")
    public ResponseEntity generateRandomPetriNetWithPrescriptionPN(@RequestBody PetriNetGeneratorRequest petriNetGeneratorRequest)
    {
        try
        {
            PrescriptionPnDto prescriptionPnDto = this.petriNetService.getRandomPetriNetWithPrescriptionPN(petriNetGeneratorRequest);
            return new ResponseEntity<>(prescriptionPnDto, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator-with-ptiom0c")
    public ResponseEntity generateRandomPetriNetWithDefinitionPNIOM0C(@RequestBody PetriNetGeneratorRequest petriNetGeneratorRequest)
    {
        try
        {
            DefinitionPTIOM0 definitionPTIOM0 = this.petriNetService.getRandomPetriNetWithDefinitionPNIOM0C(petriNetGeneratorRequest);
            return new ResponseEntity<>(definitionPTIOM0, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }



}
