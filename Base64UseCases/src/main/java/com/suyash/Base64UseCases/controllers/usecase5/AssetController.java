package com.suyash.Base64UseCases.controllers.usecase5;

import com.suyash.Base64UseCases.models.usecase5.StoredAssets;
import com.suyash.Base64UseCases.services.usecase5.AssetStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AssetController {

    private final AssetStorageService service;

    public AssetController(AssetStorageService service) {
        this.service = service;
    }

    @GetMapping("/assets")
    public String list(Model model){
        model.addAttribute("assets", service.listAllAssets());
        return "assets-list";
    }

    @GetMapping("/assets/upload")
    public String uploadForm(){
        return "assets-upload";
    }

    @PostMapping("/assets")
    public String upload(@RequestParam("file")MultipartFile file,
                         @RequestParam(value = "filename", required = false) String filename,
                         RedirectAttributes redirect) {
        try{
            StoredAssets assets = service.store(file, filename);
            redirect.addFlashAttribute("message", assets.getFilename());
            return "redirect:/assets/"+assets.getId();
        } catch(Exception e){
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/assets/upload";
        }
    }

    @GetMapping("assets/{id}")
    public String detail(@PathVariable String id,
                         Model model,
                         RedirectAttributes redirect){
        return service.get(id)
                .map(asset -> {
                    model.addAttribute("asset", asset);
                    model.addAttribute("dataUri", service.toDataUri(asset));

                    return "asset-detail";
                }).orElseGet(()-> {
                    redirect.addFlashAttribute("error", "Asset not found");
                    return "redirect:/assets";
                });
    }

    @GetMapping("/assets/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        return service.get(id).map(asset -> {
            byte[] bytes = service.decode(asset);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + asset.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(asset.getMimeType()))
                    .body(bytes);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }





}
