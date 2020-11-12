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

    private static String filesPath = "C:\\Users\\Nick\\IdeaProjects\\COVIDGeneHunter\\src\\main\\webapp\\resources\\files".replace("\\", "/");

    public static LinkedHashMap<String, DNASequence> geneHashMap;
    public static LinkedHashMap<String, DNASequence> genomeHashMap;
    public static Set<Map.Entry<String, DNASequence>> genomeSet;

    public int trueCounter, falseCounter;

    static {
        try {
            genomeHashMap = FastaReaderHelper.readFastaDNASequence(new File(filesPath + "/genomicsmall.fna"), false);
            geneHashMap = FastaReaderHelper.readFastaDNASequence(new File(filesPath + "/sequences.fasta"), false);
            genomeSet = genomeHashMap.entrySet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String renderMainPage(ModelMap model) {
        CharSequence geneSequence = "ATGGGCTATATAAACGTTTTCGCTTTTCCGTTTACGATATATAGTCTACTCTTGTGCAGAATGAATTCTCGTAACTACATAGCACAAGTAGATGTAGTTAACTTTAATCTCACATAG";
        for (Map.Entry<String, DNASequence> g:genomeSet) {
            if (g.toString().contains(geneSequence)) {
                trueCounter++;
            } else {
                falseCounter++;
            }
        }

        model.addAttribute("message", "Gene Found: " + trueCounter + " | Gene Not Found: " + falseCounter);
        return "mainpage";
    }

}
