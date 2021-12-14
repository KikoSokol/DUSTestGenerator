package com.privateAPI.DUSTestGenerator.coverability_tree.controller;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.impl.CoverabilityTreeServiceTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coverability-tree-test")
public class CoverabilityTreeControllerTest
{

    private final CoverabilityTreeServiceTest serviceTest;


    public CoverabilityTreeControllerTest(CoverabilityTreeServiceTest serviceTest) {
        this.serviceTest = serviceTest;
    }


    @GetMapping("sample1")
    public ResponseEntity getSample1()
    {
        CoverabilityTreeDto sample1 = this.serviceTest.getSample1();
        return new ResponseEntity<>(sample1, HttpStatus.OK);
    }

    @GetMapping("sample2")
    public ResponseEntity getSample2()
    {
        CoverabilityTreeDto sample2 = this.serviceTest.getSample2();
        return new ResponseEntity<>(sample2, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("generator")
    public ResponseEntity getRandomCoverabilityTree()
    {
        CoverabilityTreeGeneratorResultDto coverabilityTree = this.serviceTest.getRandomCoverability();
//        CoverabilityTreeDto coverabilityTreeDto = coverabilityTree.getCoverabilityTree();

        return new ResponseEntity<>(coverabilityTree, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator-with-parameter")
    public ResponseEntity getRandomCoverabilityTree(@RequestBody CoverabilityTreeGeneratorRequest generatorRequest)
    {
        CoverabilityTreeGeneratorResultDto coverabilityTree = this.serviceTest.getRandomCoverability(generatorRequest);
//        CoverabilityTreeDto coverabilityTreeDto = coverabilityTree.getCoverabilityTree();

        return new ResponseEntity<>(coverabilityTree, HttpStatus.OK);
    }
}
