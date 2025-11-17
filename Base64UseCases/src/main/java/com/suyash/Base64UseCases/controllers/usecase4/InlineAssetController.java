package com.suyash.Base64UseCases.controllers.usecase4;

import com.suyash.Base64UseCases.services.usecase4.InlineAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class InlineAssetController {

    @Autowired
    private InlineAssetService inlineAssetService;

    @GetMapping("inline-pdf")
    public String showInlinePdf(Model model) throws IOException{
        String dataUri = inlineAssetService.getPdfAsDataUri();
        model.addAttribute("dataUri", dataUri);
        return "inline-pdf";
    }

}
