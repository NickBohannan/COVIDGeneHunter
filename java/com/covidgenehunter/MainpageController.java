package com.covidgenehunter;

import org.biojava.nbio.core.sequence.DNASequence;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.io.File;
import java.util.Map;
import java.util.Set;

@RequestMapping("")
@Controller
public class MainpageController {

    @RequestMapping(method = RequestMethod.GET)
    public String renderMainPage(ModelMap model) {

        String filesPath = System.getenv("FASTA_ROOT_DIRECTORY");
        int trueCounter = 0, falseCounter = 0;

        LinkedHashMap<String, DNASequence> geneHashMap;
        LinkedHashMap<String, DNASequence> genomeHashMap;
        Set<Map.Entry<String, DNASequence>> genomeSet;

        try {
            genomeHashMap = FastaReaderHelper.readFastaDNASequence(new File(filesPath + "/genomicsmall.fna"), true);
            geneHashMap = FastaReaderHelper.readFastaDNASequence(new File(filesPath + "/sequences.fasta"), true);
            genomeSet = genomeHashMap.entrySet();

            System.out.println(geneHashMap.values().toString());
            for (Map.Entry<String, DNASequence> g:genomeSet) {
                String geneString = geneHashMap.values().toString();
                if (g.getValue().toString().contains((geneString.substring(1, geneString.length()-1)))) {
                    trueCounter++;
                } else {
                    falseCounter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("message", "Gene Found: " + trueCounter + " | Gene Not Found: " + falseCounter);
        return "mainpage";
    }

}
