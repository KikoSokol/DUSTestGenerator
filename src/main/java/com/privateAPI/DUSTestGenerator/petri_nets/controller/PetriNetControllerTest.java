package com.privateAPI.DUSTestGenerator.petri_nets.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;
import com.privateAPI.DUSTestGenerator.petri_nets.generator.PetriNetGenerator;
import com.privateAPI.DUSTestGenerator.petri_nets.service.PetriNetService;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("petri-net-test")
public class PetriNetControllerTest
{

    @Autowired
    private PetriNetService petriNetService;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("generator")
    public ResponseEntity getRandomPetriNet()
    {

        PetriNetGeneratorRequest petriNetGeneratorRequest = new PetriNetGeneratorRequest(3,3,5,5, 0, 20, 0, 5, 1, 5);

        PetriNetGenerator petriNetGenerator = new PetriNetGenerator();
        return new ResponseEntity<>(petriNetGenerator.generateRandomPetriNet(petriNetGeneratorRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator")
    public ResponseEntity generateRandomPetriNet(@RequestBody PetriNetGeneratorRequest petriNetGeneratorRequest)
    {
        try
        {
            PetriNetDto petriNetDto = this.petriNetService.getRandomPetriNet(petriNetGeneratorRequest);
            PrescriptionPnDto prescriptionPnDto = new PrescriptionPnDto(petriNetDto);
            System.out.println(prescriptionPnDto.getPrescriptionPN());
            System.out.println(prescriptionPnDto.getPostsets());
            System.out.println(prescriptionPnDto.getPresets());

            return new ResponseEntity<>(prescriptionPnDto, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
